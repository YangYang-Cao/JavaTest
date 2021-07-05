package com.preson.object.netty.bio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;

public class TestNioBlocking {


    /**
     * 客户端
     *
     * @throws Exception
     */
    @Test
    public void client() throws Exception {
        // 1、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 2、切换到非阻塞模式
        SelectableChannel configureBlocking = sChannel.configureBlocking(false);

        // 3、分配指定大侠的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4、发送数据给服务端
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        sChannel.write(buffer);

        buffer.clear();
        sChannel.close();

    }

    /**
     * 服务端
     *
     * @throws Exception
     */
    @Test
    public void server() throws Exception {
        // 1、获取通达
        ServerSocketChannel sChannel = ServerSocketChannel.open();

        // 2、切换到非阻塞模式
        sChannel.configureBlocking(false);

        // 3、绑定连接
        sChannel.bind(new InetSocketAddress(9898));

        // 4、获取一个选择器
        Selector selector = Selector.open();

        // 5、将通道注册到选址器上 并且指定监听事件
        sChannel.register(selector, SelectionKey.OP_ACCEPT);

        SocketChannel ssChannel = null;

        // 6、轮询式获取选择器上的已经”准备就绪“的的事件
        while (selector.select() > 0) {
            // 这个方法包括所有的选择器的注册事件
            // 7、获取当前选择器中的所以注册的”选择键“（已经就绪的监听事件）
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {

                // 8、获取准备”就绪“的事件
                SelectionKey sk = iterator.next();

                // 9、判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {

                    // 10 、若‘接受就绪’ 获取客户端连接
                    ssChannel = sChannel.accept();

                    // 11 、切换非阻塞模式
                    ssChannel.configureBlocking(false);

                    // 12、将通道注册到选择器上
                    ssChannel.register(selector, SelectionKey.OP_ACCEPT);

                    // 判断是否都就绪
                } else if (sk.isReadable()) {

                    // 13 、获取当前选择器上‘读就绪’状态的通道
                    SocketChannel SocekerChannel = (SocketChannel) sk.channel();

                    // 14、读取数据就绪

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = ssChannel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();

                    }
                    // 15 、取消选择键
                    iterator.remove();
                }
            }
        }
    }

}
