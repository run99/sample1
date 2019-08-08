package jp.co.cyberagent.dojo2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        realm = Realm.getDefaultInstance()


        val nameText = findViewById<TextView>(R.id.name_text)
        val githubText = findViewById<TextView>(R.id.github_text)
        val twitterText = findViewById<TextView>(R.id.twitter_text)

        //realmから変数名が探す値に一致するデータを検索する、REALMからデータを取得して、それをそれぞれセットする
        val profile = realm?.where(Profile::class.java)?.equalTo(
            "updateData", getIntent().getStringExtra("updateData"))?.findFirst()


        nameText.setText(profile?.name)
        githubText.setText(profile?.github)
        twitterText.setText(profile?.twitter)


        twitterButton.setOnClickListener{
            val intent = Intent(this, TwitterActivity::class.java)
            intent.putExtra("Twitter", profile?.twitter)

            startActivity(intent)
        }

        githubButton.setOnClickListener{
            val intent = Intent(this, GithubActivity::class.java)
            intent.putExtra("GitHub", profile?.github)

            startActivity(intent)
        }
    }


    override  fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm?.close()
    }
}
