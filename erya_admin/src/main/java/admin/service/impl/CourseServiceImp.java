package admin.service.impl;

import admin.mapper.CourseMapper;
import admin.pojo.Course;
import admin.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService {
    private CourseMapper courseMapper;

    public CourseServiceImp(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int deleteCourse(List<Integer> id) {
        int res = 0;
        for (Integer integer : id) {
            res += courseMapper.deleteById(integer);
        }
        return res;
    }

    @Override
    public int modifyCourse(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public IPage<Course> selectCourse(int pageNo, int pageSize, String search) {
        IPage<Course> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.select("id","name");
        wrapper.like("name", "%" + search + "%");
        return courseMapper.selectPage(page, wrapper);
    }

    @Override
    public Course selectCourseById(int id) {
        return courseMapper.selectById(id);
    }
}
