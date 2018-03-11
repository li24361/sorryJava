package com.lzh.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by lizhihao on 2018/3/11.
 */
@Controller
public class GifController {

    public ResponseEntity<Resource> renderGif() throws UnsupportedEncodingException {
        File file = null;
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + new String(file.getName().getBytes("utf-8"), "iso-8859-1") + "\"").body(resource);
    }

}
