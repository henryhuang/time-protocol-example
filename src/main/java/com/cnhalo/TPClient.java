package com.cnhalo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Time protocol client.
 *
 * Created by Henry Huang on 2020/1/18.
 */
public class TPClient {

    private static final int PORT = 37;
    private static final long OFF_SET = 2208988800L;

    public static void main(String[] args) {
        Long time = new TPClient("localhost").seekTime();
        if (time != null) {
            System.out.println(new Date((time - OFF_SET) * 1000L));
        }
    }

    private String host;

    public TPClient(String host) {
        this.host = host;
    }

    public Long seekTime() {

        try (Socket socket = new Socket(this.host, PORT);
            DataInputStream is = new DataInputStream(socket.getInputStream())) {
            int length = is.readInt();
            byte[] bytes = new byte[length];
            is.readFully(bytes);
            return Long.valueOf(new String(bytes));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
