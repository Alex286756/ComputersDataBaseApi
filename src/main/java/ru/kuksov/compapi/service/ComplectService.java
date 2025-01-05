package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Complect;
import ru.kuksov.compapi.model.Device;
import ru.kuksov.compapi.repository.ComplectRepository;
import ru.kuksov.compapi.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ComplectService {

    private final ComplectRepository complectRepository;
    private final DeviceRepository deviceRepository;

    public List<Complect> getAllComplects() {
        return this.complectRepository.findAll();
    }

    public Complect addComplect(String name, List<String> devicesId) {
        Stream<Optional<Device>> optionalStream = devicesId.stream().map(this.deviceRepository::findById);
        List<Device> devicesList = optionalStream.filter(Optional::isPresent).map(Optional::get).toList();
        Complect complect = this.complectRepository.save(Complect.builder()
                .name(name)
                .devices(List.of())
                .build());

        devicesList.forEach(device -> {
            Complect oldComplect = device.getComplect();
            if (oldComplect != null) {
                oldComplect.getDevices().remove(device);
            }
            device.setComplect(complect);
            this.deviceRepository.save(device);

            List<Device> complectDevices = new ArrayList<>(complect.getDevices());
            complectDevices.add(device);
            complect.setDevices(complectDevices);
            this.complectRepository.save(complect);
        });
        return complect;
    }

    public void updateComplect(int id, String name) {
        Optional<Complect> complect = this.complectRepository.findById(id);
        if (complect.isPresent()) {
            complect.get().setName(name);
            this.complectRepository.save(complect.get());
        }
    }

    public void deleteComplect(int id) {
        this.complectRepository.deleteById(id);
    }

    public void removeDeviceFromComplect(String deviceId) {
        Optional<Device> deviceOptional = this.deviceRepository.findById(deviceId);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            Complect complect = device.getComplect();
            if (complect != null) {
                complect.getDevices().remove(device);
                this.complectRepository.save(complect);
            }
        }
    }

    public Optional<Complect> findComplectById(int id) {
        return this.complectRepository.findById(id);
    }
}
