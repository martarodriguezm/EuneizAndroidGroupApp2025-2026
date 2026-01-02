package paula.saenz.pickamovie.GenreList

import GenreAdapter
import GenreListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import paula.saenz.pickamovie.MovieResult.MovieResultActivity
import paula.saenz.pickamovie.R
import paula.saenz.pickamovie.databinding.ActivityGenresBinding

class GenresActivity : AppCompatActivity(), GenreListener {

    private lateinit var binding: ActivityGenresBinding
    private lateinit var adapter: GenreAdapter
    private val genreList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        // Título del ActionBar
        supportActionBar?.title = getString(R.string.nav_genre)

        // Inicializar lista de géneros
        genreList.addAll( listOf( "Action", "Comedy", "Drama", "Fantasy", "Biography", "Sci-Fi", "Adventure", "Crime" ) )

        // RecyclerView
        binding.GenreRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.GenreRecyclerView.setHasFixedSize(true)

        adapter = GenreAdapter(genreList, this)
        binding.GenreRecyclerView.adapter = adapter
        binding.tvTitleGenres.text = getString(R.string.title_choose_genre)

        binding.btnDiscover.text = getString(R.string.btn_discover_movies)

        // Botón descubrir
        binding.btnDiscover.setOnClickListener {

            val selectedGenre = adapter.getSelectedGenre()

            if (selectedGenre == null) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_select_genre),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            Log.d("GenresActivity", "Enviar género: $selectedGenre")

            //pasar genero al siguiente activity
            val intent = Intent(this, MovieResultActivity::class.java)
            intent.putExtra("GENRE", selectedGenre)
            startActivity(intent)
        }
    }

    // Click sobre un género
    override fun genreClicked(genre: String) {
        Log.d("GenresActivity", "Género seleccionado: $genre")

        Toast.makeText(
            this,
            getString(R.string.toast_genre_selected, genre),
            Toast.LENGTH_SHORT
        ).show()
    }
}

