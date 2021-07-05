package com.preson.object.nio.niotcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerNio {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.bind(new InetSocketAddress(8888));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器在等待连接");
                continue;

            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();


                if (key.isAcceptable()) {

                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                if (key.isReadable()) {

                    SocketChannel channel = (SocketChannel) key.channel();

                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();

                    channel.read(byteBuffer);

                    System.out.println(new String(byteBuffer.array()));


                }

                iterator.remove();

            }


        }

    }
}
