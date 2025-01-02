package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.controller.dto.DeviceRequest;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.model.Complect;
import ru.kuksov.compapi.model.Device;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.model.Type;
import ru.kuksov.compapi.repository.BrandRepository;
import ru.kuksov.compapi.repository.ComplectRepository;
import ru.kuksov.compapi.repository.DeviceRepository;
import ru.kuksov.compapi.repository.ModelRepository;
import ru.kuksov.compapi.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final BrandRepository brandRepository;
    private final ComplectRepository complectRepository;
    private final ComplectService complectService;
    private final DeviceRepository deviceRepository;
    private final ModelRepository modelRepository;
    private final TypeRepository typeRepository;

    public List<Device> getAllDevices() {
        return this.deviceRepository.findAll();
    }

    public Device addDevice(DeviceRequest request) {
        Complect complect = getComplect(request.complectId());
        Device device = Device.builder()
                .id(request.id())
                .type(getType(request.typeId()))
                .brand(getBrand(request.brandId()))
                .model(getModel(request.modelId()))
                .serialNumber(request.serialNumber())
                .year(request.year())
                .complect(complect)
                .build();
        this.deviceRepository.save(device);

        complect.getDevices().add(device);
        this.complectRepository.save(complect);
        return device;
    }

    public void updateDevice(String deviceId, DeviceRequest request) {
        Optional<Device> oldDeviceOptional = this.deviceRepository.findById(deviceId);
        if (oldDeviceOptional.isEmpty())
            return;
        if (oldDeviceOptional.get().getComplect().getId() != request.complectId()) {
            updateComplectForDevice(deviceId, request);
            return;
        }
        Complect complect = getComplect(request.complectId());
        Device device = Device.builder()
                .id(deviceId)
                .type(getType(request.typeId()))
                .brand(getBrand(request.brandId()))
                .model(getModel(request.modelId()))
                .serialNumber(request.serialNumber())
                .year(request.year())
                .complect(complect)
                .build();
        this.deviceRepository.save(device);
    }

    private void updateComplectForDevice(String deviceId, DeviceRequest request) {
        this.complectService.removeDeviceFromComplect(deviceId);
        Optional<Device> deviceOptional = this.deviceRepository.findById(deviceId);
        Complect complect = getComplect(request.complectId());
        if (deviceOptional.isEmpty())
            return;
        deviceOptional.get().setComplect(complect);
        complect.getDevices().add(deviceOptional.get());
        this.complectRepository.save(complect);
        this.deviceRepository.save(deviceOptional.get());
    }

    public void deleteDevice(String id) {
        Optional<Device> deviceOptional = this.deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            this.complectService.removeDeviceFromComplect(id);
            this.deviceRepository.deleteById(id);
        }
    }

    private Type getType(int id) {
        Optional<Type> optional = this.typeRepository.findById(id);
        return optional.orElseGet(
                () -> this.typeRepository.save(Type.builder()
                        .name("Unknown")
                        .build()));
    }

    private Brand getBrand(int id) {
        Optional<Brand> optional = this.brandRepository.findById(id);
        return optional.orElseGet(
                () -> this.brandRepository.save(Brand.builder()
                        .name("Unknown")
                        .build()));
    }

    private Model getModel(int id) {
        Optional<Model> optional = this.modelRepository.findById(id);
        return optional.orElseGet(
                () -> this.modelRepository.save(Model.builder()
                        .name("Unknown")
                        .build()));
    }

    private Complect getComplect(int id) {
        Optional<Complect> complectOptional = this.complectRepository.findById(id);
        return complectOptional.orElseGet(
                () -> this.complectRepository.save(Complect.builder()
                        .name(String.valueOf(id))
                        .build()));
    }

    public Optional<Device> findDeviceById(String id) {
        return this.deviceRepository.findById(id);
    }
}
