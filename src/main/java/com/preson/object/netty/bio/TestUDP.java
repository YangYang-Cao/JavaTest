package com.preson.object.netty.bio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestUDP {

    /**
     * 发送端
     *
     * @throws Exception
     */
    @Test
    public void send() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            String next = scanner.next();
            buffer.put((new Date().toString() + "/n" + next).getBytes());
            buffer.flip();
            dc.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
            buffer.clear();

        }

        dc.close();
    }

    /**
     * @throws Exception
     */
    @Test
    public void server() throws Exception {

        DatagramChannel open = DatagramChannel.open();

        open.configureBlocking(false);

        open.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();

        open.register(selector, SelectionKey.OP_READ);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {

                SelectionKey next = iterator.next();

                if (next.isReadable()) {

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    open.receive(buffer);

                    buffer.flip();

                    System.out.println(new String(buffer.array(), 0, buffer.limit()));

                    buffer.clear();

                }

                iterator.remove();

            }

        }

    }


}
