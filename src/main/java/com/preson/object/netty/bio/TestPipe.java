package com.preson.object.netty.bio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;


/**
 * @author admin
 */
public class TestPipe {

    /**
     * @throws Exception
     */
    @Test
    public void name() throws Exception {
        // 1、获取管道

        Pipe open = Pipe.open();

        // 2、将缓存区中的数据写入到管道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        SinkChannel sink = open.sink();

        buffer.put("通过单向管道发送数据".getBytes());

        buffer.flip();

        sink.write(buffer);

        // 3 、读取缓存区的数据

        SourceChannel source = open.source();
        buffer.flip();
        int lan = source.read(buffer);

        System.out.println(new String(buffer.array(), 0, lan));

        source.close();

        sink.close();
    }

}
