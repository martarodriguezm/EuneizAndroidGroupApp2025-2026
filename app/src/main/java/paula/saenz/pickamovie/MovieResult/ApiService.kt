package paula.saenz.pickamovie.MovieResult

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

        @GET("api-movies")
        fun getMovies(): Call<List<Movie>>

}