package baizhi.jhy.service;

import baizhi.jhy.dao.UserDao;
import baizhi.jhy.entity.UserPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    //分页
    @Override
    public UserPageDto selectPage(int pageNow, int pageSize) {
        UserPageDto userPageDto = new UserPageDto();
        userPageDto.setPage(pageNow);//设置当前页
        int totalCount = userDao.selectTotalCount();//获取总条数
        userPageDto.setRecords(totalCount);//设置总行数
        userPageDto.setTotal(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);// 设置总页
        userPageDto.setRows(userDao.selectPage(pageNow, pageSize));// 设置当前页的数据行
        return userPageDto;
    }

}
