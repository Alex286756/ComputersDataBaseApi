package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Model;

import java.util.Optional;

/**
 * Интерфейс работы с записями о моделях
 * @author Куксов Алексей Геннадьевич
 * @version 2.0
 */
@Repository
public interface ModelRepository extends CrudRepository<Model, Integer> {

//    @Override
//    public List<Model> findAll();

    /**
     * Поиск модели по наименованию
     *
     * @param name наименование модели
     * @return результат поиска (модель или <u>null</u>)
     */
    Optional<Model> findByName(String name);
}
