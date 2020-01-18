package com.cnhalo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Time protocol server.
 *
 * Created by Henry Huang on 2020/1/18.
 */
public class TPServer {

    private static final int PORT = 37;
    private static final long OFF_SET = 2208988800L;

    public static void main(String[] args) {

        new TPServer().start();

    }

    public void start() {
        System.out.println("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket connection = serverSocket.accept();
                new TPHandlerThread(connection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class TPHandlerThread implements Runnable {

        private Socket connection;

        private TPHandlerThread(Socket connection) {
            this.connection = connection;
            new Thread(this).start();
        }

        public void run() {

            System.out.println("Receive seek time request!");

            try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                byte[] timeBytes = String.valueOf(getTime()).getBytes();
                os.writeInt(timeBytes.length);
                os.write(timeBytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static long getTime() {
        return System.currentTimeMillis() / 1000L + OFF_SET;
    }

}
