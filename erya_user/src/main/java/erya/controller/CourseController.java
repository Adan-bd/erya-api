package erya.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import erya.pojo.Course;
import erya.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("course/{page}/{pageSize}")
    public ResponseEntity<IPage<Course>> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.selectCourse(page, pageSize,search));
    }

    @PostMapping("course/getCourse/{id}")
    public ResponseEntity<Course> answers(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.selectCourseById(id));
    }
}
