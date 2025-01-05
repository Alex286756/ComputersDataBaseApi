package ru.kuksov.compapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.compapi.model.Device;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {

    @Override
    public List<Device> findAll();

    @Override
    public Optional<Device> findById(String id);
}
