package com.lzh;

import com.google.common.collect.Lists;
import com.lzh.entity.Subtitles;
import com.lzh.service.GifService;
import com.lzh.service.QcloudService;
import com.lzh.service.QiniuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SorryJavaApplicationTests {

	@Autowired
	GifService service;

	@Autowired
	QiniuService qiniuService;

	@Autowired
	QcloudService qcloudService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testRender() throws Exception {
		Subtitles subtitles = new Subtitles();
		subtitles.setTemplateName("sorry");
		subtitles.setSentence(Lists.newArrayList("哈哈","哈哈","哈哈","哈哈","哈哈","哈哈","哈哈","哈哈","哈哈"));
		service.renderGif(subtitles);
	}

	@Test
	public void testQiniuUpload() throws Exception {
		qiniuService.upload("E:\\out.gif");
	}

	@Test
	public void testQcloudUpload() throws Exception {
		qcloudService.upload("E:\\out.gif");
	}
}
