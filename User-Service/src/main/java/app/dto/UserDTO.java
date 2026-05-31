package app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными пользователя")
public class UserDTO {

    @Schema(description = "Уникальный идентификатор пользователя",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Имя пользователя", example = "Иван Петров")
    private String name;

    @Schema(description = "Email пользователя", example = "ivan@example.com")
    private String email;

    @Schema(description = "Возраст пользователя", example = "25")
    private Integer age;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
