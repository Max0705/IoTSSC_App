package com.example.iotssc_app;

import android.app.Application;

import com.example.iotssc_app.adapter.DiscoveredBluetoothDevice;

import java.util.List;


public class ApplicationData extends Application {
    private List<DiscoveredBluetoothDevice> discoveredBluetoothDevices;

    public List<DiscoveredBluetoothDevice> getDevices() {
        return discoveredBluetoothDevices;
    }

    public void setDevices(List<DiscoveredBluetoothDevice> devices) {
        this.discoveredBluetoothDevices = devices;
    }
}
