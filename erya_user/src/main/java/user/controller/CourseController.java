package user.controller;

import common.vo.Result;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;

    public CourseController(CourseService courseService, StringRedisTemplate stringRedisTemplate) {
        this.courseService = courseService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

//    @Deprecated
//    @PostMapping("course/{page}/{pageSize}")
//    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
//        IPage<Course> courseIPage = new Page<>();
//        Result result = new Result();
//        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
//        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
//        Set<ZSetOperations.TypedTuple<String>> courseId = zSetOperations.rangeWithScores("CourseId", (page - 1) * pageSize, page * pageSize - 1);
//        String courseSearch1 = (String) hashOperations.get("CourseSearch", search);
//        long courseSearch = Long.parseLong(courseSearch1 == null ? "0" : courseSearch1);
//        if (courseSearch == 0) {
//            courseIPage = courseService.selectCourse(page, pageSize, search);
//            result.setData(courseIPage);
//            hashOperations.put("CourseSearch", search, String.valueOf(courseIPage.getTotal()));
//            Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
//            List<Course> records = courseIPage.getRecords();
//            for (Course record : records) {
//                tuples.add(new DefaultTypedTuple<>(record.getId() + "-" + record.getName(), record.getId() + 0.0));
//            }
//            if (tuples.size() != 0) {
//                zSetOperations.add("CourseId", tuples);
//            }
//            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }
//
//        if (Objects.requireNonNull(courseId).size() == 0) {
//            courseIPage = courseService.selectCourse(page, pageSize, search);
//            result.setData(courseIPage);
//            Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
//            List<Course> records = courseIPage.getRecords();
//            for (Course record : records) {
//                tuples.add(new DefaultTypedTuple<>(record.getId() + "-" + record.getName(), record.getId() + 0.0));
//            }
//            zSetOperations.add("CourseId", tuples);
//            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }
//
//        courseIPage.setSize(pageSize);
//        courseIPage.setCurrent(page);
//        courseIPage.setTotal(courseSearch);
//        courseIPage.setPages(courseSearch % pageSize == 0 ? courseSearch / pageSize : courseSearch / pageSize + 1);
//        List<Course> list = new ArrayList<>();
//        for (ZSetOperations.TypedTuple<String> stringTypedTuple : courseId) {
//            Course course = new Course();
//            course.setId(Objects.requireNonNull(stringTypedTuple.getScore()).intValue());
//            String value = stringTypedTuple.getValue();
//            int i = Objects.requireNonNull(value).indexOf('-');
//            course.setName(value.substring(i + 1));
//            list.add(course);
//        }
//        courseIPage.setRecords(list);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    @PostMapping("course/{page}/{pageSize}")
    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(courseService.selectCourse(page,pageSize,search)));
    }
    @PostMapping("course/getCourse/{id}")
    public ResponseEntity<Result> answers(@PathVariable("id") int id) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        Course course;
        Map<Object, Object> map = hashOperations.entries(String.valueOf(id));
        if (map.get("name") != null && map.get("content") != null) {
            course = new Course(id, (String) map.get("name"), (String) map.get("content"));
        } else {
            course = courseService.selectCourseById(id);
            map.put("name", course.getName());
            map.put("content", course.getContent());
            hashOperations.putAll(String.valueOf(id), map);
        }
        Result result = new Result(course);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}
