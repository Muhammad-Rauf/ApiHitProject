package com.example.hitapiproject.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitapiproject.Dao.MemeDao
import com.example.hitapiproject.DataBase.AppDatabase
import com.example.hitapiproject.Entity.MemeEntity
import com.example.hitapiproject.Repository.MemeRepository
import com.example.hitapiproject.Utills.HandlerX
import com.example.hitapiproject.adapter.MemeAdapter
import com.example.hitapiproject.databinding.ActivityMainBinding
import com.example.hitapiproject.interfaces.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), MemeAdapter.OnMemeDeleteListener {
    private lateinit var memeRepository: MemeRepository
    private lateinit var memeAdapter: MemeAdapter
    private lateinit var memeDao: MemeDao
    private lateinit var progressDialog: ProgressDialog
    var BASE_URL = "https://api.imgflip.com/"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //   setContentView(R.layout.activity_main)
        binding.memeRecyclerView.layoutManager = LinearLayoutManager(this)
        memeDao = AppDatabase.getInstance(applicationContext).memeDao()
        memeRepository = MemeRepository(memeDao)
        loadingDialog()

        val savedMemes = memeRepository.getAllMemesLiveData()
        savedMemes.observe(this@MainActivity) { memes ->
            if (memes.isNotEmpty()) {
                Log.e("Meme", "Not Empty: ")

                updateAdapterData(memes)

            }
            else{
                Log.e("Meme", " Empty: ")
                getAndSaveAllMemeData()
            }
        }


    }



    private fun loadingDialog(){
    progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Loading...")
    progressDialog.setCancelable(false)
    progressDialog.show()

}
    private fun getAndSaveAllMemeData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retroData = retrofit.getData()

        retroData.enqueue(object :Callback<com.example.hitapiproject.model.Response>{
            override fun onResponse(
                call: Call<com.example.hitapiproject.model.Response>,
                response: Response<com.example.hitapiproject.model.Response>
            ) {
                if (response.isSuccessful) {
                    val memeResponse = response.body()
                    memeResponse?.let {
                        val memes = it.data.memes
                        val memeEntities = memes.map { meme ->
                            MemeEntity(
                                id = meme.id.toInt(),
                                memeName = meme.name,
                                memeUrl = meme.url,

                                )
                        }
                      //  memeAdapter = MemeAdapter(this@MainActivity,memeEntities)
                      //  binding.memeRecyclerView.adapter = memeAdapter
                        GlobalScope.launch(Dispatchers.IO) {
                            memeRepository.insertMemes(memeEntities)
                        }
                        val memesLiveData = memeRepository.getAllMemesLiveData()
                        // Observe the MutableLiveData and update the adapter when changes occur
                        memesLiveData.observe(this@MainActivity) { memes ->
                            updateAdapterData(memes)
                        }

                  /*  HandlerX({
                     GlobalScope.launch(Dispatchers.Main) {
                         val savedMemes = memeRepository.getAllMemesLiveData()

                         HandlerX({
                             memeAdapter = MemeAdapter(this@MainActivity, savedMemes,this@MainActivity)
                             binding.memeRecyclerView.adapter = memeAdapter
                             progressDialog.dismiss()
                         },3500)

                     }
                      },1500)*/

                    }
                }
            }
            override fun onFailure(
                call: Call<com.example.hitapiproject.model.Response>,
                t: Throwable
            ) {
                val memesLiveData = memeRepository.getAllMemesLiveData()
                // Observe the MutableLiveData and update the adapter when changes occur
                memesLiveData.observe(this@MainActivity) { memes ->
                    updateAdapterData(memes)
                }
                 /*   GlobalScope.launch(Dispatchers.Main) {
                        val savedMemes = memeRepository.getAllMemesLiveData()

                        HandlerX({
                            memeAdapter = MemeAdapter(this@MainActivity, savedMemes,this@MainActivity)
                            binding.memeRecyclerView.adapter = memeAdapter
                            progressDialog.dismiss()
                        },3500)

                    }*/


            }
        } )



    }

    override fun onMemeDeleted(meme: MemeEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            memeRepository.deleteMeme(meme)
            runOnUiThread {
                progressDialog.show()
                Toast.makeText(this@MainActivity, "Meme deleted!", Toast.LENGTH_SHORT).show()

            }
        }

    }
    private fun updateAdapterData(data: List<MemeEntity>) {
        memeAdapter = MemeAdapter(this, data, this)
        binding.memeRecyclerView.adapter = memeAdapter
        HandlerX({
            progressDialog.dismiss()

        },1500)
    }
}