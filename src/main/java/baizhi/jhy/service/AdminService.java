package baizhi.jhy.service;

import baizhi.jhy.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    Map queryAdmin(Admin admin, String enCode, HttpSession session);
}
