package baizhi.jhy.controller;

import baizhi.jhy.entity.Chapter;
import baizhi.jhy.entity.ChapterPageDto;
import baizhi.jhy.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;
    String ids = null;

    //分页
    @RequestMapping("querypage")
    public ChapterPageDto queryPage(String ids, Integer page, Integer rows) {//固定
        return chapterService.selectPage(ids, page, rows);
    }

    //实现jqgrid 的 增删改
    @RequestMapping("/subgrid")
    //参数：oper 代表增删改的具体操作 类型，名称，固定（qgrid 封装好的参数）
    public Map grid(Chapter chapter, String oper, String ids) {
        //截取id的前36位
        String ids1 = ids.substring(0, 36);
        HashMap map = new HashMap();
        if ("add".equals(oper)) {
            chapter.setId(UUID.randomUUID().toString());
            chapter.setCreateDate(new Date());
            chapter.setAlbumId(ids1);
            chapterService.addChapter(chapter);
            map.put("chapterId", chapter.getId());
        } else if ("edit".equals(oper)) {
            System.out.println(chapter);
            chapter.setCreateDate(new Date());
            chapter.setAlbumId(ids);
            chapterService.updateChapter(chapter);
            map.put("chapterId", chapter.getId());
        } else {
            System.out.println(chapter);
            chapterService.deleteChapter(chapter.getId());
        }
        return map;
    }

    //文件上传
    @RequestMapping("/upload")
    public void upload(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) {
        //获取真实路径
        System.out.println("url" + url);
        String realPath = session.getServletContext().getRealPath("/upload2/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }

        String filename = url.getOriginalFilename();
        //从新给图片命名
        String newName = new Date().getTime() + "-" + filename;
        System.out.println("新的名字为" + newName);
        //3.文件上传
        try {
            url.transferTo(new File(realPath, newName));
            //获取文件大小   B 字节  KB MB GB TB   1024
            long size = url.getSize();
            String s = String.valueOf(size);
            Double aDouble = Double.valueOf(size) / 1024 / 1024;
            DecimalFormat format = new DecimalFormat("0.00");
            String sizes = format.format(aDouble) + "MB";
            //获取文件的时长  秒
            AudioFile audioFile = AudioFileIO.read(new File(realPath, newName));
            AudioHeader audioHeader = audioFile.getAudioHeader();
            //获取的秒
            int length = audioHeader.getTrackLength();
            String duration = length / 60 + "分" + length % 60 + "秒";

            //修改轮播图信息
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl(newName);
            chapter.setSize(sizes);
            chapter.setTime(duration);
            chapter.setCreateDate(new Date());
            //调用修改的方法  修改绝对路径
            System.out.println("chapter" + chapter);
            chapterService.updateChapter(chapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //文件下载
    @RequestMapping("download")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println(url);
        // 处理url路径 找到文件
        /*String[] split = url.split("/");*/
        String realPath = session.getServletContext().getRealPath("/upload2/");
        /* String name = split[split.length-1];*/
        File file = new File(realPath, url);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename=" + url);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file, outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        //FileUtils.copyFile();
    }

}

