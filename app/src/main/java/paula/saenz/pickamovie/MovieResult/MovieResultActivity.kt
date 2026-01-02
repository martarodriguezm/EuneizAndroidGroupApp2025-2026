package paula.saenz.pickamovie.MovieResult

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import paula.saenz.pickamovie.MovieDetail.DetailActivity
import paula.saenz.pickamovie.R
import paula.saenz.pickamovie.databinding.ActivityMovieResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieResultActivity : AppCompatActivity(), MovieListener {

    private lateinit var binding: ActivityMovieResultBinding
    private lateinit var adapter: MovieResultAdapter

    private val allMovies = ArrayList<Movie>()
    private val filteredMovies = ArrayList<Movie>()
    private lateinit var selectedGenre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMovieResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        selectedGenre = intent.getStringExtra("GENRE") ?: ""
        supportActionBar?.title = selectedGenre

        binding.recyclerMovies.layoutManager = LinearLayoutManager(this)
        adapter = MovieResultAdapter(filteredMovies, this)
        binding.recyclerMovies.adapter = adapter

        fetchMoviesFromApi()
    }

    private fun fetchMoviesFromApi() {
        val call = ApiClient.apiService.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful && response.body() != null) {
                    allMovies.clear()
                    allMovies.addAll(response.body()!!)

                    filteredMovies.clear()
                    filteredMovies.addAll(allMovies.filter {
                        it.genre.equals(selectedGenre, ignoreCase = true)
                    })

                    adapter.notifyDataSetChanged()

                    if (filteredMovies.isEmpty()) {
                        Toast.makeText(
                            this@MovieResultActivity,
                            getString(R.string.no_movies_for_genre),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@MovieResultActivity,
                        getString(R.string.error_fetch_movies),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Toast.makeText(
                    this@MovieResultActivity,
                    getString(R.string.error_connection, t.message),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("MovieResultActivity", "Error: ${t.message}")
            }
        })
    }

    override fun movieClicked(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("MOVIE", movie)
        startActivity(intent)
    }
}