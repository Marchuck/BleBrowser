package pl.marchuck.blebrowser.repository

import android.bluetooth.BluetoothDevice
import android.util.Log

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

class WrappedBondedDevice(var device: BluetoothDevice?) {

    var connected: Boolean = false

    var alias: String

    init {
        this.alias = getAliasName(device)
        this.connected = false
    }

    fun setConnected(c:Boolean): WrappedBondedDevice{
        connected =c
        return WrappedBondedDevice@this
    }

    companion object {
        val TAG = WrappedBondedDevice::class.java.simpleName

        fun getAliasName(device: BluetoothDevice?): String {

            if (device == null) return "?"

            var deviceAlias = device.name
            try {
                val method = device.javaClass.getMethod("getAliasName")
                if (method != null) {
                    deviceAlias = method.invoke(device) as String
                }
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
                Log.e(TAG, "getAliasName: ", e)
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
                Log.e(TAG, "getAliasName: ", e)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                Log.e(TAG, "getAliasName: ", e)
            }

            return deviceAlias
        }
    }

    override fun toString(): String {
        return "WrappedBondedDevice(device=$device, alias='$alias')"
    }

}
