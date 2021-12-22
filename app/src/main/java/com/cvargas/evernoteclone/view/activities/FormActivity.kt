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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.databinding.ActivityFormBinding
import com.cvargas.evernoteclone.viewmodel.FormViewModel
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*


class FormActivity : AppCompatActivity() {

    private lateinit var viewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityFormBinding>(this, R.layout.activity_form)

        viewModel = ViewModelProvider(this)[FormViewModel::class.java]
        binding.viewModel = viewModel

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        intent.extras?.getInt("noteId")?.let { noteId ->
            viewModel.setNoteId(noteId)
            observeGetNote(noteId)
        }
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        viewModel.saved.observe(this, Observer { (saved, message) ->
            if (saved)
                finish()
            else
                displayError(message)
        })
    }

    private fun observeGetNote(noteId: Int) {
        viewModel.getNote(noteId).observe(this) { note ->
            if (note == null) {
                displayError("Falhou")
                return@observe
            }
            displayNote(note.title ?: "", note.body ?: "")
        }
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
                    viewModel.setNoteIsChanged()
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

    private fun displayNote(title: String, body: String) {
        note_title.setText(title)
        note_editor.setText(body)
    }

    private fun displayError(message: String) {
        showToast(message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != android.R.id.home)
            return super.onOptionsItemSelected(item)

        if (!viewModel.isMustBeCreateNote()) {
            finish()
            return true
        }
        viewModel.createNote()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}