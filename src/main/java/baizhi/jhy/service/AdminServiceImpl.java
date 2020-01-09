package baizhi.jhy.service;

import baizhi.jhy.dao.AdminDao;
import baizhi.jhy.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;


    @Override
    public Map queryAdmin(Admin admin, String enCode, HttpSession session) {
        HashMap map = new HashMap();
        // 2. 构建查询条件
        admin.setUsername(admin.getUsername());
        //获取验证码
        //获取存储的验证码
        String code = (String) session.getAttribute("code");
        if (code.equals(enCode)) {
            //判断是否该用户存在
            Admin adm = adminDao.selectOne(admin);
            System.out.println(adm);
            if (adm != null) {
                //用户存在的情况下判断密码是否正确
                if (adm.getPassword().equals(admin.getPassword())) {
                    //密码相同说明登陆成功
                    session.setAttribute("adm", adm);
                    map.put("status", "200");
                } else {
                    //密码不对
                    map.put("status", "400");
                    map.put("message", "输入的密码不正确");
                }
            } else {
                map.put("status", "400");
                map.put("message", "该用户不存在");
            }
        } else {
            map.put("status", "400");
            map.put("message", "输入的验证码不正确");
        }
        return map;
    }

}