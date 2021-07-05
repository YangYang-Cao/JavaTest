package com.preson.object.netty.bio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Text {

    @Test
    public void name() throws Exception {

        // 使用直接内存缓冲区完成文件的复制
        FileChannel open = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        // 表示如果文件不存在就创建一个文件，CREATE_NEW 如果文件存在也创建一个文件
        FileChannel open2 = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW);

        // 1、第一个参数表示什么模式 2、第二个参数表示从什么位置开始 3、第三个参数表示映射文件的大小
        // 内存映射文件
        MappedByteBuffer map = open.map(MapMode.READ_ONLY, 0, open.size());
        MappedByteBuffer map2 = open2.map(MapMode.READ_WRITE, 0, open.size());

        // 直接对缓存区进行数据的读写操作
        byte[] aa = null;

    }

    // 通道直接的数据传送
    @Test
    public void name1() throws Throwable {
        // 使用直接内存缓冲区完成文件的复制
        FileChannel open = FileChannel.open(Paths.get("D:\\1.txt"), StandardOpenOption.READ);
        // 表示如果文件不存在就创建一个文件，CREATE_NEW 如果文件存在也创建一个文件
        FileChannel open2 = FileChannel.open(Paths.get("D:\\3.txt"), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW);

        // 1、第一个参数表示从那里开始 2、参数二表示从那里开始 3、参数三表示到哪里去
        // 2、这个方法表示写数据
        // open.transferTo(0, open.size(), open2);

        open2.transferFrom(open, 0, open2.size());
        open.close();

        open2.close();

    }

    @Test
    public void name2() throws Exception {
        // 分散（scatter）与聚集 （Gather）
        // 分散读取（ scarrering reads）: 将通道中的数据分散到多个缓存区中
        // 聚集写入 （gathering writes ） ： 将多个缓存区的数据聚集到通道中

        // 1、分散写入
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "ra");

        // 2、获取通道
        FileChannel channel = randomAccessFile.getChannel();

        // 3、 分配指定大小的缓存区
        ByteBuffer allocate2 = ByteBuffer.allocate(1024);
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        ByteBuffer[] buffers = {allocate, allocate2};

        channel.read(buffers);

        for (ByteBuffer byteBuffer : buffers) {
            byteBuffer.flip();
        }

        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println("--------------------------------------");
        System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));

    }

    @Test
    public void main() {

        String str = "string";
        // 分配一个指定大小的缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 利用 put () 方法存入数据到缓存区
        System.out.println("------------put()-------------------------------");
        byteBuffer.put(str.getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 利用 flip（） 方法切换到读数据模式
        System.out.println("------------flip()-------------------------------");
        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("------------get()-------------------------------");

        // 利用get() 读取缓存区中的数据
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("---------rewind()----------------------------------");
        // rewind() 可重复读取数据
        byteBuffer.rewind();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println("---------clear()----------------------------------");
        // 清空缓存区 clear : 清空缓存数据区,但是缓存区的数据还在，但是处于‘被遗忘‘ 状态：
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

    }

    @Test
    public void main1() {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];

        buffer.get(bytes, 0, 2);

        System.out.println(new String(bytes, 0, 2));
        System.out.println(buffer.position());

        // mark（） ；标记
        buffer.mark();
        buffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 3));
        System.out.println(buffer.position());

        // rest() : 恢复到mark 的位置
        buffer.reset();
        System.out.println(buffer.position());

        // 判断缓存区是否还有剩余的输入
        if (buffer.hasRemaining()) {

            // 获取缓存区中可以操作的数量
            System.out.println(buffer.remaining());
        }
    }

    @Test
    public void main2() {
        // 分配直接缓存区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        // 判断是否为直接缓存区
        System.out.println(buf.isDirect());

    }

    @Test
    public void main3() throws IOException {
        // 利用通道完成文件的复制（非直接缓存区）

        // 利用通道完成文件的复制
        FileInputStream stream = new FileInputStream("1.jpg");
        FileOutputStream outputStream = new FileOutputStream("2.jpg");

        //获取通道
        FileChannel channel = stream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        // 分配指定大小的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将通道中的数据存入缓存区中
        while (channel.read(buffer) != -1) {
            /* 切换到读取数据模式 */
            Buffer flip = buffer.flip();

            outputStream.write(null);

            /* 将缓存区数据写入通道 */
            buffer.clear();
        }

        outputStream.close();
        stream.close();
    }

}
