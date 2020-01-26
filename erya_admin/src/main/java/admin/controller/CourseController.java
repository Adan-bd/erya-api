package admin.controller;

import admin.pojo.Course;
import admin.service.CourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import common.exception.EryaEnum;
import common.exception.EryaException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private CourseService courseService;
    private RedisTemplate redisTemplate;

    public CourseController(CourseService courseService, RedisTemplate redisTemplate) {
        this.courseService = courseService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("course/{page}/{pageSize}")
    public ResponseEntity<IPage<Course>> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.selectCourse(page, pageSize, search));
    }

    @PostMapping("course/delete")
    public ResponseEntity<Integer> delete(@RequestBody List<Integer> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.deleteCourse(answers));
    }
    @PostMapping("course/getCourse/{id}")
    public ResponseEntity<Course> answers(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.selectCourseById(id));
    }

    @PostMapping("course/modify")
    public ResponseEntity<Integer> modify(@RequestBody Course course) {
        if(course.getId()==0)
            throw new EryaException(EryaEnum.REQUEST_INVALID);
        HashOperations hashOperations = redisTemplate.opsForHash();
        if(course.getName()!=null)
            hashOperations.put(course.getId(),"name",course.getName());
        if(course.getContent()!=null)
            hashOperations.put(course.getId(),"content",course.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(courseService.modifyCourse(course));
    }

    @PostMapping("course/add")
    public ResponseEntity<Integer> add(@RequestBody Course answer) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.insertCourse(answer));
    }
}
