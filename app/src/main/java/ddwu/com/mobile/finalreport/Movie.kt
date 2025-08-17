package ddwu.com.mobile.finalreport

import java.io.Serializable

data class Movie(
    val id: Long,
    val title: String,
    val director: String,
    val story: String,
    val releaseDate: String,
    val imageName: String
): Serializable
