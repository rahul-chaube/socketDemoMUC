package com.rahul.socketmucdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WebSocket extends AppCompatActivity {
    public Socket clientSocket = null;
    String address,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        if (getIntent()!=null)
        {
            address=getIntent().getStringExtra(MainActivity.ADDRESS);
            port=getIntent().getStringExtra(MainActivity.PORT);
            Log.e("Host ",address);
            Log.e("Port "," "+port);
            new UpdateTask().execute();
        }
        Log.e("Host 111",address);
        Log.e("Port 111"," "+port);

    }

    private void connect() {
        this.clientSocket = new Socket();
        try {

            this.clientSocket.connect(new InetSocketAddress(address, Integer.parseInt(port)), 5000);
            sendMessage("test");
        } catch (IOException e) {
            Log.e("Exception Connection "," "+e.getMessage());
            e.printStackTrace();
        }
    }

   public void sendMessage(String message)
    {
        if (clientSocket.isConnected())
        {
            Log.e("Server  Connected "," ***** ");
            try {
                DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
                dOut.writeByte(1);
                dOut.writeUTF("This is the first type of message."+message);
                dOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.e("Server Not Connected "," ***** ");
            connect();
//            sendMessage(message);
        }
    }

    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {

          connect();
//          sendMessage("Test");
            return null;
        }

    }
}
