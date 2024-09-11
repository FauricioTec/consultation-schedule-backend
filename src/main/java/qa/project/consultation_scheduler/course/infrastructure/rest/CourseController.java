package qa.project.consultation_scheduler.course.infrastructure.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import qa.project.consultation_scheduler.course.application.service.CourseService;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.infrastructure.request.AddProfessorToCourseReq;
import qa.project.consultation_scheduler.course.infrastructure.request.CreateCourseReq;
import qa.project.consultation_scheduler.course.infrastructure.request.UpdateCourseEndSemesterReq;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    final private CourseService courseService;

    @GetMapping("/results")
    public ResponseEntity<List<Course>> getFilteredCourses(@RequestParam(required = false) String courseName,
                                                           @RequestParam(required = false) String professorName,
                                                           @RequestParam(required = false) String consultationDay,
                                                           @RequestParam(required = false) Integer totalAppointments,
                                                           @RequestParam(required = false) Integer acceptedAppointments,
                                                           @RequestParam(required = false) Integer availableAppointments) {
        return ResponseEntity.ok(courseService.getFilteredCourses(courseName, professorName, consultationDay, totalAppointments, acceptedAppointments, availableAppointments));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CreateCourseReq createCourseReq) {
        Course createdCourse = courseService.createCourse(createCourseReq.toEntity());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdCourse.getId()).toUri();
        return ResponseEntity.created(location).body(createdCourse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PatchMapping("{id}/semester-end")
    public ResponseEntity<Course> updateCourseEndSemester(@PathVariable UUID id,
                                                          @Valid @RequestBody UpdateCourseEndSemesterReq updateCourseEndSemesterReq) {
        return ResponseEntity.ok(courseService.updateCourseSemesterEndDate(id, updateCourseEndSemesterReq.endDate()));
    }

    @PatchMapping("{id}/professors")
    public ResponseEntity<Course> addProfessorToCourse(@PathVariable UUID id,
                                                       @Valid @RequestBody AddProfessorToCourseReq addProfessorToCourseReq) {
        return ResponseEntity.ok(courseService.addProfessorToCourse(id, addProfessorToCourseReq.professorId()));
    }
}
