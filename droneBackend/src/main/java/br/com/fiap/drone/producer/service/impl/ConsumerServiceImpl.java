package br.com.fiap.drone.producer.service.impl;

import br.com.fiap.drone.producer.dto.DroneDTO;
import br.com.fiap.drone.producer.service.ConsumerService;
import br.com.fiap.drone.producer.service.DroneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * (Temperatura >= 35 ou <= 0) OU (Umidade do ar <= 15%).
 * Para enviar e-mail pode usar um SaaS.
 *
 * @author lucasrodriguesdonascimento
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    private DroneService droneService;

    public ConsumerServiceImpl(DroneService droneService) {
        this.droneService = droneService;
    }

    @Override
    public void receiveDroneInfo(DroneDTO drone) {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(drone);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("Recebendo mensagem: " + json);

        droneService.storeDroneInfo(drone);

    }

}
