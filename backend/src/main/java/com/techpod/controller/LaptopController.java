package com.techpod.controller;

import com.techpod.dto.LaptopRequest;
import com.techpod.model.Laptop;
import com.techpod.service.LaptopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/laptops")
@RequiredArgsConstructor
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping
    public List<Laptop> getAll(@RequestParam(required = false) String brand,
                               @RequestParam(required = false) BigDecimal minPrice,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               @RequestParam(required = false) Integer ram) {
        if (brand != null || minPrice != null || maxPrice != null || ram != null)
            return laptopService.search(brand, minPrice, maxPrice, ram);
        return laptopService.getAll();
    }

    @GetMapping("/{id}")
    public Laptop getById(@PathVariable Long id) { return laptopService.getById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Laptop> create(@Valid @RequestBody LaptopRequest req) {
        return ResponseEntity.ok(laptopService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Laptop> update(@PathVariable Long id, @Valid @RequestBody LaptopRequest req) {
        return ResponseEntity.ok(laptopService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        laptopService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
