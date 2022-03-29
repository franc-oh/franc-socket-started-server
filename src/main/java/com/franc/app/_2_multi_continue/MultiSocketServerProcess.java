package com.franc.app._2_multi_continue;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MultiSocketServerProcess extends Thread {
    Socket socket = null;
    InputStream request = null;
    OutputStream response = null;

    public MultiSocketServerProcess(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            request = socket.getInputStream();
            response = socket.getOutputStream();

            // 1. 연결된 클라이언트와의 데이터 송.수신
            //    -  입력 데이터를 송신
            BufferedReader chat = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // 1-1. 클라이언트의 데이터 수신
                byte[] data = new byte[16];
                int dataIndex = request.read(data);
                if (0 >= dataIndex) break;
                String requestData = new String(data, 0, dataIndex);
                consolePrint(requestData);

                // 1-2. 클라이언트에 데이터 송신
                String chatMessage = chat.readLine();
                if ("".equals(chatMessage)) break;
                response.write(chatMessage.getBytes(StandardCharsets.UTF_8));
                response.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 2. 통신 종료
            try {
                if (request != null) request.close();
                if (response != null) response.close();
                if (socket != null && !socket.isClosed()) socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consolePrint(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }


}
