package com.example.server;

import com.example.server.utils.FileUtils;
import com.example.server.utils.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServiceThread extends Thread {
    private static final int kBufferSize = 1024 * 1024;
    private final Socket socket;
    private final byte[] buffer;

    public ServiceThread(final Socket socket) {
        this.socket = socket;
        this.buffer = new byte[kBufferSize];
    }

    @Override
    public void run() {
        try {
            Log.log("收到新的请求：" + socket.getInetAddress().getHostAddress());
            //构造输入输出流
            final DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            final DataInputStream din = new DataInputStream(socket.getInputStream());
            String str = "SocketReady";
            dout.writeUTF(str);
            str = din.readUTF();
            if (!str.isEmpty()) {
                Log.log("receive: " + str);
            }
            str = "AgentInfoReady";
            dout.writeUTF(str);
            while (true) {
                str = din.readUTF();
                if (!str.isEmpty()) {
                    Log.log("receive: " + str);
                } else {
                    Log.log("received nothing, break");
                    break;
                }
                final String fileName = str;
                boolean firstWrite = true;

                final long fileLength = din.readLong();
                long currentReceivedLength = 0;
                while (currentReceivedLength < fileLength) {
                    final int readSize = din.read(buffer);
                    if (readSize > 0) {
                        currentReceivedLength += readSize;
                        FileUtils.writeToFile(fileName, buffer, 0, readSize, !firstWrite);
                        firstWrite = false;
                        final double percent = currentReceivedLength * 100.0 / fileLength;
                        str = "received: " + percent + "%(" + currentReceivedLength + "/" + fileLength + ")";
                        Log.log(str);
                    } else {
                        Log.log("error: stream reach to end before complete");
                        sleep(10);
                    }
                }
                str = "FileReceived";
                dout.writeUTF(str);
            }
            //关闭输出流
            dout.close();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭socket
                socket.close();
            } catch (final IOException e) {}
            Log.log("连接已结束");
        }
    }
}
