package com.ygq.manager.repository;

import com.ygq.finance.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ythawed
 * @date 2019/12/04
 *
 * JPA类，继承单条件类型与多条件型类型JpaRepository，JpaSpecificationExecutor
 * JpaRepository<Product, String> Product表示查询返回的结果，String表示查询的条件，此处是id
 * JpaSpecificationExecutor<Product> Product表示查询返回的结果，没有String，因为是多条件查询
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {

}
