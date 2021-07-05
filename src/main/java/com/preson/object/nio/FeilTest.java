package com.preson.object.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

public class FeilTest {


    /**
     * 一、缓存区（buffer）：在 Java NIO 中负责数据的存储。缓存区就是数组 用于存储不同数据类型
     * <p>
     * 根据数据类型不同（Boolean 除外） 提供了不同的缓存区
     * 1、ByteBuffer
     * 2、CharBuffer
     * 3、ShortBuffer
     * 4、IntBuffer
     * 5、LongBuffer
     * 6、FloatBuffer
     * 7、DoubleBuffer
     * <p>
     * 上述缓存区的管理方式几乎一致，通过allocate（） 获取缓存区
     * <p>
     * 二、缓存区存储数据的两个核心方法
     * put（）：存入数据到缓冲区
     * get（）：获取缓存区数据
     * rewind()： 可以重复读取数据
     * clear（）： 情况缓存区内容
     * mark ():标记，表示记录当前position 的位置可以通过 reset（） 恢复到 mark（）标记位置
     * hasRemaining() : 判断缓存是否还有可以操作的数据
     * remaining(): 表示缓存区还有多少可以操作的内容
     * 0 <= mark <= position <= limit <= capacity
     * <p>
     * 四、缓存区中的四个核心属性
     * capacity：容量，表述缓存区中最大存区数据的容量，一旦声明不能改变
     * limit：界限，表示缓存区可以操作的数据的大小（意味着limit 之后的数据不能进行操作）
     * position：位置，表示缓存区中正在操作的数据位置
     * flip : 切换缓存区模式
     * <p>
     * 五 、直接缓存区
     * 非直接缓存区：通过allocate（） 方法分配缓存区，将缓存区建立在JVM的缓存区上
     * 非直接缓存区：通过allocateDirect（） 方法分配直接缓存区，将缓存区建立在物理内存中，可以提高效率
     *
     * </p>
     */

    public void allocate() {
        /** 创建直接缓存区 */
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        /** isDirect() 判断这个缓存区是否为直接缓冲区 如果为直接缓存区返回结果为 true 反正依然 */
        System.out.println(byteBuffer.isDirect());

    }

    @Test
    public void testBuffer() {

        /** 分配指定大小的缓存区 */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("---------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        /** 向缓存区存入数据 */
        String str = "hello";
        byteBuffer.put(str.getBytes());

        System.out.println("----------put()-----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        byteBuffer.flip();
        System.out.println("--------put()-------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("--------get()-------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        /** rewind() 可以重复读取数据 */
        byteBuffer.rewind();
        System.out.println("--------rewind()-------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        /** clear() : 清空缓存区，但是清空缓存区中的数据依然存在 但是处于被遗忘的状态*/
        byteBuffer.clear();
        System.out.println("--------clear() -------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

    }

    @Test
    public void mark() {
        String str = "hello";

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        byte[] bytes = new byte[byteBuffer.limit()];

        byteBuffer.get(bytes, 0, 2);

        System.out.println(new String(bytes, 0, 2));

        System.out.println(byteBuffer.position());

        byteBuffer.mark();

        byteBuffer.get(bytes, 2, 2);

        System.out.println(new String(bytes, 2, 3));
        System.out.println(byteBuffer.position());

        byteBuffer.reset();
        System.out.println(byteBuffer.position());


        System.out.println("-----------------------");
        /** 判断缓存区还有没有可以操作的数据 */
        if (byteBuffer.hasRemaining()) {
            /** 如果有缓存区还有可以操作的内容打印还有多少的个内容 */
            System.out.println(byteBuffer.remaining());
        }
    }


}
