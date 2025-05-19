package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmService;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    private FilmService filmService;
    private Film testFilm;

    @BeforeEach
    void setUp() {
        filmService = new FilmService();

        testFilm = new Film();
        testFilm.setName("Test Film");
        testFilm.setDescription("Test Description");
        testFilm.setReleaseDate(LocalDate.of(2000, 1, 1));
        testFilm.setDuration(120);
    }

    @Test
    void createFilm_ShouldAddFilmToStorage() {
        Film createdFilm = filmService.createFilm(testFilm);

        assertNotNull(createdFilm.getId());
        assertEquals(1, filmService.getAllFilms().size());
        assertEquals("Test Film", filmService.getAllFilms().get(0).getName());
    }

    @Test
    void createFilm_ShouldThrowException_WhenReleaseDateBefore1895() {
        testFilm.setReleaseDate(LocalDate.of(1890, 1, 1));

        assertThrows(ValidationException.class, () -> filmService.createFilm(testFilm));
    }

    @Test
    void updateFilm_ShouldUpdateExistingFilm() {
        Film createdFilm = filmService.createFilm(testFilm);
        createdFilm.setName("Updated Name");

        Film updatedFilm = filmService.updateFilm(createdFilm);

        assertEquals("Updated Name", updatedFilm.getName());
        assertEquals(1, filmService.getAllFilms().size());
    }

    @Test
    void updateFilm_ShouldThrowException_WhenFilmNotFound() {
        testFilm.setId(999L);

        assertThrows(NotFoundException.class, () -> filmService.updateFilm(testFilm));
    }

    @Test
    void getAllFilms_ShouldReturnEmptyList_WhenNoFilmsAdded() {
        List<Film> films = filmService.getAllFilms();

        assertTrue(films.isEmpty());
    }

    @Test
    void getAllFilms_ShouldReturnAllFilms() {
        filmService.createFilm(testFilm);
        Film anotherFilm = new Film();
        anotherFilm.setName("Another Film");
        anotherFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        anotherFilm.setDuration(90);
        filmService.createFilm(anotherFilm);

        List<Film> films = filmService.getAllFilms();

        assertEquals(2, films.size());
    }
}
