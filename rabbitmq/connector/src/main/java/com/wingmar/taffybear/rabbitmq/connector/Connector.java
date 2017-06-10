package com.wingmar.taffybear.rabbitmq.connector;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Connector implements Closeable {

    private Connection connection;
    private Channel channel;

    public Channel connect(final ChannelName channelName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(channelName.name(), true, false, false, null);
        return channel;
    }

    @Override
    public void close() throws IOException {
        try {
            channel.close();
        } catch (TimeoutException e) {
            throw new IllegalStateException("Could not close channel.");
        }
        connection.close();
    }
}
