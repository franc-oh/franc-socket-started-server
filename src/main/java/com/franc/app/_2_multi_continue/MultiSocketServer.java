package com.franc.app._2_multi_continue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiSocketServer {
    private static final String IP = "127.0.0.1";   // IP
    private static final int PORT = 53001;          // PORT
    private static final String MESSAGE = "Hello Client!!"; // 클라이언트에 전송할 메시지

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // 1. 서버 소켓 생성
            serverSocket = new ServerSocket();

            // 2. 소켓 주소 할당
            serverSocket.bind(new InetSocketAddress(IP, PORT));
            System.out.println("[Socket] Binding!! - " + IP + ":" + PORT);

            // 3. 클라이언트의 연결요청을 대기 => 반복
            //   - 연결요청이 올 때까지 대기, 연결이 되면 Socket 객체를 반환한다.
            //   - 연결에 대한 데이터 처리는 쓰레드를 생성해서 별도로 처리, 메인 메소드는 다시 연결요청을 대기하는 것을 반복.
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[Socket] Connect Success!!! - " + socket.getInetAddress());
                new MultiSocketServerProcess(socket).start(); // 연결에 대한 데이터 처리
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // 4. 통신 종료
            try {
                if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
