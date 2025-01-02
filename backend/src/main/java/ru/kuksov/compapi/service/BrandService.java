package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return this.brandRepository.findAll();
    }

    public Brand addBrand(String brandName) {
        return this.brandRepository.save(
                Brand.builder()
                        .name(brandName)
                        .build()
        );
    }

    public void updateBrand(int id, String name) {
        Optional<Brand> brand = this.brandRepository.findById(id);
        if (brand.isPresent()) {
            brand.get().setName(name);
            this.brandRepository.save(brand.get());
        }
    }

    public void deleteBrand(int id) {
        this.brandRepository.deleteById(id);
    }

    public Optional<Brand> findTypeByName(String name) {
        return this.brandRepository.findByName(name);
    }

    public Optional<Brand> findTypeById(int id) {
        return this.brandRepository.findById(id);
    }
}
