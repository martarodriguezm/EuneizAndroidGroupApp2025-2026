package paula.saenz.pickamovie.Favs

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import paula.saenz.pickamovie.MovieDetail.DetailActivity
import paula.saenz.pickamovie.MovieDetail.FavoritesManager
import paula.saenz.pickamovie.MovieResult.Movie
import paula.saenz.pickamovie.MovieResult.MovieListener
import paula.saenz.pickamovie.MovieResult.MovieResultAdapter
import paula.saenz.pickamovie.R
import paula.saenz.pickamovie.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: MovieResultAdapter
    private val favoriteMovies: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top + 60,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        supportActionBar?.title = getString(R.string.favorites)

        // RecyclerView
        binding.recyclerMovies.layoutManager = LinearLayoutManager(this)

        adapter = MovieResultAdapter(favoriteMovies, object : MovieListener {
            override fun movieClicked(movie: Movie) {
                val intent = Intent(this@FavoritesActivity, DetailActivity::class.java)
                intent.putExtra("MOVIE", movie)
                startActivity(intent)
            }
        })

        binding.recyclerMovies.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        favoriteMovies.clear()
        favoriteMovies.addAll(FavoritesManager.getAllFavorites(this))
        adapter.notifyDataSetChanged()
    }
}
