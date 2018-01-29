package com.waben.stock.applayer.operation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.waben.stock.applayer.operation.business.UploadBusiness;

@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private UploadBusiness uploadBusiness;

    @PostMapping("/upload")
    public String upload(@RequestParam("uploadFile") MultipartFile file){
        String resultPath = "";
        try {
           resultPath =  uploadBusiness.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return resultPath;
    }

}
