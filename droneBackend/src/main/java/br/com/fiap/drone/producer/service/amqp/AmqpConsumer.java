package br.com.fiap.drone.producer.service.amqp;

/**
 * Responsavel por realizar o consume de qualquer broker AMQP
 * @author lucasrodriguesdonascimento
 *
 */
public interface AmqpConsumer<T> {
	
	void consumer(T t);

}
