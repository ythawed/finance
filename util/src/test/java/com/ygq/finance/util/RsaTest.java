package com.ygq.finance.util;

import com.ygq.util.RsaUtil;
import org.junit.Test;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 *
 * rsa加密解密测试
 *  公钥与私钥在线生成
 *
 */
public class RsaTest {

    /**
     * 在线生成的私钥
     */
    private String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOi80LVvPNneW7//\n" +
            "EtQi53xrwW5u+BdXEW7tneNsGNze2Gi3yAiulm33nUyR19xQrT3WQeIObCICcyQM\n" +
            "m9kjVj1xZwWDIqNzNHWIO6xRM8wbAeSWKAHHihxXKhETPyYLQmWJKefw4qG62+d6\n" +
            "DIWwMjmVW5VnIVA8ZvO9e1ggn5wHAgMBAAECgYEA5Zkn2GWVxWUcZGtxmr+YWGFB\n" +
            "YP2uV/A4LZPSuAHzASoCmkP73bzGgFPP5TLXnewlDCTrt+P8fwylpEGgGfVHY9Jj\n" +
            "xYFFIqcLN7pjZDvIB6sUv7puV+OisyqcmpiwLyyG0pfhoAWnSwvoO8IPft9dOLO4\n" +
            "0GikdRRymqgZbMMzQAkCQQD4wnQa7A4KRwMn1AC3R46sw4uz4F2YFSMM1r2PsQP6\n" +
            "Z4DPNeqb7V4iMBN3O8lZP5Mr4dBSblz5k0+Xq+Tzr4u7AkEA74L6qDOnYbzGusAs\n" +
            "PneGZ9bXDktix+IjA+7BalZe3dwAbc0bUBcozxMy5MrtXzYO2cOPeFO5FD1EXr/D\n" +
            "NFmeJQJBAKCOCRgTQWCawsujBq9ErS6Oq412g5IUGmI/+2tRygqFYVrcJsdrJfWU\n" +
            "6Att+B/1a4zIG4gImVOwI4/2LzilFd8CQBvO0D1hKejeGxCpqTljdTZvKi7dr81X\n" +
            "Qwt81/X5qzAFEAOvkaE93x4Ts4/+w4qPYzADVY/54H4qEtsRVp+oXAUCQQDO2uuT\n" +
            "s8rPZ4Et/fiJAdeUpgW9J5Wiu6hCkZXPXfYcii8cxUMQzXWgy8WPjWUXHGfFQii5\n" +
            "YFFM9D047GDim3Dp";
    /**
     * 在线生成的公钥
     */
    private String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDovNC1bzzZ3lu//xLUIud8a8Fu\n" +
            "bvgXVxFu7Z3jbBjc3thot8gIrpZt951MkdfcUK091kHiDmwiAnMkDJvZI1Y9cWcF\n" +
            "gyKjczR1iDusUTPMGwHkligBx4ocVyoREz8mC0JliSnn8OKhutvnegyFsDI5lVuV\n" +
            "ZyFQPGbzvXtYIJ+cBwIDAQAB";


    @Test
    public void test() {
        //待加密的文本
        String text = "{\"amount\":100,\"chanId\":\"002\",\"chanUserId\":\"1014\",\"createTime\":\"2019-12-364 12:17:53\",\"memo\":\"come again\",\"outerOrderId\":\"9410\",\"productId\":\"T001\"}";
        //加密后的文本
        String sign = RsaUtil.sign(text, PRIVATE_KEY);
        System.out.println(sign);

        //解密的结果
        boolean verify = RsaUtil.verify(text, sign, PUBLIC_KEY);
        System.out.println(verify);

    }

}
