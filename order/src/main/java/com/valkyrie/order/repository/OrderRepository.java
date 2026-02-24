package com.valkyrie.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.order.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select * from orders where customer_id = :customerId", nativeQuery = true)
    public List<Order> findAllOrdersByCustomerId(@Param("customerId") String customerId);

    @Modifying
    @Transactional
    @Query(value = "update orders set cancel = not cancel where order_id = :orderId", nativeQuery = true)
    public int cancelOrder(@Param("orderId") String orderId);

    @Query(value = "select cancel from orders where order_id = :orderId", nativeQuery = true)
    public Boolean checkCancelStatus(@Param("orderId") String orderId);
    
    @Modifying
    @Transactional
    @Query(value = "delete from orders where id in (select id from orders order by id desc limit 5)", nativeQuery = true)
    public int deleteLastFiveOrders();

    @Query(value = "select exists (select 1 from orders where order_id = :orderId)", nativeQuery = true)
    public boolean isExistByOrderId(@Param("orderId") String orderId);
}
