package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Brand;

import java.util.Optional;

/**
 * Интерфейс работы с записями о брендах
 * @author Куксов Алексей Генндьевич
 * @version 2.0
 */
@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {

    /**
     * Поиск бренда по наименованию
     *
     * @param name наименование бренда
     * @return результат поиска (бренд или <u>null</u>)
     */
    Optional<Brand> findByName(String name);
}
