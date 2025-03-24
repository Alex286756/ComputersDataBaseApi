package ru.kuksov.compapi.service;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.kuksov.compapi.controller.BrandController;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    BrandService service;

    @Test
    @DisplayName("findAllBrands возвращает список брендов")
    void findAllBrands_ReturnListOfBrands() {
        // given
        var brands = List.of(new Brand(1, "Первая задача"),
                new Brand(2, "Вторая задача"));
        doReturn(brands).when(this.brandRepository).findAll();

        // when
        var result = this.service.findAllBrands();

        // then
        assertNotNull(result);
        assertEquals(brands, result);
    }

    @Test
    @DisplayName("findBrandById возвращает найденный бренд по id")
    void findBrandById_IdValid_ReturnBrandWithId() {
        // given
        var name = "Первая задача";
        var id = 1;
        Optional<Brand> brand = Optional.of(new Brand(id, name));

        doReturn(brand).when(this.brandRepository).findById(id);

        // when
        var result = this.service.findBrandById(id);

        // then
        assertNotNull(result);
        assertEquals(brand.get(), result);
    }

    @Test
    @DisplayName("findBrandById возвращает null в случае, если бренд не найден")
    void findBrandById_IdNotValid_ReturnNull() {
        // given
        var id = 1;

        doReturn(Optional.empty()).when(this.brandRepository).findById(id);

        // when
        var result = this.service.findBrandById(id);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("findBrandByName возвращает найденный бренд по name")
    void findBrandByName_IdValid_ReturnBrandWithName() {
        // given
        var name = "Первая задача";
        var id = 1;
        Optional<Brand> brand = Optional.of(new Brand(id, name));

        doReturn(brand).when(this.brandRepository).findByName(name);

        // when
        var result = this.service.findBrandByName(name);

        // then
        assertNotNull(result);
        assertEquals(brand.get(), result);
    }

    @Test
    @DisplayName("findBrandByName возвращает null в случае, если бренд не найден")
    void findBrandByName_IdNotValid_ReturnNull() {
        // given
        var name = "Первая задача";

        doReturn(Optional.empty()).when(this.brandRepository).findByName(name);

        // when
        var result = this.service.findBrandByName(name);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("addBrand возвращает бренд после добавления в БД")
    void addBrand_Valid_ReturnBrand() {
        // given
        var name = "Первая задача";
        var id = 1;
        Brand brand = new Brand(id, name);

        doReturn(brand).when(this.brandRepository).save(any());

        // when
        var result = this.service.addBrand(name);

        // then
        assertNotNull(result);
        assertEquals(brand, result);
    }

    @Test
    @DisplayName("updateBrand возвращает бренд после редактирования")
    void updateBrand_Success_ReturnBrand() {
        // given
        var id = 1;
        var name = "Третья задача";
        Brand brand = new Brand(id, name);
        doReturn(Optional.of(brand)).when(this.brandRepository).findById(id);
        doReturn(brand).when(this.brandRepository).save(any());

        // when
        var result = this.service.updateBrand(id, name);

        // then
        assertNotNull(result);
        assertEquals(brand, result);
    }

    @Test
    @DisplayName("updateBrand возвращает null если бренд не был найден")
    void updateBrand_BrandNotFound_ReturnFalse() {
        // given
        var id = 1;
        var name = "Третья задача";
        doReturn(Optional.empty()).when(this.brandRepository).findById(id);

        // when
        var result = this.service.updateBrand(id, name);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("deleteBrand возвращает true")
    void deleteBrand_Success_ReturnTrue() {
        // given
        var id = 1;
        var name = "Третья задача";
        Brand brand = new Brand(id, name);
        doReturn(Optional.of(brand)).when(this.brandRepository).findById(id);
        doNothing().when(this.brandRepository).deleteById(id);

        // when
        var result = this.service.deleteBrand(id);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("deleteBrand возвращает false, т.к. бренда нет")
    void deleteBrand_BrandNotFound_ReturnFalse() {
        // given
        var id = 1;
        doReturn(Optional.empty()).when(this.brandRepository).findById(id);

        // when
        var result = this.service.deleteBrand(id);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("deleteBrand возвращает false из-за ошибки удаления")
    void deleteBrand_Failure_ReturnFalse() {
        // given
        var id = 1;
        var name = "Третья задача";
        Brand brand = new Brand(id, name);
        doReturn(Optional.of(brand)).when(this.brandRepository).findById(id);
        doThrow(ObjectNotFoundException.class).when(this.brandRepository).deleteById(id);

        // when
        var result = this.service.deleteBrand(id);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("deleteAllBrands возвращает true")
    void deleteAllBrands_Success_ReturnTrue() {
        // given
        doNothing().when(this.brandRepository).deleteAll();

        // when
        var result = this.service.deleteAllBrands();

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("deleteAllBrands возвращает false")
    void deleteAllBrands_Failure_ReturnFalse() {
        // given
        doThrow(ObjectNotFoundException.class).when(this.brandRepository).deleteAll();

        // when
        var result = this.service.deleteAllBrands();

        // then
        assertFalse(result);
    }

}