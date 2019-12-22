package user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import user.pojo.Course;

public interface CourseService {

    IPage<Course> selectCourse(int page, int pageSize, String search);

    Course selectCourseById(int id);
}
