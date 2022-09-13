package id.co.iconpln.dicodingintermediate_storyapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //Ignore theme mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var instance: ApplicationController
            private set
    }
}