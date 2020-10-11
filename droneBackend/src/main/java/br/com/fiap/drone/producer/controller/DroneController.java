package br.com.fiap.drone.producer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.drone.producer.dto.DroneDTO;
import br.com.fiap.drone.producer.service.DroneService;

@RestController
@RequestMapping("drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    /**
     * Cadastrar drone
     *
     * @param drone
     * @return
     */
    
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DroneDTO addDrone(@RequestBody DroneDTO drone) {
        return droneService.storeDroneInfo(drone);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DroneDTO patchDrone(@RequestBody DroneDTO drone) {
        if (drone.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Necessário ID do Drone");
        }
        return droneService.storeDroneInfo(drone);
    }

    /**
     * Localizar drone
     */
    @GetMapping()
    public List<DroneDTO> getAll() {
        return droneService.findAllDrone();
    }

    @GetMapping("/{id}")
    public DroneDTO getById(@PathVariable Long id) {
        return droneService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Drone não encontrado (id: " + id.toString() + ")"));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteDrone(@PathVariable Long id) {
        droneService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Drone não encontrado (id: " + id.toString() + ")"));
        droneService.deleteById(id);
    }
}
