package com.franc.app._1_basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final String IP = "127.0.0.1";   // IP
    private static final int PORT = 53001;          // PORT
    private static final String MESSAGE = "Hello Client!!"; // 클라이언트에 전송할 메시지

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream request = null;
        OutputStream response = null;

        try {
            // 1. 서버 소켓 생성
            serverSocket = new ServerSocket();

            // 2. 소켓 주소 할당
            serverSocket.bind(new InetSocketAddress(IP, PORT));
            System.out.println("[Socket] Binding!! - " + IP + ":" + PORT);

            // 3. 클라이언트의 연결요청 대기
            //   - 연결요청이 올 때까지 대기, 연결이 되면 Socket 객체를 반환한다.
            //   - 연결에 대한 부분은 OS를 통해 Java에서 자체적으로 처리한다.
            socket = serverSocket.accept();
            System.out.println("[Socket] Connect Success!!! - " + socket.getInetAddress());

            // 4. 연결된 클라이언트와 데이터 송수신
            request = socket.getInputStream();
            response = socket.getOutputStream();

            // -- 4-1. 클라이언트의 데이터 수신
            byte[] data = new byte[16];
            int dataIndex = request.read(data);
            String requestData = new String(data, 0, dataIndex);

            System.out.println("[Socket] Client Message - " + requestData);

            // -- 4-2. 클라이언트에 데이터 송신
            response.write(MESSAGE.getBytes());
            response.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // 5. 통신 종료
            try {
                if (request != null) request.close();
                if (response != null) response.close();
                if (socket != null && socket.isClosed()) socket.close();
                if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
