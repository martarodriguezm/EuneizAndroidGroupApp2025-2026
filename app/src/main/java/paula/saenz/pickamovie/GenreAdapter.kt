package paula.saenz.pickamovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GenreAdapter(
    private val genres: List<String>,
    private val listener: OnGenreClickListener
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    interface OnGenreClickListener {
        fun onGenreClick(genre: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.textGenre.text = genre
        holder.itemView.setOnClickListener {
            listener.onGenreClick(genre)
        }
    }

    override fun getItemCount(): Int = genres.size

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textGenre: TextView = itemView.findViewById(R.id.textGenreItem)
    }
}
