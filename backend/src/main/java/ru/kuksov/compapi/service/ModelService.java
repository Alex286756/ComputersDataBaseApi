package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.repository.ModelRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    public List<Model> getAllModels() {
        return this.modelRepository.findAll();
    }

    public Model addModel(String name) {
        return this.modelRepository.save(
                Model.builder()
                        .name(name)
                        .build()
        );
    }

    public void updateModel(int id, String name) {
        Optional<Model> model = this.modelRepository.findById(id);
        if (model.isPresent()) {
            model.get().setName(name);
            this.modelRepository.save(model.get());
        }
    }

    public void deleteModel(int id) {
        this.modelRepository.deleteById(id);
    }

    public Optional<Model> findTypeByName(String name) {
        return this.modelRepository.findByName(name);
    }

    public Optional<Model> findTypeById(int id) {
        return this.modelRepository.findById(id);
    }
}
    