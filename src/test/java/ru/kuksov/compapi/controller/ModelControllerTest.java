package ru.kuksov.compapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.kuksov.compapi.controller.dto.ModelRequest;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.service.ModelService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {

    @Mock
    ModelService modelService;

    @InjectMocks
    ModelController controller;

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/models " +
            "возвращает HTTP-ответ со статусом 200 OK и списком моделей")
    void getAllModels_ReturnsValidResponseEntity() {
        // given
        var models = List.of(new Model(1, "Первая задача"),
                new Model(2, "Вторая задача"));
        doReturn(models).when(this.modelService).findAllModels();

        // when
        var responseEntity = this.controller.getAllModels();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(models, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/models/2 " +
            "возвращает HTTP-ответ со статусом 200 OK и моделью с указанным id=2")
    void getModelById_IdExist_ReturnsValidResponseEntity() {
        // given
        var id = 2;
        var model = Optional.of(new Model(2, "Вторая задача"));
        doReturn(model).when(this.modelService).findModelById(id);

        // when
        var responseEntity = this.controller.getModelById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(model.get(), responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/models/3 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. модели с id=3 нет")
    void getModelById_IdNotExist_ReturnsValidResponseEntity() {
        // given
        var id = 3;
        doReturn(Optional.empty()).when(this.modelService).findModelById(id);

        // when
        var responseEntity = this.controller.getModelById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/models/name/Alex " +
            "возвращает HTTP-ответ со статусом 200 OK и модель с name=Alex")
    void getModelByName_NameExist_ReturnsValidResponseEntity() {
        // given
        var name = "Alex";
        var model = Optional.of(new Model(2, name));
        doReturn(model).when(this.modelService).findModelByName(name);

        // when
        var responseEntity = this.controller.getModelByName(name);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(model.get(), responseEntity.getBody());
    }

    @Test
    @DisplayName("GET http://localhost:8081/compapi/v2/models/name/Bob " +
            "возвращает HTTP-ответ со статусом 404 NOT_FOUND, т.к. модели с названием Bob нет")
    void getModelByName_NameNotExist_ReturnsValidResponseEntity() {
        // given
        var name = "Alex";
        doReturn(Optional.empty()).when(this.modelService).findModelByName(name);

        // when
        var responseEntity = this.controller.getModelByName(name);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("POST http://localhost:8081/compapi/v2/models " +
            "возвращает HTTP-ответ со статусом 201 CREATED и моделью, добавленной в базу данных")
    void addNewModel_PayloadIsValid_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var request = new ModelRequest(name);
        var model = new Model(3, name);

        doReturn(model).when(this.modelService).addModel(request.name());

        // when
        var responseEntity = this.controller.addNewModel(request);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody());
        if (responseEntity.getBody() instanceof Model responseModel) {
            assertEquals(name, responseModel.getName());
        } else {
            assertInstanceOf(Model.class, responseEntity.getBody());
        }
//        verifyNoMoreInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("POST http://localhost:8081/compapi/v2/models " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. в базу данных нельзя добавлять модели " +
            "с пустым названием")
    void addNewModel_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var name = "";

        // when
        var responseEntity = this.controller.addNewModel(new ModelRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/models/1 " +
            "возвращает HTTP-ответ со статусом 200 OK и модель после изменения")
    void editModel_PayloadValid_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var id = 1;
        var model = new Model(id, name);

        doReturn(model).when(this.modelService).updateModel(id, name);

        // when
        var responseEntity = this.controller.editModel(id, new ModelRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(model, responseEntity.getBody());
//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/models/20 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. в базе данных не может быть модели " +
            "с пустым названием")
    void editModel_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var name = "";
        var id = 20;

        // when
        var responseEntity = this.controller.editModel(id, new ModelRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("PATCH http://localhost:8081/compapi/v2/models/20 " +
            "возвращает HTTP-ответ со статусом 404 NOT_FOUND, т.к. в базе данных не найден модель " +
            "с id=20")
    void editModel_ModelNotFoundById_ReturnsValidResponseEntity() {
        // given
        var name = "Третья задача";
        var id = 20;

        doReturn(null).when(this.modelService).updateModel(id, name);

        // when
        var responseEntity = this.controller.editModel(id, new ModelRequest(name));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/models " +
            "возвращает HTTP-ответ со статусом 200 OK и сообщение об удалении")
    void deleteAllModels_Success_ReturnsValidResponseEntity() {
        // given
        doReturn(true).when(this.modelService).deleteAllModels();

        // when
        var responseEntity = this.controller.deleteAllModels();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Удалены все модели", responseEntity.getBody());
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/models " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. произошла какая то ошибка" +
            "при удалении моделей")
    void deleteAllModels_GetError_ReturnsValidResponseEntity() {
        // given
        doReturn(false).when(this.modelService).deleteAllModels();

        // when
        var responseEntity = this.controller.deleteAllModels();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Ошибка при удалении всех моделей", responseEntity.getBody());

//        verifyNoInteractions(this.modelRepository);
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/models/1 " +
            "возвращает HTTP-ответ со статусом 200 OK и сообщение об удалении")
    void deleteModelById_Success_ReturnsValidResponseEntity() {
        // given
        var id = 1;
        doReturn(true).when(this.modelService).deleteModel(id);

        // when
        var responseEntity = this.controller.deleteModelById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Модель %d удалена из БД".formatted(id), responseEntity.getBody());
    }

    @Test
    @DisplayName("DELETE http://localhost:8081/compapi/v2/models/1 " +
            "возвращает HTTP-ответ со статусом 400 BAD_REQUEST, т.к. произошла какая то ошибка" +
            "при удалении модели")
    void deleteModelById_GetError_ReturnsValidResponseEntity() {
        // given
        var id = 1;
        doReturn(false).when(this.modelService).deleteModel(id);

        // when
        var responseEntity = this.controller.deleteModelById(id);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Проблемы при удалении модели № %d".formatted(id), responseEntity.getBody());

//        verifyNoInteractions(this.modelRepository);
    }

}