package qa.project.consultation_scheduler.professor.infrastructure.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import qa.project.consultation_scheduler.professor.aplication.service.ProfessorService;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professors")
@AllArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<Professor>> getProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@Valid @RequestBody Professor professor) {
        Professor createProfessor = professorService.createProfessor(professor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createProfessor.getId()).toUri();
        return ResponseEntity.created(location).body(createProfessor);
    }

    @GetMapping("{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable UUID id) {
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

    @PostMapping("{id}/schedules")
    public ResponseEntity<Schedule> addConsultationSchedule(@PathVariable UUID id,
                                                  @Valid @RequestBody Schedule schedule) {
        Schedule createdSchedule = professorService.addConsultationSchedule(id, schedule);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdSchedule.getId()).toUri();
        return ResponseEntity.created(location).body(createdSchedule);
    }
}
