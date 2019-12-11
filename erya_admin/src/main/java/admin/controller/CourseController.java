package admin.controller;

import admin.pojo.Course;
import admin.service.CourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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
    public ResponseEntity<Integer> modify(@RequestBody Course answer) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.modifyCourse(answer));
    }

    @PostMapping("course/add")
    public ResponseEntity<Integer> add(@RequestBody Course answer) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.insertCourse(answer));
    }
}
