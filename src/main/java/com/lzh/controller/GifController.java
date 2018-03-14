package com.lzh.controller;

import com.lzh.entity.Subtitles;
import com.lzh.service.GifService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lizhihao on 2018/3/11.
 */
@RestController
@RequestMapping("/")
public class GifController {

    @Autowired
    GifService service;

    @ApiOperation(value = "获取gif", notes = "")
    @RequestMapping(path = "/gif", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<Resource> renderGif(@RequestBody Subtitles subtitles) throws Exception {
        String file = service.renderGif(subtitles);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=txtx.gif").body(resource);
    }

}
