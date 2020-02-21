package com.tpappsmoviles.serviapp.activity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class FirebaseMessagingSnippets {

    public void sendToToken() throws FirebaseMessagingException {
        // [START send_to_token]
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "YOUR_REGISTRATION_TOKEN";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        // [END send_to_token]
    }

    public static void sendToTopic(String tipoNotificacion, String textoNotificacion, String nombreTienda) throws FirebaseMessagingException {
        // [START send_to_topic]
        // The topic name can be optionally prefixed with "/topics/".
        String topic = nombreTienda;

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("tipoNotificacion", tipoNotificacion)
                .putData("textoNotificacion", textoNotificacion)
                .setTopic(topic)
                .build();

        RemoteMessage m = new RemoteMessage.Builder("mensaje")
                .addData("tipoNotificacion", tipoNotificacion)
                .addData("textoNotificacion", textoNotificacion)
                .build();

        // Send a message to the devices subscribed to the provided topic.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        // [END send_to_topic]
    }

}
