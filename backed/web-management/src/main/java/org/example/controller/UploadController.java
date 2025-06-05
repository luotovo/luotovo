package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result upload(String name, Integer age,MultipartFile file) throws IOException {
        //若file和前端的name一致，则不需要@RequestParam
        log.info("文件上传：{}，{}，{}",name,age,file); 
        //保存文件
        String originalFilename = file.getOriginalFilename();//1.jpg
        //新的文件名
        String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
        //截取原始文件名的后缀
        String newFileName = UUID.randomUUID().toString()+extension;
        file.transferTo(new File("D:/upload/"+newFileName));
        return Result.success();

    }
}
