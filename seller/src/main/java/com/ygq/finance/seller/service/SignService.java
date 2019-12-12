package com.ygq.finance.seller.service;

import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author ythawed
 * @date 2019/12/9 0009
 * RSA 验签服务。本类不被controller调用，而是在signAop类被调用
 */
@Service
public class SignService {

    static Map<String, String> keyMap = new Hashtable<>();
    static {
//        key本处为手动写死，实际需自动生成。value为在线生成的公钥
        keyMap.put("001", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDovNC1bzzZ3lu//xLUIud8a8Fu\n" +
                "bvgXVxFu7Z3jbBjc3thot8gIrpZt951MkdfcUK091kHiDmwiAnMkDJvZI1Y9cWcF\n" +
                "gyKjczR1iDusUTPMGwHkligBx4ocVyoREz8mC0JliSnn8OKhutvnegyFsDI5lVuV\n" +
                "ZyFQPGbzvXtYIJ+cBwIDAQAB");
    }

    /**
     * 根据验签id获取对应公钥
     * @param authId 授权编号
     * @return 对应公钥
     */
    public String getPublicKey(String authId) {
        return keyMap.get(authId);
    }
}
