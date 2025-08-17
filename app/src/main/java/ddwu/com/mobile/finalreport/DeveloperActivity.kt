package ddwu.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityDeveloperBinding

class DeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "개발자 소개"

        // 🔙 돌아가기 버튼 클릭 시 액티비티 종료
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}