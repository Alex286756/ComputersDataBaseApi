package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.repository.BrandRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс содержит всю логику, касающуюся работы с брендами
 * @author Куксов Алексей Генндьевич
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    /**
     * Поиск всех брендов в БД
     *
     * @return список брендов
     */
    public List<Brand> findAllBrands() {
        List<Brand> result = new ArrayList<>();
        this.brandRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * Поиск бренда по идентификатору
     *
     * @param id идентификатор
     * @return бренд или <u>null</u> если такого бренда в БД нет
     */
    public Brand findBrandById(int id) {
        return this.brandRepository.findById(id).orElse(null);
    }

    /**
     * Поиск бренда по названию
     *
     * @param name название бренда
     * @return бренд или <u>null</u> если такого бренда в БД нет
     */
    public Brand findBrandByName(String name) {
        return this.brandRepository.findByName(name).orElse(null);
    }

    /**
     * Добавление бренда в БД
     *
     * @param brandName название бренда
     * @return бренд, добавленный в БД
     */
    public Brand addBrand(String brandName) {
        return this.brandRepository.save(
                Brand.builder()
                        .name(brandName)
                        .build()
        );
    }

    /**
     * Обновление записи о бренде
     *
     * @param id идентификатор бренда
     * @param name новое название бренда
     * @return бренд после обновления записи или <u>null</u> если такого бренда в БД нет
     */
    public Brand updateBrand(int id, String name) {
        Optional<Brand> brand = this.brandRepository.findById(id);
        if (brand.isPresent()) {
            brand.get().setName(name);
            return this.brandRepository.save(brand.get());
        }
        return null;
    }

    /**
     * Удаление бренда по идентификатору из БД
     *
     * @param id идентификатор бренда для удаления
     * @return возвращает <u>true</u>, если стирание прошло удачно,
     * иначе - <u>false</u>
     */
    public boolean deleteBrand(int id) {
        if (this.brandRepository.findById(id).isEmpty())
            return false;
        try {
            this.brandRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Удаление всех брендов из БД
     *
     * @return возвращает <u>true</u>, если стирание прошло удачно,
     * иначе - <u>false</u>
     */
    public boolean deleteAllBrands() {
        try {
            this.brandRepository.deleteAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
