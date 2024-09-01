package com.example.activityservices.until;



import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


public class UploadUntil {

    public static final String DOMAIN = "https://test-01234.oss-cn-wulanchabu.aliyuncs.com/";
//
    public static String uploadFile(MultipartFile file){
        String oldFileName = file.getOriginalFilename();
        String ext = "." + FilenameUtils.getExtension(oldFileName);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;

        String endpoint = "https://oss-cn-wulanchabu.aliyuncs.com";
        String accessKeyId = "LTAI5t7dS6mEugZrSLqsk1sc";
        String accessKeySecret = "SBIw1Ogriz9Qrevs57GwoDyTuS7Fcx";
        String bucketName = "test-01234";

        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        try {
            ossClient.putObject(bucketName,fileName,file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            ossClient.shutdown();
        }
        return DOMAIN + fileName;
    }

}

