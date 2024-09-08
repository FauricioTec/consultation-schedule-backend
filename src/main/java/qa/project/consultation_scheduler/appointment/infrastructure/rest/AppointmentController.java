package qa.project.consultation_scheduler.appointment.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import qa.project.consultation_scheduler.appointment.aplication.service.AppointmentService;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/results")
    public ResponseEntity<List<Appointment>> getAppointmentsReport(@RequestParam(required = false) String studentIdCard,
                                                         @RequestParam(required = false) String courseName,
                                                         @RequestParam(required = false) String professorName,
                                                         @RequestParam(required = false) LocalDateTime startDateTime) {
        return ResponseEntity.ok(appointmentService.getFilteredAppointments(studentIdCard, courseName, professorName, startDateTime));
    }

    @GetMapping("/next")
    public ResponseEntity<Appointment> getNextAppointment(@RequestParam UUID studentId,
                                                             @RequestParam UUID courseId,
                                                             @RequestParam(required = false) UUID lastAppointmentId) {
        Appointment createdAppointment;
        if (lastAppointmentId == null) {
            createdAppointment = appointmentService.getNextAppointment(studentId, courseId);
        } else {
            createdAppointment = appointmentService.getNextAppointment(studentId, courseId, lastAppointmentId);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdAppointment.getId()).toUri();
        return ResponseEntity.created(location).body(createdAppointment);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Appointment> acceptAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.acceptAppointment(id));
    }
}
