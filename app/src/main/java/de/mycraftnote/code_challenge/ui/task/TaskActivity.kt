package de.mycraftnote.code_challenge.ui.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.mycraftnote.code_challenge.R
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_task)

        setSupportActionBar(toolbar)
    }
}