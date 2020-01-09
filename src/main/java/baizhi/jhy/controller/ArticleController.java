package baizhi.jhy.controller;

import baizhi.jhy.entity.Article;
import baizhi.jhy.entity.ArticlePageDto;
import baizhi.jhy.service.ArticleService;
import baizhi.jhy.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    //分页
    @RequestMapping("queryPage")
    public ArticlePageDto queryPage(Integer page, Integer rows) {//固定
        return articleService.selectPage(page, rows);
    }

    //实现jqgrid 的 增删改
    @RequestMapping("/jqgrid")
    //参数：oper 代表增删改的具体操作 类型，名称，固定（qgrid 封装好的参数）
    public Map grid(Article article, String oper) {
        HashMap map = new HashMap();
        if ("add".equals(oper)) {
            article.setId(UUID.randomUUID().toString());
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.addArticle(article);
            map.put("articleId", article.getId());
        } else if ("edit".equals(oper)) {
            //判断是否修改图片
            //如果没有修改图片，则图片为空
            if (article.getImg() == "") {
                article.setImg(null);
            }
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.updateArticle(article);
            map.put("articleId", article.getId());
        } else {
            articleService.deleteArticle(article.getId());
        }
        return map;
    }

    //文件上传
    @RequestMapping("/upload")
    public void upload(MultipartFile img, String articleId, HttpServletRequest request) {
        //调用工具类
        String http = HttpUtil.getHttp(img, request, "/uploadArt/");
        //文件上传
        //更新数据库信息
        Article article = new Article();
        article.setId(articleId);
        article.setImg(http);
        //调用修改的方法  修改绝对路径
        articleService.updateArticle(article);
    }


    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        hashMap.put("current_url", request.getContextPath() + "/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count", files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype", extension);
            fileMap.put("filename", name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime", format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list", arrayList);
        return hashMap;
    }

    //添加
    @RequestMapping("insertArticle")
    public String insertArticle(Article article, MultipartFile inputfile, String id, HttpServletRequest request) {
        if (article.getId() == null || "".equals(article.getId())) {

            // insert 添加
            String uri = articleService.articleUploa(inputfile, id, request);
            article.setImg(uri);

            article.setId(UUID.randomUUID().toString());
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.addArticle(article);
        } else {
            // update 修改
            String uri = articleService.articleUploa(inputfile, id, request);
            article.setImg(uri);

            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.updateArticle(article);
        }
        System.out.println(article);
        System.out.println(inputfile);
        return "ok";
    }
}