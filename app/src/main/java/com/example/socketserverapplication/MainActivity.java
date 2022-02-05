package com.example.socketserverapplication;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    public static int[][] a1 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public static int[][] a2 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public static int[][] a3 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};


    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;

    private TextView text;

    public static final int SERVERPORT = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text =findViewById(R.id.text2);

        updateConversationHandler = new Handler();

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {

            Socket socket;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    static class CommunicationThread implements Runnable {

        final Socket clientSocket;


        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {
                System.out.println("Server has been started");

                while(clientSocket.isConnected()){

                    // Create a datainput stream object to communicate with the client (Connect)
                    DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                    //writes on client socket
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                    // Collect the matrix through the data

                     // Create the matrix a1 & a2
                    for (int i = 0; i < 3; i++){
                        for (int j = 0; j < 3; j++){
                            a1[i][j] = input.readInt();
                    a1[i][j] = input.readInt();


                        }
                    }
                    //sent to client
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++)
                            out.writeInt(a3[i][j]);
                    out.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {

        }
    }

}