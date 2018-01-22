package com.waben.stock.applayer.operation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aliturn")
public class AliturnController {

    @RequestMapping("/index")
    public String index() {
        return "publisher/aliturn/index";
    }

}
