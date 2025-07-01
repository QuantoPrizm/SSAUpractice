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
        // Никогда не включаем хеш пароля в DTO!
        dto.setPassword(null); // Явное обнуление
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

        // Хешируем пароль только если он предоставлен
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        return user;
    }

    public void updateUserFromDto(UserDto dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(entity.getEmail())) {
            entity.setEmail(dto.getEmail());
        }

        // Пароль обновляется только если предоставлен новый
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
    }
}