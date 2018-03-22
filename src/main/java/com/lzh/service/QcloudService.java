package com.lzh.service;

import com.google.common.base.Preconditions;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

/**
 * @Description:
 * @author: lizhihao
 * @date: 2018/3/15 13:26
 */
@ConfigurationProperties(prefix = "qcloud")
@Setter
@Getter
@Service
public class QcloudService {

    private static final Logger logger = LoggerFactory.getLogger(QcloudService.class);

    private String accessKey ;

    private String secretKey ;

    private String bucket ;

    private String domainOfBucket;

    private String enable;

    public String upload (String localFilePath) {
        Preconditions.checkArgument("true".equals(enable),"不允许上传");

        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = bucket;
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = new File(localFilePath);
        // 指定要上传到 COS 上的路径
        String key = Paths.get(localFilePath).getFileName().toString();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        logger.info("result:{},{}", putObjectResult.getContentMd5(),putObjectResult.getETag());
        String finalUrl = String.format("%s/%s", domainOfBucket, key);
        logger.info("file url:{}", finalUrl);
        return finalUrl;
    }
}
