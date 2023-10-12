package com.example.hitapiproject.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField

@Entity(tableName = "memes")
 class MemeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0 ,
    val memeUrl: String,
    val memeName: String,

)