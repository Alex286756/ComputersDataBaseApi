package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Type;
import ru.kuksov.compapi.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        return this.typeRepository.findAll();
    }

    public Type addType(String name) {
        return this.typeRepository.save(
                Type.builder()
                    .name(name)
                    .build()
        );
    }

    public void updateType(int id, String name) {
        Optional<Type> type = this.typeRepository.findById(id);
        if (type.isPresent()) {
            type.get().setName(name);
            this.typeRepository.save(type.get());
        }
    }

    public void deleteType(int id) {
        this.typeRepository.deleteById(id);
    }

    public Optional<Type> findTypeByName(String name) {
        return this.typeRepository.findByName(name);
    }

    public Optional<Type> findTypeById(int id) {
        return this.typeRepository.findById(id);
    }
}
