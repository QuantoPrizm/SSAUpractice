package ru.ssau.BoardGames.entity.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ssau.BoardGames.entity.dto.UserDto;
import ru.ssau.BoardGames.entity.User;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPasswordHash());
        // Намеренно не включаем пароль в DTO
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        // Пароль обрабатывается отдельно в контроллере
        // createdAt устанавливается автоматически
        // lastLogin устанавливается при логине
        return user;
    }

    public void updateUserFromDto(UserDto dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        // Пароль обновляется отдельно через контроллер
        // Не обновляем createdAt - это неизменяемое поле
        // lastLogin обновляется отдельно при логине
    }
}