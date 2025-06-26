package com.paqueteria.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void enviarWhatsApp(String telefono, String mensaje) {
        log.info("📲 Enviando WhatsApp a {}: {}", telefono, mensaje);
        // Aquí podrías usar la API de Twilio u otra
    }

    public void enviarSMS(String telefono, String mensaje) {
        log.info("📩 Enviando SMS a {}: {}", telefono, mensaje);
        // Aquí podrías integrar un proveedor como Nexmo o Twilio
    }
}
