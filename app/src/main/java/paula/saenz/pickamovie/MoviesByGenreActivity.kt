package paula.saenz.pickamovie

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MovieByGenreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_by_genre)

        val genre = intent.getStringExtra("GENRE")
        findViewById<TextView>(R.id.textGenre).text = genre
    }
}
