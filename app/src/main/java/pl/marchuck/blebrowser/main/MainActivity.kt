package pl.marchuck.blebrowser.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.marchuck.blebrowser.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main, MainFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
