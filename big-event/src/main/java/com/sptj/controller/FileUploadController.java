package com.sptj.controller;

import com.sptj.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        //
        System.out.println("进入upload 1");
        String filePath = "D:\\bigeventfiles\\";
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            file.transferTo(new File(filePath + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return Result.success("url访问地址...");
        System.out.println("http://localhost:8080/big-event/" + filename);
        return Result.success("http://localhost:8080/big-event/" + filename);

    }
}
