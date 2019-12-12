package com.ygq.finance.seller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ythawed
 * @date 2019/12/7 0007
 */
@Component
public class HazelcastTest {

    /**
     * 红线无所谓，idea的原因
     */
    @Autowired()
    private HazelcastInstance hazelcastInstance;


    @PostConstruct
    public void test() {
        IMap<Object, Object> map = hazelcastInstance.getMap("ygq");
        map.put("user", "ygq");

    }


}
