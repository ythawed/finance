package com.ygq.finance.seller.service;

import com.ygq.finance.entity.VerificationOrder;
import com.ygq.finance.entity.enums.ChanEnum;
import com.ygq.finance.seller.repository.VerificationOrderRepository;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/10 0010
 */
@Service
public class VerificationOrderService {

    private static Logger LOG = LoggerFactory.getLogger(VerificationOrderService.class);

    @Autowired
    private VerificationOrderRepository verificationRepository;
    /**
     * 指定默认路径为工程/verification/data
     */
    @Value("${verification.rootDir:F:/javaPlugins/verification/data}")
    private String rootDir;
    /**
     * 时间格式
     */
    private static DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat DAYTIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String END_LINE = System.getProperty("line.separator", "\n");


    /**
     * 生成某个渠道在某个时间段的对账数据
     */
    public File makeVerificationFile(String chanId, Date day) {

        // 获取对账数据文件的存放位置
        File file = getPath(rootDir, chanId, day);
        //如果文件已经存在
        if (file.exists()) {
            //直接返回
            return file;
        }
        //文件不存在
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //构造当前时间的时间段起止时间
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        List<String> orders = verificationRepository.queryVerificationOrders(chanId, start, end);

        LOG.info("对账文件结果，结果={}", orders);


        //将各个订单之间用换行符隔开
        String result = String.join(END_LINE, orders);
        //将结果写入文本
        FileUtil.writeAsString(file, result);
        return file;
    }

    /**
     * 保存订单对账数据
     */
    public void saveVerifyCationFile(String chanId, Date day) {
        //获取渠道配置的路径
        ChanEnum conf = ChanEnum.getByChanId(chanId);
        File file = getPath(conf.getRootDir(), chanId, day);
        //不存在该路径，则表示没有对账数据，不需要对账，则直接返回
        if (!file.exists()) {
            return;
        }
        String content = null;
        try {
            content = FileUtil.readAsString(file);
            System.out.println(content);
            System.out.println("=========================================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //按行切分
        String[] lines = content.split(END_LINE);
        //切分每行的属性
        List<VerificationOrder> orders = new ArrayList<>();
        for (String line : lines) {
            //切分
            orders.add(parseLine(line));
        }
        verificationRepository.save(orders);
    }

    /**
     * 对账
     */
    public List<String> verifyOrders(String chanId, Date day) {
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        //长款
        List<String> excessOrders = verificationRepository.queryExcessOrders(chanId, start, end);
        //漏单
        List<String> missOrders = verificationRepository.queryMissOrders(chanId, start, end);
        //差异
        List<String> differentOrders = verificationRepository.queryDifferentOrders(chanId, start, end);
        //收集结果
        List<String> errors = new ArrayList<>();
        errors.add("长款订单号:" + String.join(",", excessOrders));
        errors.add("漏单订单号:" + String.join(",", missOrders));
        errors.add("差异订单号:" + String.join(",", differentOrders));
        return errors;
    }


    /**
     * 按查询结果的属性顺序设置order属性
     * order_id,chan_id,product_id,chan_user_id,order_type,outer_order_id,amount,DATE_FORMAT(create_time
     */
    private VerificationOrder parseLine(String line) {
        VerificationOrder order = new VerificationOrder();
        //切分，获得属性
        String[] props = line.split("\\|");

        //******注意属性设置的顺序，因为verification_order与order两表根据属性对账。
        //因为orderId与outOrderId两个属性，生成对账文件后，有
        order.setOuterOrderId(props[0]);
        order.setChanId(props[1]);
        order.setProductId(props[2]);
        order.setChanUserId(props[3]);
        order.setOrderType(props[4]);
        order.setOrderId(props[5]);
        order.setAmount(new BigDecimal(props[6]));
        try {
            //将字符串转成时间格式。去掉小冒号
            // 2018-12-30 20:17:53'
            Date date = DAYTIME_FORMAT.parse(props[7]);
            order.setCreateTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LOG.info("解析每条订单的属性，res={}",order);
        return order;

    }

    /**
     * 结束时间为start + 24h
     */
    private Date add24Hours(Date start) {
        return new Date(start.getTime() + 1000 * 60 * 60 * 24);
    }

    /**
     * 转化当前时间为起始时间
     */
    private Date getStartOfDay(Date day) {
        String startStr = DAY_FORMAT.format(day);
        Date start = null;
        try {
            start = DAY_FORMAT.parse(startStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return start;
    }

    /**
     * 获取对账文件的路径
     * @param rootDir 文件目录
     * @param chanId 渠道id
     * @param day 当前时间
     * @return 对账文件路径
     */
    private File getPath(String rootDir, String chanId, Date day) {
        String name = DAY_FORMAT.format(day) + "-" + chanId + ".txt";
        File path = Paths.get(rootDir, name).toFile();
        return path;
    }

}
