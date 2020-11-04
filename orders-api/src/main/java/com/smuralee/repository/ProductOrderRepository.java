package com.smuralee.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@XRayEnabled
@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("SELECT order from ProductOrder order where order.userId = :userId")
    List<ProductOrder> findByUserId(@Param("userId") Long userId);
}
