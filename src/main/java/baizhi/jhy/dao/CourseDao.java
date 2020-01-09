package baizhi.jhy.dao;

import baizhi.jhy.entity.Course;

import java.util.List;

public interface CourseDao {
    public List<Course> selectAll(String id);

    public void insert(Course c);

    public void delete(String id);

    public Course selectById(String id);
}
