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

public class BlockingNioTest {


    /**
     * <p>
     * 一、使用NIO 完成网络通信的三个核心
     * <p>
     * 1、通道（channel）：负责连接
     * <p>
     * Java.nio.channels.channel 接口
     * |-- SelectableChannel
     * |--SocketChannel
     * |--ServerSocketChannel
     * |--DatagramChannel
     * <p>
     * |--Pipe.SinkChannel
     * |--Pipe.sourceChannel
     *2、缓存区（buffer）：负责数据的存储
     *
     * 3、选择器（Selector） ： 是SelectChannel的多路复用器。用于监控SelectableChannel的 IO 状况
     *
     *
     *
     *
     * </p>
     */

    /**
     * 客户端
     */
    @Test
    public void blockingClient() throws IOException {
        /** 获取通道 */
        SocketChannel socketChannel = SocketChannel.open();

        /** 获取本地文件 */
        FileChannel open = FileChannel.open(Paths.get("D:\\filel.txt"), StandardOpenOption.READ);

        /** 绑定地址跟端口号 */
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);

        /** 连接绑定的地址 */
        socketChannel.connect(socketAddress);

        /** 创建缓存区 */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        /** 读取本地文件并发送到服务端去 */


        while (open.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        open.close();
        socketChannel.close();


    }

    /**
     * 服务端
     */
    @Test
    public void blockingServer() throws IOException {
        /** 获取通道 */
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel fileChannel = FileChannel.open(Paths.get("D:\\filel1.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        /** 设置绑定端口号 */
        InetSocketAddress socketAddress = new InetSocketAddress(8888);

        /** 绑定端口号 */
        serverSocketChannel.bind(socketAddress);

        /** 获取客户端连接通道 */
        SocketChannel socketChannel = serverSocketChannel.accept();

        /** 创建一个指定大小的缓存区 */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        /** 读取客户端数据并保持到本地 */
        while (socketChannel.read(byteBuffer) != -1) {

            byteBuffer.flip();
            fileChannel.write(byteBuffer);

            byteBuffer.clear();


        }

        /** 官博通道 */
        fileChannel.close();
        serverSocketChannel.close();


    }


}
