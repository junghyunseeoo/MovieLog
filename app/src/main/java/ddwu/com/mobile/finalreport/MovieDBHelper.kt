package ddwu.com.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 2) {

    companion object {
        const val DB_NAME = "movie.db"
        const val TABLE_NAME = "movie_table"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
        CREATE TABLE $TABLE_NAME (
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT,
            director TEXT,
            story TEXT,
            releaseDate TEXT,
            imageName TEXT 
        )
    """.trimIndent()
        db?.execSQL(createTable)

        db?.execSQL("INSERT INTO $TABLE_NAME (title, director, story, releaseDate, imageName) VALUES ('드래곤 길들이기', '딘 데블로이스', '용과 친구가 되는 소년의 성장 이야기', '2010-05-20', 'movie1')")
        db?.execSQL("INSERT INTO $TABLE_NAME (title, director, story, releaseDate, imageName) VALUES ('크루엘라', '크레이그 질레스피', '악당이 되어가는 디자이너의 이야기', '2021-05-26', 'movie2')")
        db?.execSQL("INSERT INTO $TABLE_NAME (title, director, story, releaseDate, imageName) VALUES ('어벤져스', '조스 웨던', '히어로들이 모여 지구를 구하다', '2012-04-26', 'movie3')")
        db?.execSQL("INSERT INTO $TABLE_NAME (title, director, story, releaseDate, imageName) VALUES ('도둑들', '최동훈', '10인의 도둑들이 펼치는 대규모 작전', '2012-07-25', 'movie4')")
        db?.execSQL("INSERT INTO $TABLE_NAME (title, director, story, releaseDate, imageName) VALUES ('극한직업', '이병헌', '닭집을 차린 마약반 형사들의 코믹 수사극', '2019-01-23', 'movie5')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}