package baizhi.jhy.entity;

import baizhi.jhy.dao.UserDao;
import baizhi.jhy.util.ApplicationContentUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;

/*
* invoke : 读取每行数据后会执行的方法
  doAfterAllAnalysed: 所有数据读取完毕后执行的方法
* */

public class DemoDataListener extends AnalysisEventListener<User> {

    ArrayList<User> list = new ArrayList<>();

    ApplicationContentUtils appUtil = new ApplicationContentUtils();

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        //参数：DemoData  针对每行数据 进行实体类封装
        UserDao bean = (UserDao) appUtil.getBean(UserDao.class);
        bean.insertSelective(user);
        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
