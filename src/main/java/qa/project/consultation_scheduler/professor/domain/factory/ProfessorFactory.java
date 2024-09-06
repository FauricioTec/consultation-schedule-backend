package qa.project.consultation_scheduler.professor.domain.factory;

import qa.project.consultation_scheduler.professor.domain.entity.Professor;

public class ProfessorFactory {

    public static Professor createProfessor(String name) {
        return new Professor(name);
    }
}
