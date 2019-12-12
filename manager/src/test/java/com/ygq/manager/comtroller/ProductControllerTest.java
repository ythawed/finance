package com.ygq.manager.comtroller;

import com.ygq.finance.entity.Product;
import com.ygq.finance.entity.enums.ProductStatus;
import com.ygq.manager.repository.ProductRepository;
import com.ygq.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/3 0003
 *
 * 测试ProductController类
 * 三个必要注解
 * 1. 运行类
 * 2. 测试环境：指定一个随机端口测试即可
 * 3. 测试方法的执行顺序指定:使用方法名称字典序执行，所以测试方法名称会决定执行顺序
 *
 * 完成后，gradle-当前工程-tasks-build-build 进行自动执行测试
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    // 引用测试模板
    private static RestTemplate restTemplate=new RestTemplate();

    //测试方法的访问路径
    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //测试内容
    //1 .正常数据
    private static List<Product> normals = new ArrayList<>();
    //2. 异常数据
    private static List<Product> exceptions = new ArrayList<>();

    @Autowired
    private ProductRepository repository;

    /**
     * 初始化测试数据：
     *                  产品的必备属性
     * ****用BeforeClass注解初始化
     */
    @BeforeClass
    public static void init() {

        Product p1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "活期盈-金色人生", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "朝朝盈-聚财", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);
        /**
         * 不合规数据
         */
        Product e1 = new Product(null, "产品编号不能为空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.34));
        Product e2 = new Product("E002", "收益率范围应该为0<rate<=30", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "投资步长应为整数", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.44));
        exceptions.add(e1);
        exceptions.add(e2);
        exceptions.add(e3);
        /**
         * 对应异常数据的操作
         */
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
        //设置测试模板
        restTemplate.setErrorHandler(errorHandler);
    }

    /**
     * 测试正常数据
     */
    @Test
    public void aCorrectDataTest() {
        // 循环插入设置的数据，进行测试
        normals.forEach(product -> {
            //调用测试数据对应的方法，将其返回结果转成json
            //正常插入后的响应结果应该是product，异常时，则不是product
            Product result = RestUtil.postJSON(restTemplate, baseUrl + "/products", product, Product.class);
            //如果插入的响应结果含有创建时间，则表示插入成功，如果不存在创建时间，则表示插入失败
            Assert.notNull(result.getCreateTime(), "插入失败");
        });
    }
    /**
     * 测试插入异常数据
     */
    @Test
    public void bFaultDataTest() {
        exceptions.forEach(product -> {
            //插入异常，返回的事json对，也就是k-v信息，这里使用map收集
            HashMap<String, String> result = RestUtil.postJSON(restTemplate, baseUrl + "/products", product, HashMap.class);
            //响应信息:{"error":"Internal Server Error","exception":"java.lang.IllegalArgumentException",
            // "message":"投资步长应为整数","path":"/manager/products","status":500,"timestamp":1575380439206}
            //从上可以看出，异常会有error作为key响应,所以我们根据相应结果是否含error来判断插入是否异常
            //也可以进一步利用产品名称更进一步定位异常，也就是出错产品与返回的插入异常产品名称是否相同
            Assert.notNull(result.get("message").equals(product.getName()), "插入成功");
        });
    }

    @Test
    public void cQueryTest() {
        //查询正常数据
        normals.forEach(product -> {
            Product result = restTemplate.getForObject(baseUrl + "/products/" + product.getId(), Product.class);
            Assert.isTrue(product.getId().equals(result.getId()),"查询失败");
        });
        exceptions.forEach(product -> {
            Product result = restTemplate.getForObject(baseUrl + "/products/" + product.getId(), Product.class);
            Assert.isNull(result,"查询失败");
        });
    }

}
