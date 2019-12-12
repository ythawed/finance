package com.ygq.finance.seller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ythawed
 * @date 2019/12/6 0006
 */
@Controller
public class Test {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "say hello to seller";
    }
}
