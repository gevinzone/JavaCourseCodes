package io.kimmking.kmq.core;

import lombok.SneakyThrows;


public final class Kmq {

    private final String topic;

    private final int capacity;

    private KmqMessage[] queue;

    private int producerOffset = 0;
    private int consumerOffset = 0;

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new KmqMessage[capacity];
    }

    public boolean send(KmqMessage message) {
        synchronized (this) {
            producerOffset = (producerOffset + 1) % capacity;
            try {
                if (producerOffset == consumerOffset) {
                    wait();
                }
                queue[producerOffset] = message;
                notifyAll();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public KmqMessage poll() {
        synchronized (this) {
            try {
                while (consumerOffset == producerOffset) {
                    wait();
                }
                consumerOffset = (consumerOffset + 1) % capacity;
                KmqMessage message = queue[consumerOffset];
                notifyAll();
                return message;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @SneakyThrows
    public KmqMessage poll(long timeout) {
        synchronized (this) {
            try {
                while (consumerOffset == producerOffset) {
                    wait(timeout);
                    if (timeout > 0) {
                        break;
                    }
                }
                if (consumerOffset == producerOffset) {
                    return null;
                }
                consumerOffset = (consumerOffset + 1) % capacity;
                KmqMessage message = queue[consumerOffset];
                notifyAll();
                return message;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
