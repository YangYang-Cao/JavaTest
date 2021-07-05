package com.preson.object.nio.pipeline;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipelineDemoTest {



        @Test
        public void pipeDemo () throws IOException {
        Pipe pipe = Pipe.open();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sink = pipe.sink();


        byteBuffer.put("通过单向管道发送数据".getBytes());
        byteBuffer.flip();
        sink.write(byteBuffer);

        Pipe.SourceChannel sourceChannel = pipe.source();

        sourceChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        sourceChannel.close();
        sink.close();

    }


    }
