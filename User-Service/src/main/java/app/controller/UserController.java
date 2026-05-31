package app.controller;

import app.dto.CreateUserRequest;
import app.dto.UpdateUserRequest;
import app.dto.UserDTO;
import app.model.UserCollectionModel;
import app.model.UserModel;
import app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API для управления пользователями с поддержкой HATEOAS")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать нового пользователя",
            description = "Создаёт пользователя с указанным именем, email и возрастом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = UserModel.class))),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные (email уже существует, неверный возраст и т.д.)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для создания пользователя",
            required = true,
            content = @Content(examples = @ExampleObject(
                    name = "Пример запроса",
                    value = "{\"name\":\"Иван Петров\",\"email\":\"ivan@example.com\",\"age\":25}"
            ))
    )
    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(request);
        UserModel userModel = new UserModel(createdUser);
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить пользователя",
            description = "Обновляет данные существующего пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUser(id, request);
        UserModel userModel = new UserModel(updatedUser);
        return ResponseEntity.ok(userModel);
    }

    @Operation(summary = "Удалить пользователя",
            description = "Удаляет пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить пользователя по ID",
            description = "Возвращает пользователя с указанным ID и HATEOAS ссылками для навигации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        UserModel userModel = new UserModel(user);
        return ResponseEntity.ok(userModel);
    }

    @Operation(summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей с HATEOAS ссылками")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    })
    @GetMapping
    public ResponseEntity<UserCollectionModel> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        List<UserModel> userModels = users.stream()
                .map(UserModel::new)
                .collect(Collectors.toList());
        UserCollectionModel collectionModel = new UserCollectionModel(userModels);
        return ResponseEntity.ok(collectionModel);
    }
}
