package com.web.controller;

import com.commonutils.util.json.JSONObject;
import com.commonutils.util.validate.FileTypeCensor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@RequestMapping(value = "/file")
public class FileController {
    /**
     * 文件上传
     * @param multipartFiles
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")

    public String uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile[] multipartFiles,
                                 String param,
                                 HttpServletRequest request){
        JSONObject result = new JSONObject();
        try {
            for (int i=0; i< multipartFiles.length; i++){
//                boolean upFlag = FileTypeCensor.isLegalFileType(FileTypeCensor.FILE_SUFFIX.IMAGE.getValue(),multipartFiles[i]);
//                if(!upFlag){
//                    throw new Exception(FileTypeCensor.FILE_SUFFIX.IMAGE.getDesc()+"必须为"+FileTypeCensor.FILE_SUFFIX.IMAGE.getValue());
//                }

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
            System.out.println("param:"+ URLDecoder.decode(param,"UTF-8"));
        } catch (Exception e) {
            result.put("msg", e.getMessage());
            result.put("flag", "fail");
        }
        result.put("flag", "success");
        result.put("msg", "success");

        return result.toString();
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downFile")
    public void downTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File("f:\\test.txt");//测试文件
        if (!file.exists()) {
            System.out.println("文件不存在!");
        } else {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            // 读取要下载的文件，保存到输入流
            InputStream in = new FileInputStream(file);
            // 创建输出流
            OutputStream out = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            // 关闭文件输入流
            in.close();
            // 关闭输出流
            out.close();
        }
    }

    /**
     * 图片上传
     * @param multipartFiles
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")

    public String uploadImg(@RequestParam(value = "uploadFile", required = false) MultipartFile[] multipartFiles,
                             HttpServletRequest request){
        JSONObject result = new JSONObject();
        try {
            for (int i=0; i< multipartFiles.length; i++){
                BufferedImage image = ImageIO.read(multipartFiles[i].getInputStream());
                if(image == null){
                    throw new Exception("请上传图片");
                }

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
            System.out.println(e.getMessage());
            result.put("msg", e.getMessage());
            result.put("flag", "fail");
        }
        result.put("flag", "success");
        result.put("msg", "success");

        return result.toString();
    }

    /**
     * excel上传
     * @param multipartFiles
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/excelUpload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")

    public String uploadExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile[] multipartFiles,
                            HttpServletRequest request){
        JSONObject result = new JSONObject();
        try {
            for (int i=0; i< multipartFiles.length; i++){
                Workbook wb = WorkbookFactory.create(multipartFiles[i].getInputStream());

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
            System.out.println(e.getMessage());
            result.put("msg", e.getMessage());
            result.put("flag", "fail");
        }
        result.put("flag", "success");
        result.put("msg", "success");

        return result.toString();
    }
}

