package com.example.server;

import com.example.server.utils.Log;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMain {
    static int serverPort;
    static ServerSocket ss;

    public static void main(final String[] args) {
        serverPort = 8008;
        try {
            ss = new ServerSocket(serverPort);
            Log.log("开启端口：" + serverPort);
            while (true) {
                final Socket socket = ss.accept();
                new ServiceThread(socket).start();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            Log.log("server terminated.");
        }
    }
}
