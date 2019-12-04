package com.rahul.socketmucdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextAddress,editTextPort;
    public static final String ADDRESS="address";
    public static final String PORT="port";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextAddress=findViewById(R.id.txtAddress);
        editTextPort=findViewById(R.id.editTextPortNumber);

    }

    public void buttonCLick(View view) {

        if (editTextAddress.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
        }else  if (editTextPort.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Port Number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(MainActivity.this,WebSocket.class);
            intent.putExtra(ADDRESS,editTextAddress.getText().toString().trim());
            intent.putExtra(PORT,editTextPort.getText().toString());
            startActivity(intent);
        }
    }
}
