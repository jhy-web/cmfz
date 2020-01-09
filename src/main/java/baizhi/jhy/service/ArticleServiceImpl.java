package baizhi.jhy.service;

import baizhi.jhy.dao.ArticleDao;
import baizhi.jhy.entity.Article;
import baizhi.jhy.entity.ArticlePageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    //分页
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public ArticlePageDto selectPage(int pageNow, int pageSize) {
        ArticlePageDto articlePageDto = new ArticlePageDto();
        articlePageDto.setPage(pageNow);//设置当前页
        int totalCount = articleDao.selectTotalCount();//获取总条数
        articlePageDto.setRecords(totalCount);//设置总行数
        articlePageDto.setTotal(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);//设置总页数
        articlePageDto.setRows(articleDao.selectPage(pageNow, pageSize));//设置当前页的数据行。
        return articlePageDto;
    }


    //文件上传
    @Override
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request) {
        //获取http
        String scheme = request.getScheme();
        //获取IP
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();


        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/article/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = inputfile.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/article/" + newName;
        //文件上传
        try {
            inputfile.transferTo(new File(realPath, newName));
            //修改轮播图得信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }


    @Override
    public void addArticle(Article article) {
        articleDao.addArticle(article);
    }

    @Override
    public void deleteArticle(String id) {
        articleDao.deleteArticle(id);
    }

    @Override
    public void updateArticle(Article article) {
        articleDao.updateArticle(article);
    }
}
