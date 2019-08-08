package jp.co.cyberagent.dojo2019

import io.realm.RealmObject

open class Profile : RealmObject(){

    var name: String? = null
    var github: String? = null
    var twitter:String? = null
    var updateData:String? = null
}
