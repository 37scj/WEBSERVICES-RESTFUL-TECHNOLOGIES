package br.com.fiap.drone.producer.service.impl;

import br.com.fiap.drone.producer.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaMailService implements MailService {

    Logger logger = LoggerFactory.getLogger(JavaMailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${checksensors.send-to}")
    private String[] sendTo;

    public void sendEmail(String msgBody) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendTo);

        msg.setSubject("Email drone");
        msg.setText(msgBody);

        logger.info("JavaMailService.sendEmail: " + msgBody);

        javaMailSender.send(msg);

    }


}
