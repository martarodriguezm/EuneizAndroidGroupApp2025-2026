
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import paula.saenz.pickamovie.GenreList.GenresActivity
import paula.saenz.pickamovie.R
import paula.saenz.pickamovie.databinding.ItemGenreBinding


class GenreAdapter(
    private val genres: ArrayList<String>,
    private val listener: GenresActivity
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    // Posición del item seleccionado
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    val previousPosition = selectedPosition
                    selectedPosition = position

                    // Actualizar visualmente el anterior y el nuevo
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)

                    listener.genreClicked(genres[position])
                }
            }
        }

        fun bind(genre: String, isSelected: Boolean) {
            binding.textGenreItem.text = genre

            if (isSelected) {
                binding.textGenreItem.setBackgroundColor(
                    binding.root.context.getColor(R.color.backgroundColor)
                )
            } else {
                binding.textGenreItem.setBackgroundColor(
                    binding.root.context.getColor(android.R.color.transparent)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = genres.size

    // Permite a la Activity obtener el género seleccionado
    fun getSelectedGenre(): String? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            genres[selectedPosition]
        } else null
    }
}

// Listener para clicks
interface GenreListener {
    fun genreClicked(genre: String)
}
