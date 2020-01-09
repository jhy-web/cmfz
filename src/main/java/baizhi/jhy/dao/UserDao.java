package baizhi.jhy.dao;

import baizhi.jhy.entity.User;
import baizhi.jhy.entity.UserDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    //条形图（查询用户活跃度）
    public Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);

    //地图（查询用户地区分布）
    public List<UserDto> queryByLocation(String sex);

    //查询分页
    List<User> selectPage(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize);

    //查询总行数
    int selectTotalCount();

    //补全
    public void updateUserMessage(User u);

}
