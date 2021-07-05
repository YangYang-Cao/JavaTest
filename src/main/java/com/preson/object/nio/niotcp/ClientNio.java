package com.preson.object.nio.niotcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientNio {


    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);


        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);

        boolean connect = socketChannel.connect(socketAddress);

        if (!connect) {

            boolean finishConnect = true;

            finishConnect = socketChannel.finishConnect();

            while (!finishConnect) {

                finishConnect = socketChannel.finishConnect();

                System.out.println("客户端连接失败可以做其他的事情");

            }
        }

        String str = "hello";

        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(buffer);


        System.in.read();

    }


}
