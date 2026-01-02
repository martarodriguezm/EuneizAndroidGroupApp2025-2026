package paula.saenz.pickamovie.MovieDetail

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import paula.saenz.pickamovie.MovieResult.Movie
import kotlin.collections.any
import kotlin.collections.filter
import kotlin.collections.none
import kotlin.collections.toMutableList
import kotlin.text.isNullOrEmpty

object FavoritesManager {

    private const val PREFS_NAME = "favorites"
    private const val FAVORITES_KEY = "favorite_movies"
    private val gson = Gson()

    // Obtener lista completa de favoritos
    fun getAllFavorites(context: Context): MutableList<Movie> {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPrefs.getString(FAVORITES_KEY, null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Movie>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    // Comprobar si un Movie está en favoritos
    fun isFavorite(context: Context, movieId: String): Boolean {
        return getAllFavorites(context).any { it.id == movieId }
    }

    // Añadir a favoritos
    fun addFavorite(context: Context, movie: Movie) {
        val favorites = getAllFavorites(context)
        if (favorites.none { it.id == movie.id }) {
            favorites.add(movie)
            saveFavorites(context, favorites)
        }
    }

    // Eliminar de favoritos
    fun removeFavorite(context: Context, movieId: String) {
        val favorites = getAllFavorites(context).filter { it.id != movieId }.toMutableList()
        saveFavorites(context, favorites)
    }

    // Guardar lista en SharedPreferences
    private fun saveFavorites(context: Context, movies: MutableList<Movie>) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit().putString(FAVORITES_KEY, gson.toJson(movies)).apply()
    }
}
