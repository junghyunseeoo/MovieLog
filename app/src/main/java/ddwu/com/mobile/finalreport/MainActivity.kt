package ddwu.com.mobile.finalreport

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: MovieDao
    private lateinit var adapter: MovieAdapter
    private lateinit var movieList: List<Movie>

    // AddActivity 결과 런처
    private val addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            movieList = dao.getAll()
            adapter.updateList(movieList)
        }
    }

    // UpdateActivity 결과 런처
    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            movieList = dao.getAll()
            adapter.updateList(movieList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = MovieDao(this)
        movieList = dao.getAll()

        adapter = MovieAdapter(this, movieList)

        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("movie", movie) // Movie는 Parcelable 또는 Serializable 구현 필요
                updateLauncher.launch(intent)
            }

            override fun onItemLongClick(movie: Movie): Boolean {
                showDeleteDialog(movie)
                return true
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun showDeleteDialog(movie: Movie) {
        AlertDialog.Builder(this)
            .setTitle("삭제 확인")
            .setMessage("${movie.title}을(를) 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                val result = dao.delete(movie.id)
                if (result > 0) {
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    movieList = dao.getAll()
                    adapter.updateList(movieList)
                } else {
                    Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.movie_add -> {
                val intent = Intent(this, AddActivity::class.java)
                addLauncher.launch(intent)
                true
            }
            R.id.dev_info -> {
                val intent = Intent(this, DeveloperActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.app_close -> {
                AlertDialog.Builder(this)
                    .setTitle("앱 종료")
                    .setMessage("정말 종료하시겠습니까?")
                    .setPositiveButton("예") { _, _ -> finish() }
                    .setNegativeButton("아니오", null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}