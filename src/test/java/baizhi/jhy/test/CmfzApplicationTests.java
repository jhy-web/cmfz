package baizhi.jhy.test;

import baizhi.jhy.dao.AdminDao;
import baizhi.jhy.entity.Admin;
import baizhi.jhy.entity.ArticlePageDto;
import baizhi.jhy.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CmfzApplicationTests {

    @Autowired
    AdminDao adminDao;

    @Autowired
    ArticleService articleService;

    @Test
    void login() {
        Admin admin = new Admin(null, "admin", "admin");
        Admin admin1 = adminDao.selectOne(admin);
        System.out.println(admin1);
    }

    @Test
    void page() {
        ArticlePageDto articlePageDto = articleService.selectPage(1, 3);
        System.out.println(articlePageDto);
    }

}
