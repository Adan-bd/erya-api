package erya.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import erya.pojo.Course;

public interface CourseService {

    IPage<Course> selectCourse(int page, int pageSize, String search);

    Course selectCourseById(int id);
}
