package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private val dialogTimePicker = TimePickerFragment()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = ListViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        findViewById<ImageButton>(R.id.ib_start_time).setOnClickListener {
            dialogTimePicker.show(supportFragmentManager, START_TIME_PICKER)
        }

        findViewById<ImageButton>(R.id.ib_end_time).setOnClickListener {
            dialogTimePicker.show(supportFragmentManager, END_TIME_PICKER)
        }

        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true)
                onBackPressed()
            else {
                Toast.makeText(this, R.string.input_empty_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == START_TIME_PICKER) {
            findViewById<TextView>(R.id.tv_start_time).text = dateFormat.format(calendar.time)
        } else {
            findViewById<TextView>(R.id.tv_end_time).text = dateFormat.format(calendar.time)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val courseName = findViewById<TextInputEditText>(R.id.ed_course_name).text?.trim().toString()
        val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition

        val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString()
        val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString()

        val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text?.trim().toString()
        val note = findViewById<TextInputEditText>(R.id.ed_note).text?.trim().toString()

        viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
        finish()
    }

    companion object {
        const val START_TIME_PICKER = "START_TIME_PICKER"
        const val END_TIME_PICKER = "END_TIME_PICKER"
    }
}