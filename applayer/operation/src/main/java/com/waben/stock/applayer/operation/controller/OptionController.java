package com.waben.stock.applayer.operation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/option")
public class OptionController {

    @RequestMapping("/index")
    public String index(){
        return "options/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public String pages(){
        return "aaa";
    }
}
