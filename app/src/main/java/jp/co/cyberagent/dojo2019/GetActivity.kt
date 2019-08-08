package jp.co.cyberagent.dojo2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GetActivity : AppCompatActivity() {

    var realm: Realm? = null

    var index : Int? = null
    var index2 : Int? = null
    var index3: Int? = null
    var index4 : Int? = null

    var name : String? = null
    var twitter : String? = null
    var github : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        realm = Realm.getDefaultInstance()


        val dataResult = intent.getStringExtra("RESULT")
        val dataText = findViewById<TextView>(R.id.contentText)
        dataText.setText(dataResult)

        //文字の切り出し方の参考文献
        //https://qiita.com/emboss369/items/c39f196e703447820b47#kotlin%E6%96%87%E5%AD%97%E5%88%97%E9%96%A2%E6%95%B0
        //https://qiita.com/farman0629/items/f959a4ff791f26b33fc3

        index = dataResult.indexOf("=")
        index2 = dataResult.indexOf("&")
        index3 = dataResult.indexOf("tw")
        index4 = dataResult.indexOf("&gh")

        name = dataResult.substring(index!! + 1, index2!!)
        twitter = dataResult.substring(index3!! + 3, index4!!)
        github = dataResult.substring(index4!! + 4)

        val nameText = findViewById<TextView>(R.id.name_text)
        nameText.setText(name)

        val githubText = findViewById<TextView>(R.id.github_text)
        githubText.setText(github)

        val twitterText = findViewById<TextView>(R.id.twitter_text)
        twitterText.setText(twitter)


    }

    @Suppress("UNUSED_PARAMETER")
    fun add(view: View) {

        //日付を取得する
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE)
        val updateDate = sdf.format(date)

      //  check(name, github, twitter, updateDate)

        save(name, github, twitter, updateDate)

        finish()

    }

    /*private fun check(name: String?, github: String?, twitter: String?, updateDate: String) {

        //Profileクラスのインスタンスを生成
        val profile = Profile()

        //Profileのそれぞれの要素に、Addの要素を代入する
        profile.name  = name
        profile.github = github
        profile.twitter = twitter
        profile.updateData = updateDate


        //Logで確認する
        Log.d("Proflie", profile.name)
        Log.d("Profile", profile.github)
        Log.d("Profile", profile.twitter)
        Log.d("Profile", profile.updateData)
    }*/

    fun save(name: String?, github: String?, twitter: String?, updataData: String) {

        //メモを保存する,Memoクラスに移したものをRealmに保存する
        realm?.executeTransaction(Realm.Transaction {
            val profile = realm?.createObject(Profile::class.java)
            profile?.name = name
            profile?.github = github
            profile?.twitter = twitter
            profile?.updateData = updataData

        })
    }

    override  fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm?.close()
    }
}
