package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseWrapperComment {
    private Integer count;
    private List<CommentDTO> results;
}
