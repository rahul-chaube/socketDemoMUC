package com.rahul.socketmucdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketConnection extends AppCompatActivity {
    String address,port;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_connection);
        if (getIntent()!=null)
        {
            address=getIntent().getStringExtra(MainActivity.ADDRESS);
            port=getIntent().getStringExtra(MainActivity.PORT);
            Log.e("Host ",address);
            Log.e("Port "," "+port);
            socketConnection(address,port);
        }

    }

    private void socketConnection(String address, String port) {
        try {
            socket= IO.socket(address+":"+port);
//            socket = IO.socket("http://localhost");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("stream", "hi");
                    socket.disconnect();
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            });
            socket.connect();
//            socket=new Socket(address, Integer.parseInt(port));
//            socket.connect();
        } catch (URISyntaxException e) {
            Log.e("Exception Occur ",e.getMessage());
            e.printStackTrace();
        }

    }

    public void sendCommand()
    {
        if (socket.connected()) {
            socket.emit("stream", "Hi " + System.currentTimeMillis());
            Log.e("Server is  ","Message Send***********  ");
        }
        else {
            socketConnection(address,port);
            Log.e("Server is  ","not connected***********  ");
        }
        }

    public void sendMeesage(View view) {
        sendCommand();
    }
}
