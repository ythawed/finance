package com.ygq.finance.seller.task;

import com.ygq.finance.entity.enums.ChanEnum;
import com.ygq.finance.seller.service.VerificationOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ythawed
 * @date 2019/12/11 0011
 *
 * 定时对账
 * 还需在启动类注解@
 * cron表达式生成: http://cron.qqe2.com/
 */
@Component
public class VerifyOrderTask {

    @Autowired
    private VerificationOrderService verificationOrderService;

    /**
     * 测试：每3秒执行一次输出
     */
    @Scheduled(cron = "0/3 * * * * ? ")
    public void test() {
//        System.out.println("hello!");
    }

    /**
     * 每天凌晨1,3,5点按照chan_id执行对账文件的生成与存库
     */
    @Scheduled(cron = "0 0 1,3,5 * * ? ")
    public void makeVerificationFile() {
        //时间，昨天的这个时间账单
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        for (ChanEnum value : ChanEnum.values()) {
            //生成对账文件
            verificationOrderService.makeVerificationFile(value.getChanId(), yesterday);
            //解析并保存到数据库
            verificationOrderService.saveVerifyCationFile(value.getChanId(), yesterday);
        }
    }
    /**
     * 每天凌晨2,4,6点按照chan_id执行读库数据，进行对账
     */
    @Scheduled(cron = "0 0 2,4,6 * * ? ")
    public void verifyOrder() {
        //时间
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        for (ChanEnum value : ChanEnum.values()) {
            //读库数据，进行对账
            verificationOrderService.verifyOrders(value.getChanId(), yesterday);
        }
    }

}
