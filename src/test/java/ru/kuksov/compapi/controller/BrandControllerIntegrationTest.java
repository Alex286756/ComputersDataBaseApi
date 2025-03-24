package ru.kuksov.compapi.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.repository.BrandRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://alexkosarev.name/2022/12/29/spring-in-a-nutshell-testing-rest-services/?ysclid=m8jb8uixx5158107128

//@SpringBootTest
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
class BrandControllerIntegrationTest {

//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    BrandRepository brandRepository;

    @AfterEach
    void tearDown() {
//        this.brandRepository.deleteAll();
    }

    @Test
    void getAllBrands_ReturnsValidResponseEntity() throws Exception {
        // given

//        var requestBuilder = get("/compapi/v2/brands");
//        var brands = List.of(new Brand(1, "Первая задача"),
//                new Brand(2, "Вторая задача"));
//        brands.forEach(this.brandRepository::save);
//
//        // when
//        this.mockMvc.perform(requestBuilder)
//            // then
//            .andExpectAll(
//                status().isOk(),
//                content().contentType(MediaType.APPLICATION_JSON),
//                content().json("""
//                        [
//                            {
//                                "id": "1",
//                                "name": "Первая задача"
//                            },
//                            {
//                                "id": "2",
//                                "name": "Вторая задача"
//                            }
//                        ]
//                        """)
//            );

    }

//    @Test
//    void addNewBrand_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
//        // given
//        var requestBuilder = post("/compapi/v2/brands")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                            "name": "Третья задача"
//                        }
//                        """);
//
//        // when
//        this.mockMvc.perform(requestBuilder)
//                // then
//                .andExpectAll(
//                        status().isCreated(),
//                        header().exists(HttpHeaders.LOCATION),
//                        content().contentType(MediaType.APPLICATION_JSON),
//                        content().json("""
//                                {
//                                    "name": "Третья задача"
//                                }
//                                """),
//                        jsonPath("$.id").exists()
//                );
//
//        assertEquals(1, this.brandRepository.findAll().size());
//
//        final var brand = this.brandRepository.findAll().get(0);
//        assertNotNull(brand.id());
//        assertEquals("Третья задача", brand.());
//    }

//    @Test
//    void addNewBrand_PayloadIsInvalid_ReturnsValidResponseEntity() throws Exception {
//        // given
//        var requestBuilder = post("/compapi/v2/brands")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
//                .content("""
//                        {
//                            "name": null
//                        }
//                        """);
//
//        // when
//        this.mockMvc.perform(requestBuilder)
//                // then
//                .andExpectAll(
//                        status().isBadRequest(),
//                        header().doesNotExist(HttpHeaders.LOCATION),
//                        content().contentType(MediaType.APPLICATION_JSON),
//                        content().json("""
//                                {
//                                    "errors": ["Task details must be set"]
//                                }
//                                """, true)
//                );
//
//        assertTrue(this.taskRepository.getTasks().isEmpty());
//    }

//    @LocalServerPort
//    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;

//    @Sql({ "schema.sql", "data.sql" })
//    @Test
//    public void testAllEmployees()
//    {
//        assertTrue(
//                this.restTemplate
//                        .getForObject("http://localhost:" + port + "/brands", Brand.class)
//                        .getId() == 3);
//    }


//    @Test
//    public void testGetBrandNotFound() throws Exception {
//        Mockito.when(brandService.findBrandById(1)).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/compapi/v2/brands/1"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }

//    @Test
//    public void testGetBrandSuccess() throws Exception {
//        Brand mockBrand = new Brand(1, "Dr. Smith");
//        Mockito.when(brandService.findBrandById(1)).thenReturn(mockBrand);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/compapi/v2/brands/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"Dr. Smith\"}"));
//    }

    @Test
    void getAllBrands() {
    }

    @Test
    void getBrandById() {
    }

    @Test
    void getBrandByName() {
    }

    @Test
    void addNewBrand() {
    }

    @Test
    void editBrand() {
    }

    @Test
    void deleteAllBrands() {
    }
}