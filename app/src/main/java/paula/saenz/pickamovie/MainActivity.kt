package paula.saenz.pickamovie

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import paula.saenz.pickamovie.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textWelcome.text = "¡Bienvenido a PickAMovie!"

        binding.image.setImageResource(R.drawable.pickamovie)

        binding.botonDiscover.setOnClickListener {
            val intent = Intent(this, DiscoverMoviesActivity::class.java)
            startActivity(intent)
        }

        binding.botonFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
