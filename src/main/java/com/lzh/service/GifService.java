package com.lzh.service;

import com.lzh.entity.Subtitles;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lizhihao on 2018/3/11.
 */
@Service
public class GifService {

    private static final Logger logger = LoggerFactory.getLogger(GifService.class);

    public static final String tempPath = "static/cache/";

    @RequestMapping(path = "/")
    public void renderGif(Subtitles subtitles) throws Exception {
        String assPath = renderAss(subtitles);
        String gifPath = tempPath+ UUID.randomUUID()+".gif";
        ClassPathResource classPathResource = new ClassPathResource("/static/" + subtitles.getTemplateName());
        String videoPath = classPathResource.getFile().getAbsolutePath() + "/template.mp4";
        String cmd = String.format("G:\\ffmpeg\\ffmpeg -i %s -r 8 -vf \"ass=%s,scale=300:-1\" -y %s", videoPath, assPath, gifPath);
        logger.info("cmd: {}", cmd);
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            exec.waitFor();
        } catch (Exception e) {
            logger.error("生成fig报错：{}", e);
        }
    }

    public String renderAss(Subtitles subtitles) throws Exception {

        File file = new File(tempPath+UUID.randomUUID().toString().replace("-","")+".ass");

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDefaultEncoding("UTF-8");
        ClassPathResource classPathResource = new ClassPathResource("/static/" + subtitles.getTemplateName());
        cfg.setDirectoryForTemplateLoading(classPathResource.getFile());
        Map<String, Object> root = new HashMap<>();
        Map<String, String> mx = new HashMap<>();

        for (int i = 0; i < subtitles.getSentence().size(); i++) {
            mx.put("sentences" + i, subtitles.getSentence().get(i));
        }
        root.put("mx", mx);
        Template temp = cfg.getTemplate("template.ftl");
        try (FileWriter writer = new FileWriter(file);) {
            temp.process(root, writer);
        } catch (Exception e) {
            logger.error("生成ass文件报错", e);
        }
        return file.getPath();
    }
}
