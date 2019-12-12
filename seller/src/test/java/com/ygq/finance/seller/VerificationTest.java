package com.ygq.finance.seller;

import com.ygq.finance.seller.service.VerificationOrderService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/10 0010
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerificationTest {

    @Autowired
    private VerificationOrderService verificationOrderService;
    /**
     * 测试对账文件的生成
     */
    @Test
    public void aTest() {
        Date time = new GregorianCalendar(2018, 11, 30).getTime();
        File file = verificationOrderService.makeVerificationFile("002", time);
        System.out.println(file.getAbsolutePath());

    }
    /**
     * 测试对账文件的解析
     */
    @Test
    public void bTest() {
        Date time = new GregorianCalendar(2018, 11, 30).getTime();
        verificationOrderService.saveVerifyCationFile("002", time);
    }

    /**
     * 对账
     */
    @Test
    public void cTest() {
        Date time = new GregorianCalendar(2018, 11, 30).getTime();
        List<String> list = verificationOrderService.verifyOrders("002", time);
        list.forEach(System.out::println);
    }
}
