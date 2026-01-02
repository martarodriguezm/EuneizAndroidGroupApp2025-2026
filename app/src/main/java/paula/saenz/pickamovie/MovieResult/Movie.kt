package paula.saenz.pickamovie.MovieResult

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val year: Int,
    val genre: String,
    val stars: Double,
    val image_url: String
) : Parcelable

