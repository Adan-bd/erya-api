package user.controller;

import common.vo.Result;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import user.pojo.Course;
import user.service.CourseService;

import java.util.Map;

@RestController
public class CourseController {
    private CourseService courseService;
    private RedisTemplate redisTemplate;

    public CourseController(CourseService courseService, RedisTemplate redisTemplate) {
        this.courseService = courseService;
        this.redisTemplate = redisTemplate;
    }


    @PostMapping("course/{page}/{pageSize}")
    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        Result result = new Result(courseService.selectCourse(page, pageSize, search));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping("course/getCourse/{id}")
    public ResponseEntity<Result> answers(@PathVariable("id") int id) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Course course;
        Map map = hashOperations.entries(id);
        if (map.get("name") != null && map.get("content") != null) {
            course = new Course(id, (String) map.get("name"), (String) map.get("content"));
        } else {
            course = courseService.selectCourseById(id);
            map.put("name",course.getName());
            map.put("content",course.getContent());
            hashOperations.putAll(id,map);
        }
        Result result = new Result(course);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}
