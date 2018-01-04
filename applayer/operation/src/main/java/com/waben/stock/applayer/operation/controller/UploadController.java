package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.UploadBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private UploadBusiness uploadBusiness;

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request){
        String resultPath = "";
        try {
           resultPath =  uploadBusiness.upload(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return resultPath;
    }

}
