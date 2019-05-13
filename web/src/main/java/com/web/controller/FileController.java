package com.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 文件上传
     * @param multipartFiles
     * @param request
     */
    @RequestMapping(value = "/upload")
    public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile[] multipartFiles,
                                 HttpServletRequest request){
        try {
            for (int i=0; i< multipartFiles.length; i++){
                // 输出源文件名称 就是指上传前的文件名称
                System.out.println("uploadFile:" + multipartFiles[i].getOriginalFilename());
                // 创建文件(MultipartFile转File)
                String saveRoot = "f:\\";
                File file = new File(saveRoot + multipartFiles[i].getOriginalFilename());
                InputStream in  = multipartFiles[i].getInputStream();
                OutputStream os = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int n;
                while ((n = in.read(buffer,0,4096)) != -1){
                    os.write(buffer,0,n);
                }
                in.close();
                os.close();
            }
            //输出其他字段
            String name = request.getParameter("name");
            System.out.println("name:"+name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/fileUpload")
    public String fileUpload(){
        return "fileUpload";
    }
}

