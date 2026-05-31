package app.model;

import app.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class UserCollectionModel extends CollectionModel<UserModel> {

    public UserCollectionModel(List<UserModel> users) {
        super(users);

        // Ссылка на создание нового пользователя
        Link createLink = linkTo(methodOn(UserController.class).createUser(null)).withRel("create");

        // Ссылка на первую страницу (если будет пагинация)
        Link firstPageLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("first");

        // Ссылка на документацию Swagger
        Link swaggerLink = Link.of("/swagger-ui.html", "documentation");

        this.add(createLink, firstPageLink, swaggerLink);
    }
}
