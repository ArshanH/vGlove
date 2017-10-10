package com.capstone.vglove.vglovecapstone;



import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class RecognizeActivity extends AppCompatActivity {
    private vGloveApplication myapp;
    private BluetoothDevice mDevice;
    private InputStream mmStream;
    private TextView mt;
    private BluetoothGatt mBTG;
    private BluetoothLeService mBLEService;
    private BluetoothGattCharacteristic mBLEChar;
    private Handler mHand;
    private static int btCount;
    private static byte[] btRead;
    private final static String vGloveUUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    private final static String vGloveCharUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    private final static String vGloveDescUUID = "00002902-0000-1000-8000-00805f9b34fb";

    private AssetManager mManager;
    private InputStream mStream;
    private GestureRecognizer mGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mt = (TextView)findViewById(R.id.textView5);
        myapp = (vGloveApplication) this.getApplication();
        mDevice = myapp.getGlobDevice();
        mHand = new Handler();

        mManager = this.getAssets();
        try {
            mStream = mManager.open("gestureLib.txt");
        }
        catch (IOException e){

        }
        mGesture = new GestureRecognizer(mStream);
        if (mDevice == null) {
            Toast.makeText(getApplicationContext(), "Please register your vGlove!", Toast.LENGTH_LONG).show();
        }
        btCount = 0;
        btRead = new byte[60];

    }

    protected void onResume() {
        super.onResume();
    }

    public void onClickRead(View v){
        mBTG = mDevice.connectGatt(this, true, btleGattCallback);
        mt.setText("Connecting to BluetoothDevice Please Wait...");
    }

    private void displayData(String data) {
        if (data != null) {
            mt.setText(data);
        }
    }

    public void updateGlobArray(byte[] toInsert){
        System.arraycopy(toInsert, 0, btRead, btCount, 20);
        String result = "";
        btCount += 20;
        if (btCount == 60) {
            btCount = 0;
            result = mGesture.getGesture(btRead);
        }
        if (result != null) {
            mt.setText(result);
        }

    }

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            // this will get called anytime you perform a read or write characteristic operation
            final byte[] mBytes = characteristic.getValue();

            mHand.post(new Runnable() {
                @Override
                public void run() {
                    updateGlobArray(mBytes);
                }
            });

        }

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            // this will get called when a device connects or disconnects

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mHand.post(new Runnable() {
                    @Override
                    public void run() {
                        mt.setText("Service connected! Please wait to discover services...");
                    }
                });

                try {
                    Thread.sleep(600);
                }
                catch (InterruptedException w) {

                }
                mBTG.discoverServices();
            }
            else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mHand.post(new Runnable() {
                    @Override
                    public void run() {
                        mt.setText("Service disconnected...:(");
                    }
                });

            }
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
            // this will get called after the client initiates a 			BluetoothGatt.discoverServices() call
            mHand.post(new Runnable() {
                @Override
                public void run() {
                    mt.setText("Service discovered!");
                }
            });

            List<BluetoothGattService> services = mBTG.getServices();
            for (BluetoothGattService service : services) {

                if (service.getUuid().equals(UUID.fromString(vGloveUUID))) {
                    mHand.post(new Runnable() {
                        @Override
                        public void run() {
                            mt.setText("vGloveUUID found!");
                        }
                    });
                    List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();


                    for (final BluetoothGattCharacteristic btgchar : characteristics) {


                        if (btgchar.getUuid().equals(UUID.fromString(vGloveCharUUID))) {

                            mHand.post(new Runnable() {
                                @Override
                                public void run() {
                                    mt.setText("vGloveCharUUID found!");

                                }
                            });
                            mBTG.setCharacteristicNotification(btgchar, true);

                            for (final BluetoothGattDescriptor desc : btgchar.getDescriptors()) {
                                if (desc.getUuid().equals(UUID.fromString(vGloveDescUUID))) {
                                    desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    mBTG.writeDescriptor(desc);
                                }

                            }
                        }
                    }
                }


            }
        }
    };


/*
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBLEService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBLEService.initialize()) {

                Log.e("Tag", "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBLEService.connect(mDevice.getAddress());
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBLEService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                //mConnected = true;
                //updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //mConnected = false;
                //updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private void clearUI() {
        //mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mt.setText("No data");
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
*/
}
