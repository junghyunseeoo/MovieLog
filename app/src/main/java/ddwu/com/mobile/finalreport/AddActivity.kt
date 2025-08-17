package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var dao: MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = MovieDao(this)

        title = "영화 추가"
        // ▶ [추가] 버튼 클릭
        binding.btnAdd.setOnClickListener {
            val title = binding.addTitle.text.toString()
            val director = binding.addDirector.text.toString()
            val releaseDate = binding.addReleaseDate.text.toString()
            val story = binding.addStory.text.toString()

            // 필수 입력 검사: 영화명
            if (title.isBlank()) {
                Toast.makeText(this, "영화명을 입력하세요!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Movie 객체 생성 및 DB에 삽입
            val newMovie = Movie(
                0,  // id는 autoincrement
                title = title,
                director = director,
                releaseDate = releaseDate,
                story = story,
                imageName = "movie_basic"
            )

            dao.insert(newMovie) // MovieDao에 insert() 메서드 있어야 함

            setResult(RESULT_OK) // MainActivity에서 데이터 갱신
            finish()
        }

        // ▶ [취소] 버튼 클릭
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}