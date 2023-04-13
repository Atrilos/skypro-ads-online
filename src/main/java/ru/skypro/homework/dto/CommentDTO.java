package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long author;
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private Long pk;
    @Size(min = 3)
    private String text;
}
