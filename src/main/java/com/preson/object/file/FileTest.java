package com.preson.object.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTest {


    /*
     *缓存区的管理方式几乎一致，通过allocate（）获取缓冲区
     *
     * put() 存入数据到缓存区中
     * get() 获取缓存区中的数据
     *
     * 缓存区中的四个核心属性
     * capacity ： 容量，表示缓冲区中最大存储数据的容量，一旦声明不能改变
     * limit ：界限，表示缓存区中可以操作的大小。（limit 后数据不能进行读写）
     * position ：位置，表示缓存区中正在操作数据的位置
     *
     * mark : 标记，表示记录当前位置position 的位置，可以通过reset() 恢复到 mark记录的位置
     *
     * 直接缓存区与非直接缓存区
     * 非直接缓存区：通过allocate() 方法分配缓存区，将缓存区建立在JVM内存中
     * 直接缓存区：通过allocateDirect（）方法分配直接缓存区，将缓存区建立在物理内存中，可以提高效率
     *
     *
     * */

    public static void main(/*String[] args*/) {

        String str = "string";
        /*分配一个指定大小的缓存区*/
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());


        /*利用 put () 方法存入数据到缓存区*/
        System.out.println("------------put()-------------------------------");
        byteBuffer.put(str.getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());


        /*利用 flip（） 方法切换到读数据模式 */
        System.out.println("------------flip()-------------------------------");
        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("------------get()-------------------------------");



        /*利用get() 读取缓存区中的数据*/
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());


        System.out.println(new String(bytes, 0, bytes.length));


        System.out.println("---------rewind()----------------------------------");
        /*rewind() 可重复读取数据*/
        byteBuffer.rewind();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println("---------clear()----------------------------------");
        /*清空缓存区 clear : 清空缓存数据区,但是缓存区的数据还在，但是处于‘被遗忘‘ 状态：*/
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

    }


    public static void main1(/*String[] args*/) {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];

        buffer.get(bytes, 0, 2);

        System.out.println(new String(bytes, 0, 2));
        System.out.println(buffer.position());


        /*mark（） ；标记 */
        buffer.mark();
        buffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 3));
        System.out.println(buffer.position());


        /*rest() : 恢复到mark 的位置*/
        buffer.reset();
        System.out.println(buffer.position());


        /*判断缓存区是否还有剩余的输入*/
        if (buffer.hasRemaining()) {

            /*获取缓存区中可以操作的数量*/
            System.out.println(buffer.remaining());
        }
    }


    public static void main2(/*String[] args*/) {
        /*分配直接缓存区*/
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        /*判断是否为直接缓存区*/
        System.out.println(buf.isDirect());

    }


    public static void main(String[] args) throws IOException {
        /*利用通道完成文件的复制（非直接缓存区）*/

        /*利用通道完成文件的复制*/
        FileInputStream stream = new FileInputStream("1.jpg");
        FileOutputStream outputStream = new FileOutputStream("2.jpg");

        /*获取通道*/
        FileChannel channel = stream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        /*分配指定大小的缓存区*/
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        /*将通道中的数据存入缓存区中*/
        while (channel.read(buffer) != -1) {
            /*切换到读取数据模式*/
            Buffer flip = buffer.flip();

//            outputStream.write(buffer);

            /*将缓存区数据写入通道*/

            buffer.clear();
        }

        outputStream.close();
        stream.close();
    }

}
