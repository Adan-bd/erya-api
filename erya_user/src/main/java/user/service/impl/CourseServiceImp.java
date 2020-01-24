package user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import user.mapper.CourseMapper;
import user.pojo.Course;
import user.service.CourseService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImp implements CourseService {
    private CourseMapper courseMapper;

    public CourseServiceImp(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }


    @Override
    public IPage<Course> selectCourse(int pageNo, int pageSize,String search) {
        IPage<Course> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.like("name","%"+search+"%");
        return courseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Course selectCourseById(int id) {
        return courseMapper.selectById(id);
    }
}
