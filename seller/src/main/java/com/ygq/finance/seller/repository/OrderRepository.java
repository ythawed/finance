package com.ygq.finance.seller.repository;

import com.ygq.finance.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 * 订单与mysql的交互类
 * 可以不添加@Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

}
