package com.example.hitapiproject.Dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hitapiproject.Entity.MemeEntity
@Dao
interface MemeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemes(memes: List<MemeEntity>)

    @Query("SELECT * FROM memes")
    fun getAllMemesLiveData(): LiveData<List<MemeEntity>>

    @Delete
    suspend fun deleteMeme(meme: MemeEntity)
}