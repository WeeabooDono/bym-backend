package com.wd.bym.webapp.controller;

import com.wd.bym.context.AbstractIntegrationTest;
import com.wd.bym.core.transform.dto.pagination.PagerDto;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = {"classpath:datasets/repository/manga_repository_test_before.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = {"classpath:datasets/repository/manga_repository_test_after.sql"}),
})
class MangaControllerIT extends AbstractIntegrationTest {

    private static final String ENDPOINT = "/api/v1/mangas/";

    /**
     * Test les cas :
     * - Quand la taille de la page est supérieure au nombre d'objets.
     * - Quand la taille de la page est inférieure au nombre d'objets.
     * - Quand on affiche la page 2.
     * - Quand on veut voir une page qui ne contient pas de mangas.
     * - Quand on souhaite avoir un manga depuis son id et qu'il existe.
     * - Quand on souhaite avoir un manga depuis son id et qu'il n'existe pas.
     */
    @Nested
    class GetMangas {

        @Test
        void test_getAll_whenPageSizeIsGreaterThanTheNumberOfItems() throws Exception {
            var pager = PagerDto.builder().page(0).pageSize(9).build();

            final MockHttpServletRequestBuilder request = get(ENDPOINT)
                    .param("page", String.valueOf(pager.getPage()))
                    .param("pageSize", String.valueOf(pager.getPageSize()));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items.*.id", contains(1, 2, 3)))
                    .andExpect(jsonPath("$.items.*.title", contains("test_1", "test_2", "test_3")))
                    .andExpect(jsonPath("$.total").value(3));
        }

        @Test
        void test_getAll_whenPageSizeIsLessThanTheNumberOfItems() throws Exception {
            var pager = PagerDto.builder().page(0).pageSize(2).build();

            final MockHttpServletRequestBuilder request = get(ENDPOINT)
                    .param("page", String.valueOf(pager.getPage()))
                    .param("pageSize", String.valueOf(pager.getPageSize()));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items.*.id", contains(1, 2)))
                    .andExpect(jsonPath("$.items.*.title", contains("test_1", "test_2")))
                    .andExpect(jsonPath("$.total").value(3));
        }

        @Test
        void test_getAll_whenPageSizeIsLessThanTheNumberOfItemsAndThisIsTheSecondPage() throws Exception {
            var pager = PagerDto.builder().page(1).pageSize(2).build();

            final MockHttpServletRequestBuilder request = get(ENDPOINT)
                    .param("page", String.valueOf(pager.getPage()))
                    .param("pageSize", String.valueOf(pager.getPageSize()));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items[0].id", equalTo(3)))
                    .andExpect(jsonPath("$.items[0].title", equalTo("test_3")))
                    .andExpect(jsonPath("$.total").value(3));
        }

        @Test
        void test_getAll_whenPageSizeIsTooGreatToHaveMangas() throws Exception {
            var pager = PagerDto.builder().page(1).pageSize(Integer.MAX_VALUE).build();

            final MockHttpServletRequestBuilder request = get(ENDPOINT)
                    .param("page", String.valueOf(pager.getPage()))
                    .param("pageSize", String.valueOf(pager.getPageSize()));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items", Matchers.empty()))
                    .andExpect(jsonPath("$.total").value(3));
        }

        @Test
        void test_getOne_whenIdExists_shouldReturnOk() throws Exception {
            final long id = 1L;

            final MockHttpServletRequestBuilder request = get(ENDPOINT + "/" + id);

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(1)))
                    .andExpect(jsonPath("$.title", equalTo("test_1")));
        }

        @Test
        void test_getOne_whenIdDoesNotExists_shouldThrowNotFound() throws Exception {
            final long id = 999;

            final MockHttpServletRequestBuilder request = get(ENDPOINT + "/" + id);

            mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andExpect(verifyExceptionType("com.wd.bym.webapp.exception.NotFoundException"))
                    .andExpect(verifyExceptionTitle("error.server.not-found.entity.title"))
                    .andExpect(verifyExceptionMessage("error.server.not-found.entity.msg"))
                    .andExpect(verifyExceptionCause("Manga with id " + id + " was not found !"));
        }
    }

    /**
     * Test les cas :
     * - Quand on sauvegarde un manga avec tous les paramètres remplis.
     * - Quand on sauvegarde un manga avec un titre qui existe déjà.
     * - Quand on sauvegarde un manga avec un titre à "null".
     */
    @Nested
    class CreateManga {

        @Test
        void test_createManga_whenMangaHasEveryParameter() throws Exception {
            final JSONObject json = new JSONObject(ofEntries(
                    entry("title", "title")
            ));

            final MockHttpServletRequestBuilder request = post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json.toString());

            final long expectedId = 4L;

            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost" + ENDPOINT + expectedId));

            mockMvc.perform(get(ENDPOINT + expectedId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(4)))
                    .andExpect(jsonPath("$.title", equalTo("title")));
        }

        @Test
        void test_createManga_whenMangaTitleAlreadyExists_shouldThrowBadRequest() throws Exception {
            final JSONObject json = new JSONObject(ofEntries(
                    entry("title", "test_1")
            ));

            final MockHttpServletRequestBuilder request = post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json.toString());

            mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(verifyExceptionType("com.wd.bym.webapp.exception.BadRequestException"))
                    .andExpect(verifyExceptionTitle("error.server.bad-request.constraint-not-respected.title"))
                    .andExpect(verifyExceptionMessage("create.manga.title: The title should be unique"))
                    .andExpect(verifyExceptionCause("create.manga.title: The title should be unique"));
        }

        @Test
        void test_createManga_whenMangaTitleIsNull_shouldThrowBadRequest() throws Exception {
            final JSONObject json = new JSONObject();

            final MockHttpServletRequestBuilder request = post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json.toString());

            mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(verifyExceptionType("com.wd.bym.webapp.exception.BadRequestException"))
                    .andExpect(verifyExceptionTitle("error.server.bad-request.constraint-not-respected.title"))
                    .andExpect(verifyExceptionMessage("create.manga.title: must not be null"))
                    .andExpect(verifyExceptionCause("create.manga.title: must not be null"));
        }
    }
}