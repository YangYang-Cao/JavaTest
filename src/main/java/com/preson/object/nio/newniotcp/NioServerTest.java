package com.preson.object.nio.newniotcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerTest {


    public static void main(String[] args) throws IOException {

        /** 创建一个ServerSocketChannel 服务端 */
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        /** 创建一个选择器 */
        Selector selector = Selector.open();
        /** 服务器绑定监听端口 */
        serverSocketChannel.bind(new InetSocketAddress(8080));
        /** 设置服务器模式 */
        serverSocketChannel.configureBlocking(false);

        /** 将服务器注册到选择器上 在向选择器表面想要接受的事件   */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        /** 循环等待客户连接 */
        while (true) {

            /** 在这里等待连接一秒钟 如果有客户端连接返回  1 如果没有客户端连接着返回  0  */
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了一秒钟");
                continue;
            }
            /** 把所有连接的客户端存放到 SelectorKeys集合里面 */
            Set<SelectionKey> keySet = selector.selectedKeys();

            /** 把连接的客户端遍历一遍遍历出来 */
            Iterator<SelectionKey> iterator = keySet.iterator();

            while (iterator.hasNext()){
                /** 得到具体每一个客户端 */
                SelectionKey Key = iterator.next();

                /**  isAcceptable（） 这个方法代表是不是新的连接如果是这返回ture 不是就返回false  */
                if (Key.isAcceptable()){

                    /**  接受新的客户端连接 */
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    /** 将通道设置为非阻塞 */
                    socketChannel.configureBlocking(false);

                    /** 将服务端的通道注册到选择器上并分配空间 */
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));


                }
                /** 如果发生读事件说明这个客户端已经连接成功了 */
                if (Key.isReadable()){
                    /** 通过客户端反向获取通道 */
                    SocketChannel channel = (SocketChannel)Key.channel();
                    /** 通过客户端获取缓存区 */
                    ByteBuffer byteBuffer = (ByteBuffer)Key.attachment();

                    /** 通道读取缓存区内容 */
                    int read = channel.read(byteBuffer);

                    System.out.println(read);

                    /** 输出打印出缓存区内容 */
                    System.out.println(new String(byteBuffer.array()));

                }

                /** 使用完毕删除连接 */
                iterator.remove();
            }


        }


    }

}
