package com.wingmar.taffybear.rabbitmq.consumer;

import com.wingmar.taffybear.rabbitmq.connector.Connector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerRunner {
    public static void main(String[] args) throws IOException, TimeoutException {
        new Consumer(new Connector()).consume();
    }
}
