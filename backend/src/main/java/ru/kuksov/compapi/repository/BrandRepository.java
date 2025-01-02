package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Brand;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {

    @Override
    public List<Brand> findAll();

    Optional<Brand> findByName(String name);
}
