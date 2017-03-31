package pl.marchuck.blebrowser.main;

import android.bluetooth.BluetoothDevice;

/**
 * Project "BleBrowser"
 * <p>
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

public interface ConnectDeviceListener {

    void connect(BluetoothDevice device);
    void disconnect(BluetoothDevice device);
}
