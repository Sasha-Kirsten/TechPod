package com.techpod;
import com.techpod.dto.LaptopRequest;
import com.techpod.service.LaptopService;
import com.techpod.exception.ResourceNotFoundException;
import com.techpod.model.Laptop;
import com.techpod.repository.LaptopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaptopServiceTest {

    @Mock
    private LaptopRepository laptopRepository;

    @InjectMocks
    private LaptopService laptopService;

    private Laptop mockLaptop;
    private LaptopRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockLaptop = Laptop.builder()
                .id(1L)
                .brand("Dell")
                .model("XPS 15")
                .processor("Intel i7")
                .ramGb(16)
                .storage("512GB SSD")
                .gpu("NVIDIA RTX 3050")
                .screenSizeInch(15.6)
                .price(new BigDecimal("1299.99"))
                .stockQuantity(10)
                .build();

        mockRequest = new LaptopRequest();
        mockRequest.setBrand("Dell");
        mockRequest.setModel("XPS 15");
        mockRequest.setProcessor("Intel i7");
        mockRequest.setRamGb(16);
        mockRequest.setStorage("512GB SSD");
        mockRequest.setGpu("NVIDIA RTX 3050");
        mockRequest.setScreenSizeInch(15.6);
        mockRequest.setPrice(new BigDecimal("1299.99"));
        mockRequest.setStockQuantity(10);
    }

    @Test
    void getAll_shouldReturnAllLaptops() {
        when(laptopRepository.findAll()).thenReturn(List.of(mockLaptop));

        List<Laptop> result = laptopService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrand()).isEqualTo("Dell");
        verify(laptopRepository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnLaptop_whenExists() {
        when(laptopRepository.findById(1L)).thenReturn(Optional.of(mockLaptop));

        Laptop result = laptopService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getModel()).isEqualTo("XPS 15");
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(laptopRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> laptopService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnLaptop() {
        when(laptopRepository.save(any(Laptop.class))).thenReturn(mockLaptop);

        Laptop result = laptopService.create(mockRequest);

        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("Dell");
        verify(laptopRepository, times(1)).save(any(Laptop.class));
    }

    @Test
    void update_shouldModifyAndReturnLaptop() {
        mockRequest.setBrand("HP");
        mockRequest.setModel("Spectre x360");

        Laptop updatedLaptop = Laptop.builder()
                .id(1L).brand("HP").model("Spectre x360")
                .processor("Intel i7").ramGb(16).storage("512GB SSD")
                .price(new BigDecimal("1299.99")).stockQuantity(10).build();

        when(laptopRepository.findById(1L)).thenReturn(Optional.of(mockLaptop));
        when(laptopRepository.save(any(Laptop.class))).thenReturn(updatedLaptop);

        Laptop result = laptopService.update(1L, mockRequest);

        assertThat(result.getBrand()).isEqualTo("HP");
        assertThat(result.getModel()).isEqualTo("Spectre x360");
    }

    @Test
    void delete_shouldCallDelete_whenLaptopExists() {
        when(laptopRepository.findById(1L)).thenReturn(Optional.of(mockLaptop));

        laptopService.delete(1L);

        verify(laptopRepository, times(1)).delete(mockLaptop);
    }
}