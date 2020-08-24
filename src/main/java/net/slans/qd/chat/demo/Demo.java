package net.slans.qd.chat.demo;

import net.slans.qd.chat.remoting.protocol.RemotingCommand;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Demo {

    public static void main(String[] args) {
        RemotingCommand remotingCommand = new RemotingCommand();
        remotingCommand.setVersion(1);
        remotingCommand.setBody("success".getBytes(Charset.forName("UTF-8")));

        ByteBuffer bb = remotingCommand.encode();
        System.out.println("encode: " + bb);

        bb.getInt();
        byte[] tmp = new byte[bb.limit() - 4];
        bb.get(tmp);
        byte[] body = RemotingCommand.decode(tmp);
        System.out.println("decode body: " + new String(body));
    }

}
