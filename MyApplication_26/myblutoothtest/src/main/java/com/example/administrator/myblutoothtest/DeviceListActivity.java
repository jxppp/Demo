package com.example.administrator.myblutoothtest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/22.
 */
public class DeviceListActivity extends Activity {

    private ListView lisView;
    private BluetoothAdapter bluetoothAdapter;
    private DeviceListAdapter deviceListAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private Button btn_search_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        lisView = (ListView) findViewById(R.id.device_list);
        btn_search_stop = (Button) findViewById(R.id.btn_search_stop);

        if (mScanning) {
            btn_search_stop.setText("停止");
        } else {
            btn_search_stop.setText("搜索");
        }

        btn_search_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_search_stop.getText() == "搜索") {
                    deviceListAdapter.clear();
                    scanDevice(true);
                    btn_search_stop.setText("停止");
                } else {
                    scanDevice(false);
                    btn_search_stop.setText("搜索");
                }
            }
        });
        mHandler = new Handler();

        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BluetoothDevice device = deviceListAdapter.getDevice(position);
                if (device == null) return;
                final Intent intent = new Intent(DeviceListActivity.this, DeviceControlActivity.class);
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (mScanning){
                    bluetoothAdapter.cancelDiscovery();
                    mScanning = false;
                }
                startActivity(intent);
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //检查当前手机是否支持蓝牙设备
        if (bluetoothAdapter == null) {//判断手机是否有蓝牙功能
            Toast.makeText(this, "手机不支持蓝牙！！！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        //为了确保设备上啊蓝牙能使用，如果当前蓝牙设备没有启用，则弹出提示框；
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

        deviceListAdapter = new DeviceListAdapter();
        lisView.setAdapter(deviceListAdapter);
        scanDevice(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanDevice(final boolean enable) {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();//获得已绑定的设备
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceListAdapter.addDevice(device);
            }
        }
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
//                    bluetoothAdapter.stopLeScan(leScanCallback);
                    bluetoothAdapter.cancelDiscovery();
                    btn_search_stop.setText("搜索");
                }
            }, 10000);

            mScanning = true;
//            bluetoothAdapter.startLeScan(leScanCallback);
            bluetoothAdapter.startDiscovery();
            btn_search_stop.setText("停止");
        } else {
            mScanning = false;
//            bluetoothAdapter.stopLeScan(leScanCallback);
            bluetoothAdapter.cancelDiscovery();
            btn_search_stop.setText("搜索");
        }


    }

    //此方法过时，实验结果也是搜索结果很少
//    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
//        @Override
//        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    deviceListAdapter.addDevice(device);
//                    deviceListAdapter.notifyDataSetChanged();
//                }
//            });
//        }
//    };

    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //当找到某个设备的时候
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceListAdapter.addDevice(device);
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    };


    private class DeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mDevices;
        private LayoutInflater mInflator;

        public DeviceListAdapter() {
            super();
            mDevices = new ArrayList<>();
            mInflator = DeviceListActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mDevices.contains(device)) {
                mDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mDevices.get(position);
        }

        public void clear() {
            mDevices.clear();
        }

        @Override
        public int getCount() {
            return mDevices.size();
        }

        @Override

        public Object getItem(int position) {
            return mDevices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflator.inflate(R.layout.item_device_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
                viewHolder.deviceAddress = (TextView) convertView.findViewById(R.id.device_address);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            BluetoothDevice device = mDevices.get(position);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText(deviceName);
            } else {
                viewHolder.deviceName.setText("Unknown device");
            }
            viewHolder.deviceAddress.setText(device.getAddress());
            return convertView;
        }

        class ViewHolder {
            TextView deviceName;
            TextView deviceAddress;
        }
    }

}
