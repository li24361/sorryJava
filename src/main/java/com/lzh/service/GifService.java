package com.lzh.service;

import com.lzh.entity.Subtitles;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lizhihao on 2018/3/11.
 */
@Service
@Getter
@Setter
@ConfigurationProperties(prefix = "cache.template")
public class GifService {

    private static final Logger logger = LoggerFactory.getLogger(GifService.class);

    private String tempPath;

    public String renderGif(Subtitles subtitles) throws Exception {
        String assPath = renderAss(subtitles);
        String gifPath = Paths.get(tempPath).resolve(UUID.randomUUID() + ".gif").toString();
        String videoPath = Paths.get(tempPath).resolve(subtitles.getTemplateName()+"/template.mp4").toString();
        String cmd = String.format("ffmpeg -i %s -r 8 -vf ass=%s,scale=300:-1 -y %s", videoPath, assPath, gifPath);
        logger.info("cmd: {}", cmd);
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            exec.waitFor();
        } catch (Exception e) {
            logger.error("生成gif报错：{}", e);
        }
        return gifPath;
    }

    private String renderAss(Subtitles subtitles) throws Exception {
        Path path = Paths.get(tempPath).resolve(UUID.randomUUID().toString().replace("-", "") + ".ass");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(Paths.get(tempPath).resolve(subtitles.getTemplateName()).toFile());
        Map<String, Object> root = new HashMap<>();
        Map<String, String> mx = new HashMap<>();

        for (int i = 0; i < subtitles.getSentence().size(); i++) {
            mx.put("sentences" + i, subtitles.getSentence().get(i));
        }
        root.put("mx", mx);
        Template temp = cfg.getTemplate("template.ftl");
        try (FileWriter writer = new FileWriter(path.toFile())) {
            temp.process(root, writer);
        } catch (Exception e) {
            logger.error("生成ass文件报错", e);
        }
        return path.toString();
    }


}
