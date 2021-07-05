package com.preson.object.netty.bio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SocketBlockNio {


    /**
     * 客户端
     *
     * @throws IOException
     */
    @Test
    public void clien() throws IOException {

        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel open2 = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int i = 1;
        while (open2.read(buffer) != -i) {
            buffer.flip();
            open.write(buffer);
            buffer.clear();

        }

        open.shutdownOutput();

        // 接受服务器的反馈
        int len = 0;
        while ((len = open.read(buffer)) != -1) {

            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        open.close();
        open2.close();
    }

    /**
     * @throws Exception 服务端
     */
    @Test
    public void server() throws Exception {
        ServerSocketChannel open = ServerSocketChannel.open();

        FileChannel open2 = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        open.bind(new InetSocketAddress(9898));

        SocketChannel socker = open.accept();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (socker.read(buffer) != -1) {

            buffer.flip();

            open2.write(buffer);

            buffer.clear();
        }

        // 发送反馈给客户端
        buffer.put("服务端接受客户端成功".getBytes());

        buffer.flip();

        socker.write(buffer);

        open2.close();
        open.close();
        socker.close();

    }
}
