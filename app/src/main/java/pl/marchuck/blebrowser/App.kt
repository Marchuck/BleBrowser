package pl.marchuck.blebrowser

import android.app.Application
import android.content.Context

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

    companion object {

        internal var ctx: Context? = null

        val app: App
            get() = ctx?.applicationContext as App
    }
}
