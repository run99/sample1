package jp.co.cyberagent.dojo2019

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.jar.Attributes

class ProfileAdapter (context: Context, textViewResourceId: Int, objects: List<Profile>) : ArrayAdapter<Profile>(context, textViewResourceId, objects) {

    var layoutinflater: LayoutInflater? = null

    init {
        layoutinflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val profile = getItem(position)

        if (convertView == null) {
            convertView = layoutinflater?.inflate(R.layout.list_item, null)
        }

        val NameText = convertView!!.findViewById(R.id.name_title) as TextView
        val TwitterText = convertView.findViewById(R.id.twitter_title) as TextView
        val GithubText = convertView.findViewById(R.id.github_title) as TextView

        NameText.text = profile?.name
        TwitterText.text = profile?.twitter
        GithubText.text = profile?.github

        return convertView


    }
}
