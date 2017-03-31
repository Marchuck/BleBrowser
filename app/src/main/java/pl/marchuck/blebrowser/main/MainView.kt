package pl.marchuck.blebrowser.main

import io.reactivex.Observable
import pl.marchuck.blebrowser.repository.WrappedBondedDevice

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

interface MainView {

    fun onBondedDevicesReceived(devicesList: List<WrappedBondedDevice>)

    fun requestBluetooth(): Observable<Boolean>

    fun showWeNeedBluetooth()

    fun onConnected(who: String)

    fun onDisconnected(who: String)

    fun showConnecting()

    fun hideConnecting()

}
