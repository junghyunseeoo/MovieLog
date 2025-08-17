package ddwu.com.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ddwu.com.mobile.finalreport.MovieDBHelper

class MovieDao(context: Context) {

    private val helper = MovieDBHelper(context)

    fun getAll(): List<Movie> {
        val db: SQLiteDatabase = helper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${MovieDBHelper.TABLE_NAME}", null)
        val movieList = mutableListOf<Movie>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val director = cursor.getString(cursor.getColumnIndexOrThrow("director"))
            val story = cursor.getString(cursor.getColumnIndexOrThrow("story"))
            val releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("releaseDate"))
            val imageName = cursor.getString(cursor.getColumnIndexOrThrow("imageName"))

            val movie = Movie(id, title, director, story, releaseDate, imageName)
            movieList.add(movie)
        }

        cursor.close()
        db.close()

        return movieList
    }

    fun insert(movie: Movie) {
        val db = helper.writableDatabase
        val sql = "INSERT INTO ${MovieDBHelper.TABLE_NAME} (title, director, story, releaseDate, imageName) VALUES (?, ?, ?, ?, ?)"
        val args = arrayOf(movie.title, movie.director, movie.story, movie.releaseDate, movie.imageName)
        db.execSQL(sql, args)
        db.close()
    }

    fun delete(id: Long): Int {
        val db = helper.writableDatabase
        val result = db.delete(MovieDBHelper.TABLE_NAME, "_id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun update(movie: Movie): Int {
        val db = helper.writableDatabase
        val sql = "UPDATE ${MovieDBHelper.TABLE_NAME} SET title = ?, director = ?, story = ?, releaseDate = ?, imageName = ? WHERE _id = ?"
        val args = arrayOf(movie.title, movie.director, movie.story, movie.releaseDate, movie.imageName, movie.id.toString())
        db.execSQL(sql, args)
        db.close()
        return 1
    }
}