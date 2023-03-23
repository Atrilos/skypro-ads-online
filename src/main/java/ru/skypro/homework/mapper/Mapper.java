package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    public UserDTO toDto(User user) {
        return mapper.map(user, UserDTO.class);
    }

}
