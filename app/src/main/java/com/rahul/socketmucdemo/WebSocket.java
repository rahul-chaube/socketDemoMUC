package com.rahul.socketmucdemo;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebSocket extends AppCompatActivity {
    public Socket clientSocket = null;
    String address, port;
    public static  String IP="192.168.4.1";
    public static final int PORT=6000;
    TextView textViewStatus;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        textViewStatus = findViewById(R.id.socketStatus);
        if (getIntent() != null) {
            address = getIntent().getStringExtra(MainActivity.ADDRESS);
            port = getIntent().getStringExtra(MainActivity.PORT);
            IP=address.trim();
            Log.e("Host ", address);

            Log.e("Port ", " " + port);
            new UpdateTask().execute();
        }


        new Thread(new ClientThread()).start();

    }

    private void connect() {
        this.clientSocket = new Socket();
        try {

            this.clientSocket.connect(new InetSocketAddress(IP, PORT), 5000);
            textViewStatus.setText("Connected "+clientSocket.isConnected());

        } catch (IOException e) {
            textViewStatus.setText(e.getMessage());
            Log.e("Exception Connection ", " " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        try {
            if (clientSocket.isConnected()) {

//                private InputStream in;
//                private OutputStream out;
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream())),
                        true);
                out.println(message);
                out.flush();
                textViewStatus.setText("*****Message Send ");
                Log.e("Server  Connected ", " *****Message Send  ");
            } else {
                textViewStatus.setText("Socket is not connected "+clientSocket.isConnected());
//                new Thread(new ClientThread()).start();
            }
        } catch (IOException e) {
            textViewStatus.setText(e.getMessage());
            Log.e("Exception ", "while message Send " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMeesage(View view) {
        sendMessage("on");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage("off");
                Toast.makeText(WebSocket.this, "Off Command is Send ", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
//        sendMessage("on");
    }

    public void discountPort(View view) {

            try {
                if (clientSocket!=null){
                clientSocket.shutdownOutput();
                clientSocket.shutdownInput();
                clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

        }
    }

    private class UpdateTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {

//          connect();
//          sendMessage("Test");
            return null;
        }

    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                connect();
//                InetAddress serverAddr = InetAddress.getByName(address);
//
//                socket = new Socket(serverAddr, Integer.parseInt(port));
//
//                Log.e("Is Socket ", "is Connected  " + socket.isConnected());
//                textViewStatus.setText("Connected .... ");


            } catch (Exception e1) {
                textViewStatus.setText("Failed .... " + e1.getMessage());
                e1.printStackTrace();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
