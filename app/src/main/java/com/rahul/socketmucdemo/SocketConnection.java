package com.rahul.socketmucdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketConnection extends AppCompatActivity {
    String address,port;
    Socket socket;
    TextView textViewCompletePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_connection);
        textViewCompletePath=findViewById(R.id.completePath);
        if (getIntent()!=null)
        {
            address=getIntent().getStringExtra(MainActivity.ADDRESS);
            port=getIntent().getStringExtra(MainActivity.PORT);
            Log.e("Host ",address);
            Log.e("Port "," "+port);
            connection(address,port);
        }

    }

    void  connection(String address, String port)
    {
        String uri="http://192.168.2.179:3000/";
        try {
            String path=address+":"+port+"/";
            textViewCompletePath.setText(path);
            socket=IO.socket(path);
            socket.connect();
            
            if(socket.connected())
            {
                Toast.makeText(this, "Connected ", Toast.LENGTH_SHORT).show();
            }
            socket.emit("join", Build.MANUFACTURER +"  "+Build.MODEL);
            socket.on("newJoin", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("New Join is "," "+args[0]);
//                    Toast.makeText(MainActivity.this, ""+args[0], Toast.LENGTH_SHORT).show();
                }
            });
            socket.on("chat message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("Message "," "+args[0]);
//                    Toast.makeText(MainActivity.this, ""+args[0], Toast.LENGTH_SHORT).show();
                }
            });
            socket.on("command", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("Received Command "," "+args[0]);
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Failed to connect "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Socket Conn Error ",e.getMessage());
            e.printStackTrace();
        }


    }

    private void socketConnection(String address, String port) {
        try {
            String path=address+":"+port+"/";
            Log.e("Complete Path ",path);
            socket= IO.socket(path);
            socket.connect();
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
            socket.emit("command", "Hi " + System.currentTimeMillis());
            Log.e("Server is  ","Message Send***********  ");
        }
        else {
            connection(address,port);
            Log.e("Server is  ","not connected***********  ");
        }
        }

    public void sendMeesage(View view) {
        sendCommand();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
