package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Model;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends CrudRepository<Model, Integer> {

    @Override
    public List<Model> findAll();

    Optional<Model> findByName(String name);
}
