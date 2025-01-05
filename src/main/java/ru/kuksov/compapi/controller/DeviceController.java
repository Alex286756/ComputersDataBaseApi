package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.dto.DeviceRequest;
import ru.kuksov.compapi.controller.dto.DeviceResponse;
import ru.kuksov.compapi.model.Device;
import ru.kuksov.compapi.service.DeviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compapi/v1/devices")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Устройства")
public class DeviceController {

    private final DeviceService deviceService;

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех устройств")
    @GetMapping
    public List<DeviceResponse> getAllDevices() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<Device> devices = this.deviceService.getAllDevices();
        log.info(marker, "All devices successfully found");
        return  devices.stream().map(device -> new DeviceResponse(
                device.getId(),
                device.getType().getId(),
                device.getBrand().getId(),
                device.getModel().getId(),
                device.getSerialNumber(),
                device.getYear(),
                device.getComplect().getId())
        ).toList();
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение устройства по id")
    @GetMapping("/{id}")
    public DeviceResponse getDeviceById(@PathVariable String id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get device id = " + id);
        Optional<Device> devices = this.deviceService.findDeviceById(id);
        log.info(marker, "Search device id {} finished", id);
        return devices.map(value -> new DeviceResponse(
                        value.getId(),
                        value.getType().getId(),
                        value.getBrand().getId(),
                        value.getModel().getId(),
                        value.getSerialNumber(),
                        value.getYear(),
                        value.getComplect().getId()))
                .orElseGet(() -> new DeviceResponse("", -1, -1, -1, "", -1, -1));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление нового устройства")
    @PostMapping
    public ResponseEntity<?> addDevice(@RequestBody @Validated DeviceRequest request) {
        try {
            Device newDevice = this.deviceService.addDevice(request);
            LabelMarker marker = LabelMarker.of("ComputersDB", () ->
                    "Http status");
            log.info(marker, "Код ответа на запрос: 200 ");
            return ResponseEntity.ok(new DeviceResponse(
                    newDevice.getId(),
                    newDevice.getType().getId(),
                    newDevice.getBrand().getId(),
                    newDevice.getModel().getId(),
                    newDevice.getSerialNumber(),
                    newDevice.getYear(),
                    newDevice.getComplect().getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование данных устройства")
    @PatchMapping("/{id}")
    public void editDevice(@PathVariable String id,
                             @RequestBody @Validated DeviceRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        this.deviceService.updateDevice(id, request);
        log.info(marker, "Device %s successfully edit".formatted(request.id()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление устройства")
    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable String id) {
        this.deviceService.deleteDevice(id);
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
        log.info(marker, "Device %s successfully delete".formatted(id));
    }
}
