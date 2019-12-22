package com.pinyougou.demo;

import org.csource.fastdfs.*;

public class Demo {

    public static void main(String[] args) throws Exception {
        //1.读取加载fafs_client.conf,路径必须是全路径
        ClientGlobal.init("D:\\JavaCode2\\pinyougou_project\\FastDFSDemo\\src\\main\\resources\\fdfs_client.conf");
        //2.获取trackerclient客户端
        TrackerClient client = new TrackerClient();
        //3.获取trackerClient服务端
        TrackerServer trackerServer =client.getConnection();
        //4.获取storageService服务端
        StorageServer storageServer = null;
        //5.获取storageClient客户端
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //6.上传，病返回file_id也就是url地址
        String[] jpgs = storageClient.upload_file("E:\\品优购\\79期品优购加密资料\\品优购资源V1.3\\图片库\\1.jpg", "jpg", null);
        for (String string : jpgs) {
            System.out.println(string);
        }

    }
}
