package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.repository.ModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс содержит всю логику, касающуюся работы с моделями
 * @author Куксов Алексей Генндьевич
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    /**
     * Поиск всех моделей в БД
     *
     * @return список моделей
     */
    public List<Model> findAllModels() {
        var result = new ArrayList<Model>();
        this.modelRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * Поиск модели по идентификатору
     *
     * @param id идентификатор
     * @return модель или <u>null</u> если такой модели в БД нет
     */
    public Optional<Model> findModelById(int id) {
        return this.modelRepository.findById(id);
    }

    /**
     * Поиск модели по названию
     *
     * @param name название модели
     * @return модель или <u>null</u> если такой модели в БД нет
     */
    public Optional<Model> findModelByName(String name) {
        return this.modelRepository.findByName(name);
    }

    /**
     * Добавление модели в БД
     *
     * @param modelName название модели
     * @return модель, добавленная в БД
     */
    public Model addModel(String modelName) {
        return this.modelRepository.save(
                Model.builder()
                        .name(modelName)
                        .build()
        );
    }

    /**
     * Обновление записи о модели
     *
     * @param id идентификатор модели
     * @param name новое название модели
     * @return модель после обновления записи или <u>null</u> если такой модели в БД нет
     */
    public Model updateModel(int id, String name) {
        Optional<Model> model = this.modelRepository.findById(id);
        if (model.isPresent()) {
            model.get().setName(name);
            return this.modelRepository.save(model.get());
        }
        return null;
    }

    /**
     * Удаление модели по идентификатору из БД
     *
     * @param id идентификатор модели для удаления
     * @return возвращает <u>true</u>, если стирание прошло удачно,
     * иначе - <u>false</u>
     */
    public boolean deleteModel(int id) {
        if (this.modelRepository.findById(id).isEmpty())
            return false;
        try {
            this.modelRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Удаление всех моделей из БД
     *
     * @return возвращает <u>true</u>, если стирание прошло удачно,
     * иначе - <u>false</u>
     */
    public boolean deleteAllModels() {
        try {
            this.modelRepository.deleteAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
    