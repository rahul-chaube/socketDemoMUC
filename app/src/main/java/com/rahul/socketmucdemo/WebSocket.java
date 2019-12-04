package com.rahul.socketmucdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebSocket extends AppCompatActivity {
    public Socket clientSocket = null;
    String address, port;
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
            Log.e("Host ", address);
            Log.e("Port ", " " + port);
            new UpdateTask().execute();
        }

        Log.e("Host 111", address);
        Log.e("Port 111", " " + port);

        new Thread(new ClientThread()).start();

    }

    private void connect() {
        this.clientSocket = new Socket();
        try {

            this.clientSocket.connect(new InetSocketAddress(address, Integer.parseInt(port)), 5000);

        } catch (IOException e) {
            Log.e("Exception Connection ", " " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        try {
            if (socket.isConnected()) {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(message);
                out.flush();
                Log.e("Server  Connected ", " *****Message Send  ");
            } else {
                new Thread(new ClientThread()).start();
            }
        } catch (IOException e) {
            textViewStatus.setText(e.getMessage());
            Log.e("Exception ", "while message Send " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMeesage(View view) {
        sendMessage("on");
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
                InetAddress serverAddr = InetAddress.getByName(address);

                socket = new Socket(serverAddr, Integer.parseInt(port));

                Log.e("Is Socket ", "is Connected  " + socket.isConnected());
                textViewStatus.setText("Connected .... ");


            } catch (UnknownHostException e1) {
                textViewStatus.setText("Failed .... " + e1.getMessage());
                e1.printStackTrace();
            } catch (IOException e1) {
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
