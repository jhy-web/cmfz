package baizhi.jhy.controller;

import baizhi.jhy.dao.UserDao;
import baizhi.jhy.entity.DemoDataListener;
import baizhi.jhy.entity.User;
import baizhi.jhy.entity.UserDto;
import baizhi.jhy.entity.UserPageDto;
import baizhi.jhy.service.UserService;
import baizhi.jhy.util.SmsUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    //分页
    @RequestMapping("querypage")
    public UserPageDto queryPage(Integer page, Integer rows) {//固定
        return userService.selectPage(page, rows);
    }

    @RequestMapping("sendCode")
    public Map sendCode(String phone) {
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 3);
        SmsUtil.send(phone, code);
        // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
        HashMap hashMap = new HashMap();
        hashMap.put("status", "200");
        hashMap.put("message", "短信发送成功");
        return hashMap;
    }

    //查询所有
    @RequestMapping("selectAll")
    public List<User> selectAll() {
        List<User> users = userDao.selectAll();
        return users;
    }

    //导出
    @RequestMapping("out")
    public void testEasyExcelOut() {
        /*
         * write(): 参数1：文件路径，参数2：实体类，
         * sheet: 指定写入工作薄的名称
         * doWrite(list数据)
         * 如果需要下载使用 参数1：outputSteam，参数2：实体类.class
         * */
        List<User> users = userDao.selectAll();
        String fileName = "E:\\后期项目\\day7-poiEasyExcel\\EasyExcel\\" + new Date().getTime() + ".xls";
        EasyExcel.write(fileName, User.class)
                .sheet("测试")
                .doWrite(users);
    }

    //导入
    @RequestMapping("in")
    public void testEadyExcelIn() {
        /*
         * readListener: 读取数据时的监听器，每次使用DemoDataListener都要new，不能把每次使用DemoDataListener都要new交给工厂（单例模式）管理，
         * 文件上传：Mfile url,   File file = new File;
         * */
        String url = "E:\\后期项目\\day7-poiEasyExcel\\EasyExcel\\1577973226177.xls";
        EasyExcel.read(url, User.class, new DemoDataListener()).sheet().doRead();
    }

    //echarts(条形图查询用户活跃度)
    @RequestMapping("showUserTime")
    public Map showUserTime() {
        HashMap map = new HashMap<>();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("男", 1));
        manList.add(userDao.queryUserByTime("男", 7));
        manList.add(userDao.queryUserByTime("男", 30));
        manList.add(userDao.queryUserByTime("男", 365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("女", 1));
        womenList.add(userDao.queryUserByTime("女", 7));
        womenList.add(userDao.queryUserByTime("女", 30));
        womenList.add(userDao.queryUserByTime("女", 365));
        map.put("man", manList);
        map.put("women", womenList);
        return map;
    }

    //地图（查询用户地区分布）
    @RequestMapping("showUserByAddress")
    public Map showUserByAddress() {
        HashMap map = new HashMap();
        List<UserDto> mans = userDao.queryByLocation("男");
        List<UserDto> womens = userDao.queryByLocation("女");
        map.put("mans", mans);
        map.put("womens", womens);
        return map;
    }

    //添加 删除
    @RequestMapping("addAndDelete")
    public void addAndDelete(String oper, String id, User user) {
        if ("add".equals(oper)) {
            user.setId(UUID.randomUUID().toString());
            user.setRigest_date(new Date());
            userDao.insert(user);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-b5791e29a8234f92858757233368edc7");
            Map map = showUserByAddress();
            String s = JSON.toJSONString(map);
            goEasy.publish("cmfz", s);
        } else if ("del".equals(oper)) {
            userDao.deleteByPrimaryKey(id);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-b5791e29a8234f92858757233368edc7");
            Map map = showUserByAddress();
            String s = JSON.toJSONString(map);
            goEasy.publish("cmfz", s);
        }
    }

    /*@RequestMapping("addUser")
    public void addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setSex("0");
        user.setLocation("河南");
        user.setRigest_date(new Date());
        userDao.insert(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-2cee62d1b57b4c5c9d23032665b66aef");
        Map map = showUserTime();
        String s = JSONUtils.toJSONString(map);
        System.out.println(s);
        goEasy.publish("cmfz", s);
    }*/
}
