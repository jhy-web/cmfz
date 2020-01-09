package baizhi.jhy.controller;

import baizhi.jhy.entity.Admin;
import baizhi.jhy.service.AdminService;
import baizhi.jhy.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public Map selectByUsername(Admin admin, String enCode, HttpSession session) {
        Map map = adminService.queryAdmin(admin, enCode, session);
        return map;
    }

    //退出登录
    @RequestMapping("/loginOut")
    public String loggedOut(HttpSession session) {
        session.invalidate();
        return "redirect:/jsp/login.jsp";
    }

    //验证码
    @RequestMapping("creatImage")
    public void creatImage(HttpServletResponse response, HttpSession session) {
        //获取随机的验证码
        String securityCode = ImageCodeUtil.getSecurityCode();
        //将获取的随机验证码存储起来
        session.setAttribute("code", securityCode);
        //创建图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        try {
            //将图片以IO流的形式写出去
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
