package me.wuwenbin.blog.v201801;

import cn.hutool.http.HtmlUtil;
import me.wuwenbin.blog.v201801.repository.AboutRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.HtmlUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private AboutRepository aboutRepository;
    @Test
    public void contextLoads() {
        System.out.println(aboutRepository.findByTab("about_me"));
    }

}
