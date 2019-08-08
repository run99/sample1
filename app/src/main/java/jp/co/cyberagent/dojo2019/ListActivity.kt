package jp.co.cyberagent.dojo2019

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    var realm: Realm? = null
    var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listView = findViewById<ListView>(R.id.listView)

        realm = Realm.getDefaultInstance()

        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            //onItemClickのカッコ内の変数を変えるとエラー消える
            //位置を取得、position番目に対応するメモ情報を持ってくる
            val profile = parent.getItemAtPosition(position) as Profile
            //編集画面に画面遷移の準備
            val intent = Intent(this, DetailActivity::class.java)
            //profileクラスのupdateDateという変数の値をupdateDateというカギを使ってIntentにセットしている
            intent.putExtra("updateData", profile.updateData)
            //画面遷移
            startActivity(intent)
        }

        listView?.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, _, position, _ ->
            val profile = realm?.where(Profile::class.java)?.equalTo(
                "updateData",
                (parent.getItemAtPosition(position) as Profile).updateData
            )?.findFirst()

            // 削除する
            realm?.executeTransaction(Realm.Transaction { profile?.deleteFromRealm() })

            setMemoList()

            false
        }

        scanButton.setOnClickListener {

            IntentIntegrator(this).initiateScan()
        }
    }



    fun setMemoList() {

        //realmから読み取る
        val result = realm?.where<Profile>(Profile::class.java)?.findAll()
        val items = realm?.copyFromRealm(result)

        //adapterにデータを渡す
        val adapter = ProfileAdapter(this, R.layout.list_item, items as List<Profile>)
        //listViewにAdapterをセットしている
        listView!!.adapter = adapter

    }


    //QR読み取り
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                val result1 = result.contents.toString()

                val intent = Intent(this, GetActivity::class.java)
                intent.putExtra("RESULT", result1)

                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm?.close()
    }

    override fun onResume() {
        super.onResume()

        setMemoList()
    }


}
