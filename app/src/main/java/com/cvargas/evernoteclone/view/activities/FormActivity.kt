package com.cvargas.evernoteclone.view.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.view.activities.add.Add
import com.cvargas.evernoteclone.view.activities.add.presentation.AddPresenter
import com.cvargas.evernoteclone.data.model.RemoteDataSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
class FormActivity : AppCompatActivity(), Add.View {

    private lateinit var formPresenter: AddPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        setupPresenter()
        setupViews()
    }

    private fun setupPresenter() {
        val noteId = intent.extras?.getInt("noteId")
        val dataSource = RemoteDataSource()
        formPresenter = AddPresenter(noteId = noteId, dataSource = dataSource, view = this)
    }

    override fun onStart() {
        super.onStart()
        formPresenter.getNoteDetail()
    }

    override fun onStop() {
        super.onStop()
        formPresenter.onStopActivity()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        toggleToolbar(R.drawable.ic_arrow_back_black_24dp)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val toSave = if (note_editor.text.toString().isEmpty() && note_title.text.toString()
                        .isEmpty()
                ) {
                    toggleToolbar(R.drawable.ic_arrow_back_black_24dp)
                    false
                } else {
                    toggleToolbar(R.drawable.ic_done_black_24dp)
                    true
                }
                if (toSave)
                    formPresenter.setNoteIsChanged()
            }
        }
        note_title.addTextChangedListener(textWatcher)
        note_editor.addTextChangedListener(textWatcher)
    }

    private fun toggleToolbar(@DrawableRes icon: Int) {
        supportActionBar?.let {
            it.title = null
            val upArrow = ContextCompat.getDrawable(this, icon)
            val colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_ATOP
                )
            upArrow?.colorFilter = colorFilter
            it.setHomeAsUpIndicator(upArrow)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun displayNote(title: String, body: String) {
        note_title.setText(title)
        note_editor.setText(body)
    }

    override fun displayError(customMessage: String) {
        showToast(customMessage)
    }

    override fun returnToHome() {
        finish()
    }

    override fun getBackgroundScheduler(): Scheduler = Schedulers.io()

    override fun getForegroundScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != android.R.id.home)
            return super.onOptionsItemSelected(item)

        if (!formPresenter.isMustBeCreateNote()) {
            returnToHome()
            return true
        }
        formPresenter.createNote(note_title.text.toString(), note_editor.text.toString())
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}