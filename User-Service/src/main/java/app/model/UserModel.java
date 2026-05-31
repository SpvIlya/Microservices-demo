package app.model;

import app.controller.UserController;
import app.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class UserModel extends EntityModel<UserDTO> {

    public UserModel(UserDTO user) {
        super(user);

        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();

        Link updateLink = linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update");

        Link deleteLink = linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete");

        Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users");

        Link createLink = linkTo(methodOn(UserController.class).createUser(null)).withRel("create");

        this.add(selfLink, updateLink, deleteLink, allUsersLink, createLink);
    }
}
