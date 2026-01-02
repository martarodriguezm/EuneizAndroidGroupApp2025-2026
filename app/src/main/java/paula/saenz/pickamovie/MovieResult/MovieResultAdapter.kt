package paula.saenz.pickamovie.MovieResult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import paula.saenz.pickamovie.databinding.ItemMovieBinding
import kotlin.let

class MovieResultAdapter(
    private val movies: List<Movie>,
    private val listener: MovieListener
) : RecyclerView.Adapter<MovieResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentMovie: Movie? = null

        init {
            binding.root.setOnClickListener {
                currentMovie?.let { movie ->
                    listener.movieClicked(movie)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            binding.MovieTitle.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}

// Interface para clicks de película
interface MovieListener {
    fun movieClicked(movie: Movie)
}
