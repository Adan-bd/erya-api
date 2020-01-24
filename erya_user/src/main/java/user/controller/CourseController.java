package user.controller;

import common.vo.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import user.pojo.Course;
import user.service.CourseService;

@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("course/{page}/{pageSize}")
    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        Result result = new Result(courseService.selectCourse(page, pageSize, search));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping("course/getCourse/{id}")
    @Cacheable(value = "answer", key = "#id")
    public ResponseEntity<Result> answers(@PathVariable("id") int id) {
        Course course = courseService.selectCourseById(id);
        Result result = new Result(course);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}
