package paula.saenz.pickamovie.MovieDetail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import paula.saenz.pickamovie.MovieResult.Movie
import paula.saenz.pickamovie.R
import paula.saenz.pickamovie.databinding.ActivityDetailBinding
import java.net.URL

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        movie = intent.getParcelableExtra<Movie>("MOVIE") ?: run {
            Toast.makeText(this, getString(R.string.error_loading_movie), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        supportActionBar?.title = movie.title

        // Setear datos
        binding.tvMovieTitle.text = movie.title
        binding.tvDescription.text = getString(R.string.label_description, movie.description)
        binding.tvYear.text = getString(R.string.label_year, movie.year.toString())
        binding.tvGenre.text = getString(R.string.label_genre, movie.genre)
        binding.tvStars.text = getString(R.string.label_rating, movie.stars.toString())


        if (movie.image_url.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val input = URL(movie.image_url).openStream()
                    val bitmap = BitmapFactory.decodeStream(input)
                    withContext(Dispatchers.Main) {
                        binding.ivMovieImage.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DetailActivity,
                            getString(R.string.error_loading_image),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        updateFavoriteIcon(FavoritesManager.isFavorite(this, movie.id))

        binding.fabFavorite.setOnClickListener {
            if (FavoritesManager.isFavorite(this, movie.id)) {
                FavoritesManager.removeFavorite(this, movie.id)
                Toast.makeText(this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
            } else {
                FavoritesManager.addFavorite(this, movie)
                Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
            }
            updateFavoriteIcon(FavoritesManager.isFavorite(this, movie.id))
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.heart_fill)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.heart)
        }
    }
}
