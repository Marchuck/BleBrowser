package pl.marchuck.blebrowser

/**
 * Project "BleBrowser"
 *
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */
data class BleCharacteristic(val descriptors: List<BleDescriptor>, val uuid: String)