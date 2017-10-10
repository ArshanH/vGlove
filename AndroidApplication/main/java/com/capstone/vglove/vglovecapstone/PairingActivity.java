package com.capstone.vglove.vglovecapstone;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.UUID;
/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class PairingActivity extends AppCompatActivity {
    //private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private ArrayAdapter<BluetoothDevice> mBTArray;
    private ListView devicelist;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBTArray = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_selectable_list_item);
        mHandler = new Handler();
        devicelist = (ListView) findViewById(R.id.BLEList);
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        devicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                BluetoothDevice tempdev = mBTArray.getItem(position);
                vGloveApplication myapp = (vGloveApplication)getApplicationContext();
                myapp.setGlobDevice(tempdev);
                Toast.makeText(getApplicationContext(), tempdev.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
        devicelist.setAdapter(mBTArray);
        // Initializes list view adapter.
        //mLeDeviceListAdapter = new LeDeviceListAdapter();
        //setListAdapter(mLeDeviceListAdapter);
        //scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onPause() {
        super.onPause();

        scanLeDevice(false);
        //mLeDeviceListAdapter.clear();
    }

    public void onScanButtonClick(View v){
        scanLeDevice(true);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBTArray.getPosition(device) < 0) {
                                mBTArray.add(device);
                                mBTArray.notifyDataSetChanged();
                            }

                        }
                    });
                }
            };
}





/*import android.app.ListActivity;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.ArrayList;
import java.util.UUID;

import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import static android.content.ContentValues.TAG;

public class PairingActivity extends AppCompatActivity {
    Button btnPaired;
    ListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a67");
    private BluetoothDevice mydevice;
    private ConnectThread mycthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnPaired = (Button) findViewById(R.id.button);
        devicelist = (ListView) findViewById(R.id.listView);
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item);
        devicelist.setAdapter(BTArrayAdapter);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        devicelist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "click ", Toast.LENGTH_LONG).show();
                myBluetooth.cancelDiscovery();
                String res = BTArrayAdapter.getItem(position);

                int find = res.lastIndexOf('\n');
                String mac = res.substring(find + 1);
                //Toast.makeText(getApplicationContext(), "MAC Address: " + mac, Toast.LENGTH_LONG).show();
                mydevice = myBluetooth.getRemoteDevice(mac);
                if (mydevice == null) {
                    Toast.makeText(getApplicationContext(), "bt device is null", Toast.LENGTH_LONG).show();
                }
                mycthread = new ConnectThread(mydevice); //should have a socket after this
                mycthread.run();
            }
        });


    }

    private final BroadcastReceiver bReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            // When discovery finds a device

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                // Get the BluetoothDevice object from the Intent

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // add the name and the MAC address of the object to the arrayAdapter

                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

                BTArrayAdapter.notifyDataSetChanged();

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();

            }
        }
    };

    public void bluetoothStuff(View v) {
        if (!myBluetooth.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }


        if (myBluetooth.isDiscovering()) {
            // the button is pressed when it discovers, so cancel the discovery
            myBluetooth.cancelDiscovery();

        } else {
            BTArrayAdapter.clear();
            myBluetooth.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }


    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String BTUUID = "";
        private ParcelUuid[] myuuid;


        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                //device.fetchUuidsWithSdp();
                myuuid = device.getUuids();
                if (myuuid == null) {
                    Toast.makeText(getApplicationContext(), "Error with uuids", Toast.LENGTH_LONG).show();
                    Log.println(Log.ERROR, "UUIDS", "Error uuids is null");
                    System.exit(0);
                }
                tmp = device.createRfcommSocketToServiceRecord(myuuid[1].getUuid());
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error creating socket", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Socket's create() method failed", e);
            }
            //Toast.makeText(getApplicationContext(), tmp.toString(), Toast.LENGTH_LONG).show();
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            myBluetooth.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                Toast.makeText(getApplicationContext(), "Could not connect to bluetooth socket", Toast.LENGTH_LONG).show();
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }
            Toast.makeText(getApplicationContext(), "Adding socket to metadata...", Toast.LENGTH_LONG).show();
            sendToAppMeta(mmSocket);

        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    private void sendToAppMeta(BluetoothSocket newsock) {
        vGloveApplication myapp = (vGloveApplication) getApplicationContext();
        myapp.setGlobSocket(newsock);
        //Toast.makeText(getApplicationContext(), myapp.globsocket.toString(), Toast.LENGTH_LONG).show();

    }

}
*/