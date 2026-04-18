package com.techpod.repository;

import com.techpod.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    @Query("SELECT l FROM Laptop l WHERE " +
           "(:brand IS NULL OR LOWER(l.brand) = LOWER(:brand)) AND " +
           "(:minPrice IS NULL OR l.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR l.price <= :maxPrice) AND " +
           "(:ram IS NULL OR l.ramGb >= :ram)")
    List<Laptop> search(@Param("brand") String brand,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        @Param("ram") Integer ram);
}
