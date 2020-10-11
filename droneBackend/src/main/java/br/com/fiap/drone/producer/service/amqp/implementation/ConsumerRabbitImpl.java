package br.com.fiap.drone.producer.service.amqp.implementation;

import br.com.fiap.drone.producer.service.amqp.AmqpConsumer;
import br.com.fiap.drone.producer.dto.DroneDTO;
import br.com.fiap.drone.producer.service.ConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe responsavel por ser ouvinte das filas de rabbitmq
 *
 * @author lucasrodriguesdonascimento
 */
@Component
public class ConsumerRabbitImpl implements AmqpConsumer<DroneDTO> {

    private Logger logger = LoggerFactory.getLogger(ConsumerRabbitImpl.class);

    @Autowired
    private ConsumerService consumerService;

    /**
     * Anotacao @RabbitListener
     * Rabbit message listener on the specified queues() (or bindings())
     */
    @Override
    @RabbitListener(queues = "${spring.rabbitmq.routing-key}")
    public void consumer(DroneDTO drone) {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(drone);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("Consumindo: " + json);
        try {
            consumerService.receiveDroneInfo(drone);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }

}
