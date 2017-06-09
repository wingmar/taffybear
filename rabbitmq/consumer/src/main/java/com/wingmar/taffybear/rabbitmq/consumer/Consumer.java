package com.smartbrief.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.smartbrief.rabbitmq.connector.ChannelName;
import com.smartbrief.rabbitmq.connector.Connector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer rabbitmq class.
 */
class Consumer {

    private final Connector connector;

    Consumer(Connector connector) {
        this.connector = connector;
    }

    void consume() throws IOException, TimeoutException {
        final Channel channel = connector.connect(ChannelName.TASK_QUEUE);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        final com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");

                try {
                    final int sleepTimeSeconds = Integer.parseInt(message.split(":")[1]);
                    System.out.println("Sleeping for " + sleepTimeSeconds + " sec.");
                    sleepForSeconds(sleepTimeSeconds);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(ChannelName.TASK_QUEUE.name(), false, consumer);
    }

    private static void sleepForSeconds(int sleepTimeSeconds) {
        final long sleepTimeMs = sleepTimeSeconds * 1000;
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
