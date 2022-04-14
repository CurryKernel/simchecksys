package ncu.edu.simchecksys.dao;

import ncu.edu.simchecksys.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:12
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Repository
@Mapper
public interface CourseDao {
    Course getCourseById(String cid);
    Course getCourseByName(String cname);
    List<Course> getCoursesByTno(String tno);
    List<Course> getAll();
    int countAll();
    void saveOne(Course course);
    void delById(String cid);
    void updateCourse(Course course);
}

