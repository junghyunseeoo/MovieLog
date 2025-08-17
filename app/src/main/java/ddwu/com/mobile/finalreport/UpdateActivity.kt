package ddwu.com.mobile.finalreport

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var dao: MovieDao
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding 사용
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = MovieDao(this)

        // 전달받은 영화 정보 가져오기
        movie = intent.getSerializableExtra("movie") as? Movie ?: return

        // 기존 영화 정보 표시
        binding.updateTitle.setText(movie.title)
        binding.updateDirector.setText(movie.director)
        binding.updateReleaseDate.setText(movie.releaseDate)
        binding.updateStory.setText(movie.story)

        // 수정 버튼 클릭 시
        binding.btnUpdate.setOnClickListener {
            val updatedMovie = movie.copy(
                title = binding.updateTitle.text.toString(),
                director = binding.updateDirector.text.toString(),
                releaseDate = binding.updateReleaseDate.text.toString(),
                story = binding.updateStory.text.toString()
            )

            val result = dao.update(updatedMovie)
            if (result > 0) {
                Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()
                intent.putExtra("movie", updatedMovie)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show()
            }
        }

        // 취소 버튼 클릭 시
        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}