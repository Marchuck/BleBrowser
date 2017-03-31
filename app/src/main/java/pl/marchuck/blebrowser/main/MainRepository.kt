package pl.marchuck.blebrowser.main

import android.bluetooth.*
import android.util.Log
import pl.marchuck.blebrowser.App

import pl.marchuck.blebrowser.repository.WrappedBondedDevice
import java.util.*

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

class MainRepository : BluetoothGattCallback() {

    val UUID_NOTIFY = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    val UUID_SERVICE = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")

    var connected: Boolean = false
    var currentDevice: BluetoothDevice?= null
    var presenter: MainPresenter? = null

    val bondedDevices: List<WrappedBondedDevice>
        get() {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val bluetoothDevices = adapter.bondedDevices
            val wrappedDevices  = bluetoothDevices.map { WrappedBondedDevice(it).setConnected(it == currentDevice) }
            return wrappedDevices
        }

    var gatt: BluetoothGatt? = null
    var writableCharacteristic: BluetoothGattCharacteristic? = null


    fun connect(device: BluetoothDevice?, presenter: MainPresenter) {
        MainRepository@ this.presenter = presenter
        if(device==null) {
            presenter.onDisconnected(null)
            return
        }
        currentDevice = device
        gatt = currentDevice?.connectGatt(App.app, true, MainRepository@ this)
    }

    fun disconnect() {
        gatt?.disconnect()
        gatt?.close()
        connected = false
        gatt = null
    }

    override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
        super.onReadRemoteRssi(gatt, rssi, status)
        Log.d(TAG, "onReadRemoteRssi")
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)
        Log.d(TAG, "onCharacteristicRead")

    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        Log.d(TAG, "onCharacteristicWrite")
        presenter?.valueDidWrote(characteristic?.value)

    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
        Log.d(TAG, "onServicesDiscovered")

        for (gattService in gatt!!.services) {
            Log.i(TAG, gattService.uuid.toString())
            Log.i(TAG, UUID_SERVICE.toString())

            if (gattService.uuid.toString().toLowerCase() == UUID_SERVICE.toString().toLowerCase()) {

                for (gattCharacteristic in gattService.characteristics) {
                    if (gattCharacteristic.uuid.toString().toLowerCase() == UUID_NOTIFY.toString().toLowerCase()) {
                        Log.i(TAG, gattCharacteristic.uuid.toString());
                        Log.i(TAG, UUID_NOTIFY.toString());
                        writableCharacteristic = gattCharacteristic
                        gatt.setCharacteristicNotification(gattCharacteristic, true)

                    }
                }
            }
        }
    }

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
        super.onMtuChanged(gatt, mtu, status)
        Log.d(TAG, "onMtuChanged")

    }

    override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) {
        super.onReliableWriteCompleted(gatt, status)
        Log.d(TAG, "onReliableWriteCompleted")

    }

    override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
        super.onDescriptorWrite(gatt, descriptor, status)
        Log.d(TAG, "onDescriptorWrite")

    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicChanged(gatt, characteristic)
        Log.d(TAG, "onCharacteristicChanged " + Arrays.toString(characteristic?.value))

    }

    override fun onDescriptorRead(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
        super.onDescriptorRead(gatt, descriptor, status)
        Log.d(TAG, "onDescriptorRead")

    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        Log.d(TAG, "onConnectionStateChange")
        when (newState) {
            BluetoothProfile.STATE_CONNECTING -> {
                Log.i(TAG, "STATE_CONNECTING")
                //                onGattConnecting(gatt);
            }
            BluetoothProfile.STATE_DISCONNECTING -> {
                Log.i(TAG, "STATE_DISCONNECTING")
                //                onGattDisconnecting(gatt);
            }
            BluetoothProfile.STATE_CONNECTED -> {
                Log.i(TAG, "STATE_CONNECTED")
                connected = true
                presenter?.onConnected(gatt)


                //BluetoothDevice device = gatt.getDevice(); // Get device
                //discovers new services of this gatt
                gatt?.discoverServices()
            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.e(TAG, "STATE_DISCONNECTED")
                presenter?.onDisconnected(gatt)

            }
            else -> {
                Log.e(TAG, "STATE_OTHER")
            }
        }

    }

    companion object {
        val TAG = "MainRepository"
    }

    fun sendValue(message: String, mainPresenter: MainPresenter) {
        presenter = mainPresenter
        writableCharacteristic?.value = message.toByteArray()
        gatt?.writeCharacteristic(writableCharacteristic)
    }
}
