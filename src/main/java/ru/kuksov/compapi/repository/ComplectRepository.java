package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Complect;

import java.util.List;

@Repository
public interface ComplectRepository extends CrudRepository<Complect, Integer> {

    @Override
    public List<Complect> findAll();
}
