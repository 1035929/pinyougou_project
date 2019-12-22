package com.pinyougou.shop.controller;

import com.pinyougou.common.FastDFSClient;
import com.pinyougou.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    //获取properties文件中的内容
    @Value("${FILE_SERVER_URL}")
    private String file_server_url;

    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file){
        //1.调用工具类
        try {
            FastDFSClient client=new FastDFSClient("classpath:config/fdfs_client.conf");
            //2获取上传文件的后缀名
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            //3.执行上传,后缀名要和上传文件保持一致，返回file_id图片地址
            String uploadFile = client.uploadFile(file.getBytes(), extName);
            //4.获取全部图片存储路径地址
            String url=file_server_url+uploadFile;
            //5.返回信息
            return new Result(true, url);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }
}
