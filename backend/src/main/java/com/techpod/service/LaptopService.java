package com.techpod.service;

import com.techpod.dto.LaptopRequest;
import com.techpod.exception.ResourceNotFoundException;
import com.techpod.model.Laptop;
import com.techpod.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaptopService {

    private final LaptopRepository laptopRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Laptop> getAll() { return laptopRepository.findAll(); }

    public Laptop getById(Long id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laptop not found: " + id));
    }

    public List<Laptop> search(String brand, BigDecimal minPrice, BigDecimal maxPrice, Integer ram) {
        return laptopRepository.search(brand, minPrice, maxPrice, ram);
    }

    public Laptop create(LaptopRequest r) {
        return laptopRepository.save(Laptop.builder()
                .brand(r.getBrand()).model(r.getModel()).processor(r.getProcessor())
                .ramGb(r.getRamGb()).storage(r.getStorage()).gpu(r.getGpu())
                .screenSizeInch(r.getScreenSizeInch()).price(r.getPrice())
                .stockQuantity(r.getStockQuantity()).imageUrl(r.getImageUrl())
                .description(r.getDescription()).build());
    }

    public Laptop update(Long id, LaptopRequest r) {
        Laptop l = getById(id);
        l.setBrand(r.getBrand()); l.setModel(r.getModel()); l.setProcessor(r.getProcessor());
        l.setRamGb(r.getRamGb()); l.setStorage(r.getStorage()); l.setGpu(r.getGpu());
        l.setScreenSizeInch(r.getScreenSizeInch()); l.setPrice(r.getPrice());
        l.setStockQuantity(r.getStockQuantity()); l.setImageUrl(r.getImageUrl());
        l.setDescription(r.getDescription());
        return laptopRepository.save(l);
    }

    public Laptop stockLow(Long id, LaptopRequest r) {
        Laptop l = getById(id);
        l.setStockQuantity(r.getStockQuantity());
        if (l.getStockQuantity() < 5) {
            // Trigger low stock alert (e.g., send email to admin)
            messagingTemplate.convertAndSend("/topic/stock/" + l.getId(), "Low stock alert for " + l.getBrand() + " " + l.getModel() + ": only " + l.getStockQuantity() + " left!");
        }
        return laptopRepository.save(l);
    }

    public void delete(Long id) { laptopRepository.delete(getById(id)); }
}
