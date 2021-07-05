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

public class SocektTextNio {

    /*
     * 缓存区的管理方式几乎一致，通过allocate（）获取缓冲区
     *
     * put() 存入数据到缓存区中 get() 获取缓存区中的数据
     *
     * 缓存区中的四个核心属性 capacity ： 容量，表示缓冲区中最大存储数据的容量，一旦声明不能改变 limit
     * ：界限，表示缓存区中可以操作的大小。（limit 后数据不能进行读写） position ：位置，表示缓存区中正在操作数据的位置
     *
     * mark : 标记，表示记录当前位置position 的位置，可以通过reset() 恢复到 mark记录的位置
     *
     * 直接缓存区与非直接缓存区 非直接缓存区：通过allocate() 方法分配缓存区，将缓存区建立在JVM内存中
     * 直接缓存区：通过allocateDirect（）方法分配直接缓存区，将缓存区建立在物理内存中，可以提高效率
     *
     *
     * 一、使用NIO 完成网络通信的三个核心
     *
     * 1、通道（channel）：负责连接 Java.nio.channels.channel 接口 |--selectableChannel
     * |--SoceketChannel |--ServerSocketChannel |--DatagramChannel
     *
     * |--pipe.SinkChannel |--pige.SourceChannel
     *
     * 2、缓存区（buffer）: 负责数据连接
     *
     *
     * 3、选择器（selector）： 是selectableChannel的多略服用器 用于监控SelectbaleChannel的IO状况
     *
     *
     */


    /**
     * 客户端
     *
     * @throws IOException
     */
    @Test
    public void client() throws IOException {
        // 1、获取通道
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        // 2、分配指定大小的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 3、读取本地的文件，并发送到服务端
        while (inChannel.read(buffer) != -1) {
            // 切换到读取数据模式
            buffer.flip();
            open.write(buffer);
            buffer.clear();

        }

        // 4、关闭通达
        inChannel.close();
        open.close();
    }

    /**
     * 服务端
     *
     * @throws Exception
     */
    @Test
    public void server() throws Exception {
        // 1、获取通道
        ServerSocketChannel open = ServerSocketChannel.open();

        // 第一个参数表示保存的文件，第二个参数表示写入文件 ，第三个参数表示如果没有文件就需要创建一个文件
        FileChannel open2 = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW);

        // 2、绑定连接
        open.bind(new InetSocketAddress(9898));

        // 3、获取客户端连接
        SocketChannel channel = open.accept();

        // 4、分配一个指定大小的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 5、接受客户端的数据并保存到本地

        while (channel.read(buffer) != -1) {
            buffer.flip();
            open2.write(buffer);
            buffer.clear();

        }

        // 6、关闭对于的通道
        open.close();
        open2.close();
    }


}
