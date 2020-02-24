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

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iotssc_app.MainActivity;
import com.example.iotssc_app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class used as the base adapter for the RecyclerView device display.
 */
public class ScannerDevicesAdapter extends RecyclerView.Adapter<ScannerDevicesAdapter.ViewHolder> {

	private final static String TAG = "ScannerDevicesAdapter";

	private final Context mContext;
	private List<DiscoveredBluetoothDevice> mDevices;
	private OnItemClickListener mOnItemClickListener;

	/**
	 * Constructor used to initialise the class with the activity context and a list of devices
	 * to be displayed in the view.
	 * @param activity the activity context
	 * @param mDevices list of discovered bluetooth devices seen by the user
	 */
	public ScannerDevicesAdapter(@NonNull final MainActivity activity,
								 @NonNull final List<DiscoveredBluetoothDevice> mDevices) {
		mContext = activity;
		setHasStableIds(true);
		this.mDevices = mDevices;
	}

	/**
	 * Inflates the each row used to represent a device in the view {@link layout/device_item.xml}.
	 * @param parent the parent view group
	 * @param viewType the view type
	 * @return the view holder linked with the rows.
	 */
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		final View layoutView = LayoutInflater.from(mContext)
				.inflate(R.layout.device_item, parent, false);
		return new ViewHolder(layoutView);
	}

	/**
	 * Links user actions on the interface (e.g. clicking on a row in the RecyclerView) to the
	 * wider codebase.
	 */
	public final class ViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.checkBox) CheckBox checkBox;
		@BindView(R.id.device_address) TextView deviceAddress;
		@BindView(R.id.device_name) TextView deviceName;
		@BindView(R.id.rssi) ImageView rssi;

		private ViewHolder(@NonNull final View view) {
			super(view);
			ButterKnife.bind(this, view);

			// Returns the index of the device (from the mDevices list) which has been selected
			// in the RecyclerView when its row has been clicked.
			view.findViewById(R.id.device_container).setOnClickListener(v -> {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(mDevices.get(getAdapterPosition()));
				}

			});

			// Updates the checked argument in the device object when it has been clicked
			view.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					mDevices.get(getAdapterPosition()).setChecked();

					Log.d(TAG, "Selected device - address: "
							+ mDevices.get(getAdapterPosition()).getMacAddress()
							+ " - checked: " + mDevices.get(getAdapterPosition()).getChecked());
				}
			});
		}
	}


	/**
	 * Binds a row in the view holder with the values of the device.
	 * @param holder the parent view holder
	 * @param position the position of the row in the view holder
	 */
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		final DiscoveredBluetoothDevice device = mDevices.get(position);
		final String deviceName = device.getName();

		// Sets the name of the device in the view, depending on whether its available
		if (!TextUtils.isEmpty(deviceName))
			holder.deviceName.setText(deviceName);
		else
			holder.deviceName.setText(R.string.unknown_device);

		// Sets the device address in the view
		holder.deviceAddress.setText(device.getMacAddress());

		// Translates a device's detected rssi value to a percentage and displays this in the view
		final int rssiPercent = (int) (100.0f * (127.0f + device.getRssi()) / (127.0f + 20.0f));
		holder.rssi.setImageLevel(rssiPercent);

	}

	/**
	 * Callback called when an item in the view is clicked.
	 */
	@FunctionalInterface
	public interface OnItemClickListener {
		void onItemClick(@NonNull final DiscoveredBluetoothDevice device);
	}


	/**
	 * Uses the devices hashcode as the unique id for distinguishing between rows on the
	 * RecyclerView.
	 * @param position the position of the row in the view
	 * @return the devices hashcode
	 */
	@Override
	public long getItemId(final int position) {
		return mDevices.get(position).hashCode();
	}

	/**
	 *
	 * @return
	 */
	@Override
	public int getItemCount() {
		return mDevices != null ? mDevices.size() : 0;
	}


}
