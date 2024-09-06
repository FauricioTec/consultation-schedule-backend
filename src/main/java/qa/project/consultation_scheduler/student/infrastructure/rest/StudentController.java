package qa.project.consultation_scheduler.student.infrastructure.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import qa.project.consultation_scheduler.student.aplication.service.StudentService;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.infrastructure.request.CreateStudentReq;
import qa.project.consultation_scheduler.student.infrastructure.request.EnrollStudentReq;
import qa.project.consultation_scheduler.student.infrastructure.request.UpdateStudentStarRatingReq;
import qa.project.consultation_scheduler.student.validation.annotation.ValidStarRating;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentServiceImpl;

    @GetMapping("/results")
    public ResponseEntity<List<?>> getFilteredStudents(
            @RequestParam(required = false) String campus,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Integer attemptCount,
            @RequestParam(required = false) Integer starRating) {
        return ResponseEntity.ok(studentServiceImpl.getFilteredStudents(campus, idCard, courseName, attemptCount, starRating));
    }

    @GetMapping
    public ResponseEntity<List<?>> getStudents() {
        return ResponseEntity.ok(studentServiceImpl.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentReq createStudentReq) {
        Student createdStudent = studentServiceImpl.createStudent(createStudentReq.toEntity());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdStudent.getId()).toUri();
        return ResponseEntity.created(location).body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable UUID id) {
        return ResponseEntity.ok(studentServiceImpl.getStudentById(id));
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<?>> getStudentCourses(@PathVariable UUID id) {
        return ResponseEntity.ok(studentServiceImpl.getAllStudentEnrollments(id));
    }

    @PostMapping("/{id}/enrollments")
    public ResponseEntity<Enrollment> enrollStudent(@PathVariable UUID id,
                                                    @Valid @RequestBody EnrollStudentReq enrollStudentReq) {
        Enrollment enrollment = studentServiceImpl.enrollStudent(id, enrollStudentReq.courseId(), enrollStudentReq.attemptCount());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{enrollmentId}")
                .buildAndExpand(enrollment.getId()).toUri();
        return ResponseEntity.created(location).body(enrollment);
    }

    @GetMapping("/{id}/enrollments/{enrollmentId}")
    public ResponseEntity<Enrollment> getStudentEnrollment(@PathVariable UUID id,
                                                           @PathVariable UUID enrollmentId) {
        return ResponseEntity.ok(studentServiceImpl.getStudentEnrollment(id, enrollmentId));
    }

    @PatchMapping("/{id}/enrollments/{enrollmentId}/star-rating")
    public ResponseEntity<Enrollment> updateStudentStarRating(@PathVariable UUID id,
                                                              @PathVariable UUID enrollmentId,
                                                              @Valid @RequestBody UpdateStudentStarRatingReq updateStudentStarRatingReq) {
        return ResponseEntity.ok(studentServiceImpl.updateStarRating(id, enrollmentId, updateStudentStarRatingReq.starRating()));
    }
}