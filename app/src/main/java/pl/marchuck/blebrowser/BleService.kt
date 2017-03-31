package pl.marchuck.blebrowser

/**
 * Project "BleBrowser"
 *
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */
data class BleService(val characteristics: List<BleCharacteristic>, val uuid: String);