package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserService;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserService();

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setLogin("testLogin");
        testUser.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    void createUser_ShouldAddUserToStorage() {
        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser.getId());
        assertEquals(1, userService.getAllUsers().size());
        assertEquals("test@example.com", userService.getAllUsers().get(0).getEmail());
    }

    @Test
    void createUser_ShouldSetLoginAsName_WhenNameIsEmpty() {
        testUser.setName("");

        User createdUser = userService.createUser(testUser);

        assertEquals("testLogin", createdUser.getName());
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        User createdUser = userService.createUser(testUser);
        createdUser.setEmail("updated@example.com");

        User updatedUser = userService.updateUser(createdUser);

        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        testUser.setId(999L);

        assertThrows(NotFoundException.class, () -> userService.updateUser(testUser));
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenNoUsersAdded() {
        List<User> users = userService.getAllUsers();

        assertTrue(users.isEmpty());
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        userService.createUser(testUser);
        User anotherUser = new User();
        anotherUser.setEmail("another@example.com");
        anotherUser.setLogin("anotherLogin");
        userService.createUser(anotherUser);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }
}
