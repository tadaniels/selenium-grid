package com.thoughtworks.selenium.grid;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketUtils {

    public static int getFreePort() throws IOException {
        ServerSocket socket = null;
        final int port;

        try {
            socket = new ServerSocket(0);
            port = socket.getLocalPort();
        } finally {
            if (null != socket) {
                socket.close();
            }
        }

        return port;
    }

}
