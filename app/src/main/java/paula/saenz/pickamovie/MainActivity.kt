package paula.saenz.pickamovie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import paula.saenz.pickamovie.Favs.FavoritesActivity
import paula.saenz.pickamovie.GenreList.GenresActivity
import paula.saenz.pickamovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top + 60,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        binding.textWelcome.text = getString(R.string.welcome_message)
        binding.btnDiscover.text = getString(R.string.discover_movies)
        binding.btnFavorites.text = getString(R.string.view_favorites)
        binding.imgMovie.setImageResource(R.drawable.pickamovie)

        binding.btnDiscover.setOnClickListener {
            val intent = Intent(this, GenresActivity::class.java)
            startActivity(intent)
        }
        binding.btnFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
