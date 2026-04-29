package com.techpod.repository;

import com.techpod.model.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Long> {
    
}
