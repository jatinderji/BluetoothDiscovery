package com.jatin.bluetoothdiscovery;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btnDicover;
    private EditText txtSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSeconds = findViewById(R.id.txtSeconds);
        btnDicover = findViewById(R.id.btnDiscover);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Log.d("jv",adapter.getName());
        btnDicover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int seconds = Integer.parseInt(txtSeconds.getText().toString());
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,seconds);
                startActivity(intent);

                IntentFilter myFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                registerReceiver(blueDiscoveryBroadcast, myFilter);

            }
        });
    }

    private  final BroadcastReceiver blueDiscoveryBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

           String action = intent.getAction();
           if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)){

               int result = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);

               switch (result){
                   case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:{
                       Log.d("jv","Bluetooth is discoverable now");
                       break;
                   }
               }
           }

        }
    };
}