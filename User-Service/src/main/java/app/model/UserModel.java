package app.model;

import app.controller.UserController;
import app.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class UserModel extends EntityModel<UserDTO> {

    public UserModel(UserDTO user) {
        super(user);

        // Ссылка на самого себя
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();

        // Ссылка на обновление пользователя
        Link updateLink = linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update");

        // Ссылка на удаление пользователя
        Link deleteLink = linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete");

        // Ссылка на список всех пользователей
        Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users");

        // Ссылка на создание нового пользователя
        Link createLink = linkTo(methodOn(UserController.class).createUser(null)).withRel("create");

        this.add(selfLink, updateLink, deleteLink, allUsersLink, createLink);
    }
}
