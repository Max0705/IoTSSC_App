package com.example.iotssc_app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothReceiver {

    private String TAG = "Bluetooth Receiver";

    private Set<DiscoveredBluetoothDevice> discoveredDevicesHashSet = new HashSet<>();

    /**
     * The broadcast receiver is a listener which filters device intents to detect bluetooth
     * devices nearby. Each device is added to a set to prevent duplication.
     */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Log.v(TAG, action);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Create a new device item if unique device
                discoveredDevicesHashSet.add(new DiscoveredBluetoothDevice(device, intent));
                Log.d(TAG, String.valueOf(device.getName()));
            }

        }
    };

    /**
     * Returns the broadcast receiver.
     * @return the broadcast receiver instance
     */
    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    /**
     * Converts the hashset into a list for use by the adapter.
     * @return
     */
    public List<DiscoveredBluetoothDevice> getDiscoveredBluetoothDevices() {
        return new ArrayList<>(discoveredDevicesHashSet);
    }

    /**
     * Resets the the hashset of detected device in the receiver to update before a fresh device
     * scan.
     */
    public void resetDiscoveredBluetoothDevices() {
        discoveredDevicesHashSet = new HashSet<>();
    }
}
