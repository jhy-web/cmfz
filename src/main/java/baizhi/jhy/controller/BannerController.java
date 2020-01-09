package baizhi.jhy.controller;

import baizhi.jhy.entity.Banner;
import baizhi.jhy.entity.BannerPageDto;
import baizhi.jhy.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    //分页
    @RequestMapping("queryPage")
    //当前页和每页显示的行数 必须是page 和 rows （jqgrid 封装好的参数）
    public BannerPageDto queryPage(Integer page, Integer rows) {//固定
        return bannerService.selectPage(page, rows);
    }

    //实现jqgrid 的 增删改
    @RequestMapping("/jqgrid")
    //参数：oper 代表增删改的具体操作 类型，名称，固定（qgrid 封装好的参数）
    public Map grid(Banner banner, String oper) {
        HashMap map = new HashMap();
        if ("add".equals(oper)) {
            banner.setId(UUID.randomUUID().toString());
            bannerService.addBanner(banner);
            map.put("bannerId", banner.getId());
        } else if ("edit".equals(oper)) {
            //判断是否修改图片
            //如果没有修改图片，则图片为空
            if (banner.getUrl() == "") {
                banner.setUrl(null);
            }
            bannerService.updateBanner(banner);
            map.put("bannerId", banner.getId());
        } else {
            bannerService.deleteBanner(banner.getId());
        }
        return map;
    }

    //文件上传
    @RequestMapping("/upload")
    public void upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) {
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        //2.获取文件名
        String filename = url.getOriginalFilename();
        //重新给图片命名
        String newName = new Date().getTime() + "-" + filename;
        //3.文件上传
        try {
            url.transferTo(new File(realPath, newName));
            //修改轮播图信息
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(newName);
            //调用修改的方法  修改绝对路径
            bannerService.updateBanner(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用工具类
        /* String http = HttpUtil.getHttp(url, request, "/upload/");*/
        //文件上传
        //更新数据库信息
       /* Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(http);
        bannerService.updateBanner(banner);*/
    }

}
