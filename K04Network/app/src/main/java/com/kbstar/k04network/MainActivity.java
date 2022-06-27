package com.kbstar.k04network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btnSend, btnStart;
    private EditText clientInput;
    private TextView clientDisplay, serverDisplay;

    public static final String TAG = "MY_NETWORK";  // 로그 남길 때 태그가 있는 것이 현명함! (찾기 쉬움)
    Handler handler = new Handler();

    private int serverPort = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);
        btnStart = findViewById(R.id.btnStart);
        clientInput = findViewById(R.id.clientInput);
        clientDisplay = findViewById(R.id.clientDisplay);
        serverDisplay = findViewById(R.id.serverDisplay);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Server Start
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sendMsg = clientInput.getText().toString();

                // Client Send
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendData(sendMsg);
                    }
                }).start();
            }
        });
    }

    private void sendData(String data) {

        try {
            Socket serverSocket = new Socket("localhost", serverPort);  // serverSocket
            printClient("Connected to Server");

            ObjectOutputStream outStream = new ObjectOutputStream(
                    serverSocket.getOutputStream()
            );
            outStream.writeObject(data);
            outStream.flush();
            printClient("SEND : " + data);

            ObjectInputStream inStream = new ObjectInputStream(
                    serverSocket.getInputStream()
            );
            Object obj = inStream.readObject();
            printClient("RCV : " + obj);

            serverSocket.close();

        } catch (Exception e) {
            Log.d(TAG, "Client Exception : " + e.getMessage());
        }
    }

    private void startServer() {

        try {
            ServerSocket server = new ServerSocket(serverPort);
            printServer("Server Start : #" + serverPort);

            while(true) {
                printServer("Listening... : #" + serverPort);
                Socket clientSocket = server.accept();  // clientSocket : client 정보 다 들어있음

                InetAddress clientAddress = clientSocket.getLocalAddress();
                int clientPort = clientSocket.getPort();
                printServer("Accepted... : " + clientAddress + ":" + clientPort);

                ObjectInputStream inStream = new ObjectInputStream(
                        clientSocket.getInputStream()
                );
                Object obj = inStream.readObject();
                printServer("RCV : " + obj);

                ObjectOutputStream outStream = new ObjectOutputStream(
                        clientSocket.getOutputStream()
                );
                outStream.writeObject(obj + " from Server!");   // echo
                outStream.flush();

                printServer("ECHO SENDED");
                clientSocket.close();
                printServer("Socket Closed..");
            }
        } catch (Exception e) {
            Log.d(TAG, "startServer Exception : " + e.getMessage());
        }
    }

    public void printServer(String log) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                serverDisplay.append(log + "\n");
            }
        });
    }

    public void printClient(String log) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                clientDisplay.append(log + "\n");
            }
        });
    }
}