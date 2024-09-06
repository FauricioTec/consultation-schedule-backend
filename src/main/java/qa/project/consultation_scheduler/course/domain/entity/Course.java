package qa.project.consultation_scheduler.course.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "course")
@Data
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity {

    public Course(Semester semester, String courseCode, String courseName) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "course_professor",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    @JsonManagedReference
    private List<Professor> professors = new ArrayList<>();

    @OneToOne(mappedBy = "course")
    @NotNull(message = "Semester is mandatory")
    private Semester semester;

    @Column(nullable = false)
    @NotBlank(message = "Course code is mandatory")
    private String courseCode;

    @Column(nullable = false)
    @NotBlank(message = "Course name is mandatory")
    private String courseName;

    //Virtual attribute
    @JsonProperty("totalAppointments")
    public int getTotalAppointments() {
        return appointments.size();
    }

    //Virtual attribute
    @JsonProperty("acceptedAppointments")
    public int getAcceptedAppointments() {
        return (int) appointments.stream().filter(a -> a.getStatus() == Status.ACCEPTED).count();
    }

    //Virtual attribute
    @JsonProperty("availableAppointments")
    public int getAvailableAppointments() {
        int count = 0;
        LocalDateTime start = LocalDateTime.now();
        for (Professor professor : professors) {
            count += professor.getAvailableAppointmentsCount(start, semester.getEndDate().atTime(LocalTime.MAX));
        }
        return count;
    }

    public void updateSemesterEndDate(LocalDate endDate) {
        if (endDate.isBefore(semester.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        semester.setEndDate(endDate);
    }

    public void addProfessor(Professor professor) {
        professor.addCourse(this);
        professors.add(professor);
    }
}