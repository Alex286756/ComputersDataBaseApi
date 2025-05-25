package ru.kuksov.compapi.service;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.repository.ModelRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    ModelService service;

    @Test
    @DisplayName("findAllModels возвращает список моделей")
    void findAllModels_ReturnListOfModels() {
        // given
        var models = List.of(new Model(1, "Первая задача"),
                new Model(2, "Вторая задача"));
        doReturn(models).when(this.modelRepository).findAll();

        // when
        var result = this.service.findAllModels();

        // then
        assertNotNull(result);
        assertEquals(models, result);
    }

    @Test
    @DisplayName("findModelById возвращает найденную модель по id")
    void findModelById_IdValid_ReturnModelWithId() {
        // given
        var name = "Первая задача";
        var id = 1;
        Optional<Model> model = Optional.of(new Model(id, name));

        doReturn(model).when(this.modelRepository).findById(id);

        // when
        var result = this.service.findModelById(id);

        // then
        assertTrue(result.isPresent());
        assertEquals(model, result);
    }

    @Test
    @DisplayName("findModelById возвращает null в случае, если модель не найдена")
    void findModelById_IdNotValid_ReturnNull() {
        // given
        var id = 1;

        doReturn(Optional.empty()).when(this.modelRepository).findById(id);

        // when
        var result = this.service.findModelById(id);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findModelByName возвращает найденную модель по name")
    void findModelByName_IdValid_ReturnModelWithName() {
        // given
        var name = "Первая задача";
        var id = 1;
        Optional<Model> model = Optional.of(new Model(id, name));

        doReturn(model).when(this.modelRepository).findByName(name);

        // when
        var result = this.service.findModelByName(name);

        // then
        assertTrue(result.isPresent());
        assertEquals(model, result);
    }

    @Test
    @DisplayName("findModelByName возвращает null в случае, если модель не найдена")
    void findModelByName_IdNotValid_ReturnNull() {
        // given
        var name = "Первая задача";

        doReturn(Optional.empty()).when(this.modelRepository).findByName(name);

        // when
        var result = this.service.findModelByName(name);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("addModel возвращает модель после добавления в БД")
    void addModel_Valid_ReturnModel() {
        // given
        var name = "Первая задача";
        var id = 1;
        Model model = new Model(id, name);

        doReturn(model).when(this.modelRepository).save(any());

        // when
        var result = this.service.addModel(name);

        // then
        assertNotNull(result);
        assertEquals(model, result);
    }

    @Test
    @DisplayName("updateModel возвращает модель после редактирования")
    void updateModel_Success_ReturnModel() {
        // given
        var id = 1;
        var name = "Третья задача";
        Model model = new Model(id, name);
        doReturn(Optional.of(model)).when(this.modelRepository).findById(id);
        doReturn(model).when(this.modelRepository).save(any());

        // when
        var result = this.service.updateModel(id, name);

        // then
        assertNotNull(result);
        assertEquals(model, result);
    }

    @Test
    @DisplayName("updateModel возвращает null если модель не была найдена")
    void updateModel_ModelNotFound_ReturnFalse() {
        // given
        var id = 1;
        var name = "Третья задача";
        doReturn(Optional.empty()).when(this.modelRepository).findById(id);

        // when
        var result = this.service.updateModel(id, name);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("deleteModel возвращает true")
    void deleteModel_Success_ReturnTrue() {
        // given
        var id = 1;
        var name = "Третья задача";
        Model model = new Model(id, name);
        doReturn(Optional.of(model)).when(this.modelRepository).findById(id);
        doNothing().when(this.modelRepository).deleteById(id);

        // when
        var result = this.service.deleteModel(id);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("deleteModel возвращает false, т.к. модели нет")
    void deleteModel_ModelNotFound_ReturnFalse() {
        // given
        var id = 1;
        doReturn(Optional.empty()).when(this.modelRepository).findById(id);

        // when
        var result = this.service.deleteModel(id);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("deleteModel возвращает false из-за ошибки удаления")
    void deleteModel_Failure_ReturnFalse() {
        // given
        var id = 1;
        var name = "Третья задача";
        Model model = new Model(id, name);
        doReturn(Optional.of(model)).when(this.modelRepository).findById(id);
        doThrow(ObjectNotFoundException.class).when(this.modelRepository).deleteById(id);

        // when
        var result = this.service.deleteModel(id);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("deleteAllModels возвращает true")
    void deleteAllModels_Success_ReturnTrue() {
        // given
        doNothing().when(this.modelRepository).deleteAll();

        // when
        var result = this.service.deleteAllModels();

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("deleteAllModels возвращает false")
    void deleteAllModels_Failure_ReturnFalse() {
        // given
        doThrow(ObjectNotFoundException.class).when(this.modelRepository).deleteAll();

        // when
        var result = this.service.deleteAllModels();

        // then
        assertFalse(result);
    }

}