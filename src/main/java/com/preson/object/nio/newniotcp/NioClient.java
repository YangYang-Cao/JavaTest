package com.preson.object.nio.newniotcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws IOException {

        /** 创建客户端通道 */
        SocketChannel socketChannel = SocketChannel.open();

        /** 设置客户端通道为非阻塞式的 */
        socketChannel.configureBlocking(false);

        /** 提供服务端地址跟端口号 */
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);

        /** 如果连接了该方法返回 true 没有连接上着返回 false */
        boolean connect = socketChannel.connect(socketAddress);

        /** 代表如果没有连接上 */
        if (!connect) {

            /** 如果没有连接上会继续连接 当连接成功时返回为ture 如果连接失败返回 false */
            /*boolean finishConnect = socketChannel.finishConnect();*/

            while (!socketChannel.finishConnect()) {
                System.out.println("客户端连接失败可以继续做其他的事情");
            }
        }

        String str = "hello";

        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        /** 将数据写入到缓冲区当中 */
        socketChannel.write(byteBuffer);

        /** 方法的作用是阻碍Main线程继续执行 */
        System.in.read();

    }


}
