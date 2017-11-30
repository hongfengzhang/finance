package com.waben.stock.applayer.operation.controller;

import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/circular")
public class CircularController {

    @GetMapping("/index")
    public String index(HttpSession httpSession) {
        List<MenuDto> menuDtos = (List<MenuDto>) httpSession.getAttribute("menus");
        System.out.printf(JacksonUtil.encode(menuDtos));
        return "manage/circular/bulletin";
    }

}
