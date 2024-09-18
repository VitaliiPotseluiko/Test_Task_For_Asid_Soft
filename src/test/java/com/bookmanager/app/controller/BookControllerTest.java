package com.bookmanager.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.dto.ErrorDto;
import com.bookmanager.app.dto.ErrorValidationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static final String BOOKS_PATH = "/api/books";
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
            ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        tearDown(dataSource);
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/book/add-data-to-database.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        tearDown(dataSource);
    }

    @SneakyThrows
    static void tearDown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/book/delete-all-from-database.sql"));
        }
    }

    @Test
    @Sql(scripts = "classpath:database/book/delete-book-where-title=kobzar.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            create new book
            """)
    public void createBook_ValidRequestDto_Ok() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthor("Shevchenko Taras");
        bookRequestDto.setTitle("Kobzar");
        bookRequestDto.setGenre("Poems");
        bookRequestDto.setPublicationYear(2006);
        bookRequestDto.setIsbn("2400000032632");

        BookResponseDto expected = new BookResponseDto();
        expected.setTitle(bookRequestDto.getTitle());
        expected.setAuthor(bookRequestDto.getAuthor());
        expected.setGenre(bookRequestDto.getGenre());
        expected.setIsbn(bookRequestDto.getIsbn());
        expected.setPublicationYear(bookRequestDto.getPublicationYear());
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        MvcResult result = mockMvc.perform(post(BOOKS_PATH)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookResponseDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual,"id"));
    }

    @Test
    @DisplayName("""
            create book with invalid request dto
            """)
    public void createBook_InValidRequestDto_NotOk() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthor("");
        bookRequestDto.setTitle("");
        bookRequestDto.setGenre("Poems");
        bookRequestDto.setPublicationYear(2006);
        bookRequestDto.setIsbn("2400000032632");
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);
        List<String> expectedErrors = List.of( "author can't be empty", "title can't be empty");

        MvcResult result = mockMvc.perform(post(BOOKS_PATH)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorValidationDto actual  = objectMapper.readValue(result.getResponse().getContentAsString(),
                ErrorValidationDto.class);

        assertEquals(expectedErrors.size(), actual.getErrors().length);
        assertEquals(expectedErrors, Arrays.stream(actual.getErrors()).sorted().toList());
    }

    @Test
    @DisplayName("""
            create book with request dto that has existing isbn
            """)
    public void createBook_TheSameIsbn_NotOk() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthor("Author");
        bookRequestDto.setTitle("Title");
        bookRequestDto.setGenre("Poems");
        bookRequestDto.setPublicationYear(2006);
        bookRequestDto.setIsbn("978-1-2345-6789-7");
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);
        String expectedError = "Can't create. Book with isbn "
                + bookRequestDto.getIsbn() + " is exist!";

        MvcResult result = mockMvc.perform(post(BOOKS_PATH)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ErrorDto.class);

        assertEquals(expectedError, actual.getError());
    }
}