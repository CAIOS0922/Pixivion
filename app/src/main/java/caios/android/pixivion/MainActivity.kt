package caios.android.pixivion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import caios.android.kpixiv.activity.AuthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, AuthActivity::class.java))
    }
}