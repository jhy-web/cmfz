package baizhi.jhy.controller;

import baizhi.jhy.entity.Album;
import baizhi.jhy.entity.AlbumPageDto;
import baizhi.jhy.service.AlbumService;
import baizhi.jhy.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    //分页
    @RequestMapping("querypage")
    public AlbumPageDto queryPage(Integer page, Integer rows) {//固定
        return albumService.selectPage(page, rows);
    }

    //实现jqgrid 的 增删改
    @RequestMapping("/subgrid")
    //参数：oper 代表增删改的具体操作 类型，名称，固定（qgrid 封装好的参数）
    public Map grid(Album album, String oper) {
        HashMap map = new HashMap();
        if ("add".equals(oper)) {
            album.setId(UUID.randomUUID().toString());
            albumService.addAlbum(album);
            map.put("albumId", album.getId());
        } else if ("edit".equals(oper)) {
            //判断是否修改图片
            //如果没有修改图片，则图片为空
            if (album.getDes() == "") {
                album.setDes(null);
            }
            albumService.updateAlbum(album);
            map.put("albumId", album.getId());
        } else {
            albumService.deleteAlbum(album.getId());
        }
        return map;
    }

    //文件上传
    @RequestMapping("/upload")
    public void upload(MultipartFile des, String albumId, HttpSession session, HttpServletRequest request) {
        //调用工具类
        String http = HttpUtil.getHttp(des, request, "/upload1/");
        //文件上传
        //更新数据库信息
        Album ablum = new Album();
        ablum.setId(albumId);
        ablum.setDes(http);
        //调用修改的方法  修改绝对路径
        albumService.updateAlbum(ablum);
    }

}