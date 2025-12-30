package paula.saenz.pickamovie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GenresActivity : AppCompatActivity(),
    GenreAdapter.OnGenreClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        val genres = listOf(
            "Action",
            "Comedy",
            "Drama",
            "Fantasy",
            "Biography",
            "Sci-Fi",
            "Adventure",
            "Crime"
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGenres)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GenreAdapter(genres, this)
    }

    override fun onGenreClick(genre: String) {
        val intent = Intent(this, MovieByGenreActivity::class.java)
        intent.putExtra("GENRE", genre)
        startActivity(intent)
    }
}
