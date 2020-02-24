/*
 * Copyright (c) 2018, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.iotssc_app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;


public class DiscoveredBluetoothDevice {

	private final BluetoothDevice device;
	private String name;
	private int rssi;
	private String macAddress;
	private int hashCode;
	private Boolean checked;

	/**
	 * Constructor for initialising the class. Class wraps a Bluetooth device with the additional
	 * checked parameter.
	 * @param device the bluetooth device class
	 * @param intent an intent required to query the device rssi value
	 */
	public DiscoveredBluetoothDevice(BluetoothDevice device, Intent intent) {
		this.device = device;
		this.name = device.getName();
		this.rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
		this.macAddress = device.getAddress();
		this.hashCode = device.hashCode();
		this.checked = false;
	}

	/**
	 * Returns the device name.
	 * @return string - device name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the device address.
	 * @return string - device address
	 */
	public int getRssi() { return rssi; }

	/**
	 * Returns the device mac address.
	 * @return string - device mac address
	 */
	public String getMacAddress() { return macAddress; }

	/**
	 * Returns the checked status of the device.
	 * @return boolean - checked status
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * Updates the checked status of the device.
	 */
	public void setChecked() {
		this.checked = !this.checked;
	}

	/**
	 * Returns the device hash code.
	 * @return int - device hash code
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}

	/**
	 * Method used to compares equality of devices.
	 * @param o the object to compare with
	 * @return True if the devices are the same
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof DiscoveredBluetoothDevice) {
			final DiscoveredBluetoothDevice that = (DiscoveredBluetoothDevice) o;
			return device.getAddress().equals(that.device.getAddress());
		}
		return super.equals(o);
	}

}
