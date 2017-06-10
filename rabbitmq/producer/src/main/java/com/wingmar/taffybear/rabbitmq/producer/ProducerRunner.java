package com.wingmar.taffybear.rabbitmq.producer;

import com.wingmar.taffybear.rabbitmq.connector.Connector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerRunner {
    public static void main(String[] args) throws IOException, TimeoutException {
        if (args.length == 0) {
            System.err.println("Usage: <message1>:<sleepTimeSec1> <message2:sleepTimeSec2>\n" +
                    "Eg. \"first message:5\" \"second message:3\"");
        } else {
            final Connector connector = new Connector();
            for (String arg : args) {
                final String[] arr = arg.split(":");
                new Producer(connector).produce(arr[0], Integer.parseInt(arr[1]));
            }
        }
    }
}
