package pl.marchuck.blebrowser.main

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.util.Log
import pl.marchuck.blebrowser.repository.WrappedBondedDevice
import pl.marchuck.blebrowser.repository.WrappedBondedDevice.Companion.getAliasName
import java.nio.charset.Charset

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

class MainPresenter(internal var view: MainView,
                    internal var repository: MainRepository) : ConnectDeviceListener {
    var persistentDevice: BluetoothDevice? = null

    override fun disconnect(device: BluetoothDevice?) {
        persistentDevice = null
        repository.disconnect()
    }

    override fun connect(device: BluetoothDevice?) {
        view.showConnecting()
        persistentDevice = device
        repository.connect(persistentDevice, MainPresenter@ this)

    }

    fun requestBondedDevices() {

        view.requestBluetooth()
                .subscribe({ granted ->
                    if (granted) {
                        view.onBondedDevicesReceived(repository.bondedDevices)
                    } else {
                        view.showWeNeedBluetooth()
                    }
                })
    }

    fun onConnected(gatt: BluetoothGatt?) {
        Log.d(TAG, "onConnected")
        requestBondedDevices()
        view.onConnected(getAliasName(gatt?.device))
    }

    fun onDisconnected(gatt: BluetoothGatt?) {
        Log.d(TAG, "onDisconnected")
        view.onDisconnected(getAliasName(gatt?.device))
    }

    companion object {
        val TAG = MainPresenter.javaClass.simpleName
    }

    fun onSendValue(message: String) {
        repository.sendValue(message, MainPresenter@ this)
    }

    fun valueDidWrote(value: ByteArray?) {
        Log.d(TAG, "value written " + value.toString());
    }

    fun disconnect() {
        Log.d(TAG, "disconnect")
        repository.disconnect()
    }

    fun reconnect() {
        if (!repository.connected)
            persistentDevice?.let {
                device ->
                {
                    Log.d(TAG, "reconnect")
                    repository.connect(device, MainPresenter@ this)

                }
            }
    }
}