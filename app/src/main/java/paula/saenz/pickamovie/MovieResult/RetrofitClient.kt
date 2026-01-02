package paula.saenz.pickamovie.MovieResult

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitClient {

        private const val BASE_URL = "https://devsapihub.com/"

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    object ApiClient {
        val apiService: ApiService by lazy {
            RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
