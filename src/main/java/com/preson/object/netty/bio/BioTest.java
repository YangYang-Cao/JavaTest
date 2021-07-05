package com.preson.object.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioTest {

    public static void main(String[] args) throws IOException {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");
        while (true) {

            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到客户端了");
            //就创建一个线程，与之通信
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    handler(socket);
                }
            });

        }
    }

    //调用一个handler方法，和客户端
    public static void handler(Socket socket) {


        try {
            System.out.println("线程信息  ID  " + Thread.currentThread().getId() + "  线程名字  " + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket 获取输入
            InputStream inputStream = socket.getInputStream();

            //循环读取客户端发送的数据
            while (true) {
                System.out.println("线程信息  ID  " + Thread.currentThread().getId() + "  线程名字  " + Thread.currentThread().getName());

                int read = inputStream.read(bytes);
                //判断是否读取完数据流条件是当 read等于 -1 时表示已经读取完数据
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    System.out.println("读取客户端完毕");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            System.out.println("关闭和client的连接");

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


}
