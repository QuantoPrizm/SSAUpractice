package ru.ssau.BoardGames.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ssau.BoardGames.entity.dto.UserDto;
import ru.ssau.BoardGames.entity.User;
import ru.ssau.BoardGames.entity.mapper.UserMapper;
import ru.ssau.BoardGames.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> ResponseEntity.ok(userMapper.toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

@PostMapping
public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    // Валидация уже выполнена через @Valid
    User user = userMapper.toEntity(userDto);
    User savedUser = userRepository.save(user);
    return ResponseEntity.ok(userMapper.toDto(savedUser));
}

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    userMapper.updateUserFromDto(userDto, existingUser);
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(userMapper.toDto(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}