package ru.kuksov.compapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.kuksov.compapi.controller.dto.BrandRequest;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.service.BrandService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

    @Mock
    BrandService brandService;

    @InjectMocks
    BrandController controller;

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/brands " +
            "возвращает HTTP-ответ со статусом 200 OK и списком брендов")
    void getAllBrands_ReturnsValidResponseEntity() {
        // given
        var brands = List.of(new Brand(1, "Первая задача"),
                new Brand(2, "Вторая задача"));
        doReturn(brands).when(this.brandService).findAllBrands();

        // when
        var responseEntity = this.controller.getAllBrands();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(brands, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/brands/2 " +
            "возвращает HTTP-ответ со статусом 200 OK и брендом с указанным id=2")
    void getBrandById_IdExist_ReturnsValidResponseEntity() {
        // given
        var id = 2;
        var brand = new Brand(2, "Вторая задача");
        doReturn(brand).when(this.brandService).findBrandById(id);

        // when
        var responseEntity = this.controller.getBrandById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(brand, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/brands/3 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. бренда с id=3 нет")
    void getBrandById_IdNotExist_ReturnsValidResponseEntity() {
        // given
        var id = 3;
        doReturn(null).when(this.brandService).findBrandById(id);

        // when
        var responseEntity = this.controller.getBrandById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/brands/name/Alex " +
            "возвращает HTTP-ответ со статусом 200 OK и брендом с name=Alex")
    void getBrandByName_NameExist_ReturnsValidResponseEntity() {
        // given
        var name = "Alex";
        var brand = new Brand(2, name);
        doReturn(brand).when(this.brandService).findBrandByName(name);

        // when
        var responseEntity = this.controller.getBrandByName(name);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(brand, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/brands/name/Bob " +
            "возвращает HTTP-ответ со статусом 404 NOT_FOUND, т.к. бренда с названием Bob нет")
    void getBrandByName_NameNotExist_ReturnsValidResponseEntity() {
        // given
        var name = "Alex";
        doReturn(null).when(this.brandService).findBrandByName(name);

        // when
        var responseEntity = this.controller.getBrandByName(name);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("POST http://localhost:8081/compapi/v2/brands " +
            "возвращает HTTP-ответ со статусом 201 CREATED и брендом, добавленном в базу данных")
    void addNewBrand_PayloadIsValid_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var request = new BrandRequest(name);
        var brand = new Brand(3, name);

        doReturn(brand).when(this.brandService).addBrand(request.getName());

        // when
        var responseEntity = this.controller.addNewBrand(request);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody());
        if (responseEntity.getBody() instanceof Brand responseBrand) {
            assertEquals(name, responseBrand.getName());
        } else {
            assertInstanceOf(Brand.class, responseEntity.getBody());
        }
//        verifyNoMoreInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("POST http://localhost:8081/compapi/v2/brands " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. в базу данных нельзя добавлять бренды" +
            "с пустым названием")
    void addNewBrand_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var name = "";

        // when
        var responseEntity = this.controller.addNewBrand(new BrandRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/brands/1 " +
            "возвращает HTTP-ответ со статусом 200 OK и бренд после изменения")
    void editBrand_PayloadValid_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var id = 1;
        var brand = new Brand(id, name);

        doReturn(brand).when(this.brandService).updateBrand(id, name);

        // when
        var responseEntity = this.controller.editBrand(id, new BrandRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(brand, responseEntity.getBody());
//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/brands/20 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. в базе данных не может быть бренда " +
            "с пустым названием")
    void editBrand_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var name = "";
        var id = 20;

        // when
        var responseEntity = this.controller.editBrand(id, new BrandRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/brands/20 " +
            "возвращает HTTP-ответ со статусом 404 NOT_FOUND, т.к. в базе данных не найден бренд " +
            "с id=20")
    void editBrand_BrandNotFoundById_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var id = 20;

        doReturn(null).when(this.brandService).updateBrand(id, name);

        // when
        var responseEntity = this.controller.editBrand(id, new BrandRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/brands " +
            "возвращает HTTP-ответ со статусом 200 OK и сообщение об удалении")
    void deleteAllBrands_Success_ReturnsValidResponseEntity() {
        // given
        doReturn(true).when(this.brandService).deleteAllBrands();

        // when
        var responseEntity = this.controller.deleteAllBrands();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Удалены все бренды", responseEntity.getBody());
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/brands " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. произошла какая то ошибка" +
            "при удалении брендов")
    void deleteAllBrands_GetError_ReturnsValidResponseEntity() {
        // given
        doReturn(false).when(this.brandService).deleteAllBrands();

        // when
        var responseEntity = this.controller.deleteAllBrands();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Ошибка при удалении всех брендов", responseEntity.getBody());

//        verifyNoInteractions(this.brandRepository);
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/brands/1 " +
            "возвращает HTTP-ответ со статусом 200 OK и сообщение об удалении")
    void deleteBrandById_Success_ReturnsValidResponseEntity() {
        // given
        var id = 1;
        doReturn(true).when(this.brandService).deleteBrand(id);

        // when
        var responseEntity = this.controller.deleteBrandById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Бренд %d удален из БД".formatted(id), responseEntity.getBody());
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/brands/1 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. произошла какая то ошибка" +
            "при удалении бренда")
    void deleteBrandById_GetError_ReturnsValidResponseEntity() {
        // given
        var id = 1;
        doReturn(false).when(this.brandService).deleteBrand(id);

        // when
        var responseEntity = this.controller.deleteBrandById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Проблемы при удалении бренда № %d".formatted(id), responseEntity.getBody());

//        verifyNoInteractions(this.brandRepository);
    }

}