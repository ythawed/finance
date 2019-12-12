package com.ygq.finance.seller.repository;

import com.ygq.finance.entity.VerificationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/10 0010
 *
 * 对账Jpa
 */
public interface VerificationOrderRepository extends JpaRepository<VerificationOrder, String>, JpaSpecificationExecutor<VerificationOrder> {
    /**
     * 根据chanId查询该渠道下，在是时间段(start,end)内的对账数据，返回对账数据列表
     *
     * 1、注意sql的写法与传参方式。
     * 2、注意关键字间如果换行时需要空格隔开
     * 3、查询结果的属性间用'|'连接
     * 4、字符串中时间的写法
     *      1.'%Y-%m-%d %H:%i:%s'
     *      2."%Y-%m-%d %H:%i:%s'"
     * @param chanId 渠道id
     * @param start 开始时间
     * @param end  结束时间
     * @return 对账数据结果集
     */
    @Query(value =
            "SELECT CONCAT_WS('|',order_id,chan_id,product_id,chan_user_id,order_type,outer_order_id,amount,DATE_FORMAT(create_time,\"%Y-%m-%d %H:%i:%s\")) " +
                    "FROM `pe_order` " +
                    "WHERE order_status='success' AND chan_id= ?1 AND create_time>= ?2 AND create_time < ?3"
            , nativeQuery = true)
    List<String> queryVerificationOrders(String chanId, Date start, Date end);

    /**
     * 长款（对于第三方来说，在对账数据中漏掉的数据）
     */
    @Query(value ="SELECT o.order_id FROM pe_order o LEFT JOIN pe_verification_order  vo ON o.chan_id=?1 AND o.outer_order_id=vo.order_id WHERE vo.order_id IS NULL AND o.create_time>= ?2 && o.create_time<?3",
            nativeQuery = true)
    List<String> queryExcessOrders(String chanId, Date start, Date end);
    /**
     * 漏单（对于金融机构来说，丢掉的数据）
     */
    @Query(value ="SELECT vo.order_id FROM pe_verification_order vo LEFT JOIN pe_order o ON vo.chan_id=?1 AND vo.outer_order_id=o.order_id WHERE o.`order_id`IS NULL AND vo.create_time>= ?2 && vo.create_time<?3",
            nativeQuery = true)
    List<String> queryMissOrders(String chanId, Date start, Date end);
    /**
     * 不一致的订单信息（两表已有表中，存在不同数据的行）
     */
    @Query(value ="SELECT o.order_id FROM pe_order o JOIN pe_verification_order vo ON o.chan_id=?1 AND o.outer_order_id=vo.order_id WHERE CONCAT_WS('|',o.chan_id,o.product_id,o.chan_user_id,o.order_type,o.amount,DATE_FORMAT(o.create_time,'%Y-%m-%d %H:%i:%s'))!= CONCAT_WS('|',vo.chan_id,vo.product_id,vo.chan_user_id,vo.order_type,vo.amount,DATE_FORMAT(vo.create_time,'%Y-%m-%d %H:%i:%s')) AND o.create_time>= ?2 && o.create_time<?3",
            nativeQuery = true)
    List<String> queryDifferentOrders(String chanId, Date start, Date end);


}
