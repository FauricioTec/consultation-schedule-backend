package qa.project.consultation_scheduler._shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
