package com.lzh;

import com.google.common.collect.Lists;
import com.lzh.entity.Subtitles;
import com.lzh.service.GifService;
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

	@Test
	public void contextLoads() {
	}

	@Test
	public void testRender() throws Exception {
		Subtitles subtitles = new Subtitles();
		subtitles.setTemplateName("sorry");
		subtitles.setSentence("");
		service.renderGif(subtitles);
	}
}
