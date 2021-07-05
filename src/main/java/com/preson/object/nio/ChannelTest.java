package com.preson.object.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class ChannelTest {


    /**
     * <p>
     * 一、通道 （channel）用于源点与目标节点的连接 在Java NIO 中负责缓存区中的数据的传输 channel 本身不
     * 存储任何数据，因此需要配合缓存区进行传输
     * <p>
     * 二、通道的主要实现类
     * java.nio.channels.channel 接口
     * |--fileChannel  主要使用本地
     * |--SocketChannel        主要使用网络
     * |--ServerChannel        主要使用网络
     * |--DatagramChannel      主要使用网络
     * <p>
     * 三、获取通道
     * 1、Java针对支持通道的类提供了 getChannel（） 方法
     * 本地 IO
     * FileInputStream/FileOutputStream
     * RandomAccessFile
     * <p>
     * 网络：IO
     * Socket
     * ServerSocket
     * DatagramSocket
     * 2、在jdk 1.7 中的NIO 2 针对各个通道提供了静态方法 open（） 可以获取通道
     * 3、在jdk1.7 中的NIO 2 的File工具类的 newByteChannel 也可以获取通道
     * <p>
     * <p>
     * 四、通道之间的数据传输
     * transferFrom（）
     * transferTo()
     * <p>
     * 五、分散（Scatter）与聚集（Gather）
     * 分散读取（Scattering reads）：将通道中的数据分散到多个缓存区
     * 聚集写入（Gathering writer）：将多个缓存区中的数据聚集到通道当中
     * <p>
     * 六、字符集：charset
     * 编码：字符串 -> 字符数组
     * 解码：字节数组 -> 字符串
     *
     *
     * </p>
     */

    /**
     * 字符集
     */
    @Test
    public void chartDemo1() throws CharacterCodingException {
        Charset charset = Charset.forName("UTF-8");

        /** 获取编码器 */
        CharsetEncoder encoder = charset.newEncoder();

        /** 获取解码器 */
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer buffer = CharBuffer.allocate(1024);

        String str = "河南萱围堂医药信息科技有限公司";

        buffer.put(str);

        buffer.flip();

        /** 编码 */
        ByteBuffer encode = encoder.encode(buffer);

        for (int i = 0; i < 30; i++) {
            System.out.println(encode.get());
        }

        /**
         *  解码
         *
         *  TODO
         *  TODO    输出的编码器不全
         *
         *  */
        /* buffer.flip();*/
        CharBuffer decode = decoder.decode(encode);
        System.out.println(decode.toString());


    }


    @Test
    public void charsetDemo() {

        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();

        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

    }

    /**
     * 分散与聚集
     */
    @Test
    public void scatterDemo() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\filel.txt", "rw");

        /** 获取通道 */
        FileChannel fileChannel = randomAccessFile.getChannel();

        /** 分配指定大小的缓存区 */
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);

        /** 把多个分散的缓存区放到一个数据里 */
        ByteBuffer[] buf = {buffer, buffer1};

        /** Channel读取byteBuffer 数组里面的缓存区 */
        fileChannel.read(buf);

        for (ByteBuffer bytebuffer : buf) {
            bytebuffer.flip();
        }

        System.out.println(new String(buf[0].array(), 0, buf[0].limit()));
        System.out.println("---------------------------");
        System.out.println(new String(buf[1].array(), 0, buf[1].limit()));


        /** 聚集写入 */
        RandomAccessFile rw = new RandomAccessFile("D:\\filel3.txt", "rw");
        FileChannel rwChannel = rw.getChannel();
        rwChannel.write(buf);

    }

    /**
     * transferTo 通道之间的信息传输( 直接缓存区方式方式一 )
     */
    @Test
    public void transferDemo() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\filel.txt"), StandardOpenOption.READ);
        FileChannel outChannel1 = FileChannel.open(Paths.get("D:\\filel1.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        inChannel.transferTo(0, inChannel.size(), outChannel1);

        inChannel.close();
        outChannel1.close();

    }

    /**
     * 使用直接缓存区完成文件的复制（ 直接缓存区传输方式二 ）
     */
    @Test
    public void channelDemo() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\filel.txt"), StandardOpenOption.READ);
        FileChannel outChannel1 = FileChannel.open(Paths.get("D:\\filel1.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        /** 内存映射文件 */
        MappedByteBuffer in = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer out = outChannel1.map(FileChannel.MapMode.READ_WRITE, 0, outChannel1.size());


        /** 直接对缓存区进行数据的读写操作
         *  TODO
         *  TODO
         * */
        byte[] bytes = new byte[1024 * 1024];
        ByteBuffer buffer = in.get(bytes);
        ByteBuffer byteBuffer = out.put(bytes);

        /** 关闭通道*/
        inChannel.close();
        outChannel1.close();

    }


    @Test
    public void fileOutput() throws IOException {
        String str = "萱围堂";
        /** 创建系统输出到目标文件夹的流 */
        FileOutputStream stream = new FileOutputStream("D:\\filel.txt");
        /** 通过流获取通道 */
        FileChannel channel = stream.getChannel();
        /** 分配缓存区空间大小 */
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        /** 向缓冲区写入内容 */
        buffer.put(str.getBytes());
        /** 切换缓存区模式 */
        buffer.flip();
        /** 把缓存区写入到通道 */
        channel.write(buffer);
        /** 关闭通道 */
        channel.close();
        /** 关闭缓存区 */
        buffer.clear();

    }

    @Test
    public void fileInput() throws IOException {
        /** 创建一个文件目标文件夹 */
        File file = new File("D:\\filel.txt");
        /** 创建目标文件到系统之间的流 */
        FileInputStream fileInputStream = new FileInputStream(file);
        /** 通过流获取文件夹得通道 */
        FileChannel channel = fileInputStream.getChannel();
        /** 获取文件夹得大小 */
        int length = (int) file.length();
        /** 更具文件夹的大小分配指定空间大小的缓冲区 */
        ByteBuffer buffer = ByteBuffer.allocate(length);
        /** 读取缓存区的数据 */
        channel.read(buffer);
        /** 打印输出缓冲区的内容  */
        System.out.println(new String(buffer.array()));
        /** 关闭目标文件夹到系统之间的流 */
        fileInputStream.close();

    }


}
