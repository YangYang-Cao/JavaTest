package com.preson.object.nio.blockingnio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockingNioDemoTest {


    /**
     * 客户端
     */
    @Test
    public void client() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);

        SocketChannel socketChannel = SocketChannel.open(socketAddress);

        FileChannel fileChannel = FileChannel.open(Paths.get("D:\\filel.txt"), StandardOpenOption.READ, StandardOpenOption.CREATE);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);

            byteBuffer.clear();

        }


        socketChannel.shutdownOutput();

        /** 接受服务段反馈 */
        int leng = 0;
        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, leng));
            byteBuffer.clear();
        }

        socketChannel.shutdownOutput();

        fileChannel.close();
        socketChannel.close();


    }

    /**
     * 服务端
     */
    @Test
    public void Server() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress socketAddress = new InetSocketAddress(8080);

        FileChannel fileChannel = FileChannel.open(Paths.get("D:\\filel1.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        serverSocketChannel.bind(socketAddress);

        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();

        }

        byteBuffer.put("服务端接收数据成功".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);


        fileChannel.close();
        serverSocketChannel.close();

    }

}
