package com.example.hitapiproject.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hitapiproject.Dao.MemeDao
import com.example.hitapiproject.Entity.MemeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemeRepository(private val memeDao: MemeDao) {

    suspend fun insertMemes(memes: List<MemeEntity>) {
        withContext(Dispatchers.IO) {
            memeDao.insertMemes(memes)
        }
    }

    fun getAllMemesLiveData(): LiveData<List<MemeEntity>> {
        return memeDao.getAllMemesLiveData()
    }
    suspend fun deleteMeme(meme: MemeEntity) {
        withContext(Dispatchers.IO) {
            try {
                memeDao.deleteMeme(meme)
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the deletion
                e.printStackTrace()
            }
        }
    }
}