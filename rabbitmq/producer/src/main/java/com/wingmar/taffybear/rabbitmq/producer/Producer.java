package com.wingmar.taffybear.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.wingmar.taffybear.rabbitmq.connector.ChannelName;
import com.wingmar.taffybear.rabbitmq.connector.Connector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Producer rabbitmq class.
 */
class Producer {

    private final Connector connector;

    Producer(Connector connector) {
        this.connector = connector;
    }

    void produce(final String message, final int sleepTimeSeconds) throws IOException, TimeoutException {
        final Channel channel = connector.connect(ChannelName.TASK_QUEUE);
        final String toSend = message + ":" + sleepTimeSeconds;
        channel.txSelect();
        channel.basicPublish("", ChannelName.TASK_QUEUE.name(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                toSend.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + toSend + "'");
        channel.txCommit();
        System.out.println("Consumed");

        connector.close();
    }
}
