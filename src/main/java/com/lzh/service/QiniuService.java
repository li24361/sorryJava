package com.lzh.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * @Description:
 * @author: lizhihao
 * @date: 2018/3/15 13:26
 */
@ConfigurationProperties(prefix = "qiniu")
@Setter
@Getter
@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    private String accessKey ;

    private String secretKey ;

    private String bucket ;

    private String domainOfBucket;

    public String upload (String localFilePath) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "/home/qiniu/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = Paths.get(localFilePath).getFileName().toString();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            logger.info("key:{} hash:{}", putRet.key, putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
        }
        String finalUrl = String.format("%s/%s", domainOfBucket, key);
        logger.info("file url:{}", finalUrl);
        return finalUrl;
    }
}
