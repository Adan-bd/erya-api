package admin.service;

import admin.pojo.Course;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CourseService {
    int insertCourse(Course course);

    int deleteCourse(List<Integer> id);

    int modifyCourse(Course course);

    IPage<Course> selectCourse(int page, int pageSize, String search);

    Course selectCourseById(int id);
}
