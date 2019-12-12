package com.ygq.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author ythawed
 * @date 2019/12/3
 */
@Controller
public class Test {

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "Say Hello";
    }

}
