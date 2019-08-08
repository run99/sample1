package jp.co.cyberagent.dojo2019

import android.app.Application
import io.realm.Realm

//android:name=".ProfileApplication"をマニフェストに記述することで、使えるようになった。
class ProfileApplication : Application() {

    override fun onCreate(){
        super.onCreate()

        Realm.init(applicationContext)
    }
}