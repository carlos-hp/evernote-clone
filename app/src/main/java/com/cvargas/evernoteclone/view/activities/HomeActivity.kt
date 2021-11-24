package com.cvargas.evernoteclone.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.view.adapters.NoteAdapter
import com.cvargas.evernoteclone.viewmodel.HomeViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)

        setActionBarToggle()

        setNavigationItemSelectListener()

        setRecycleViewLayoutManager()

        setFloatingActionButtonListener()
    }

    private fun setFloatingActionButtonListener() {
        fab.setOnClickListener {
            //homePresenter.onClickFabButtonListener()
        }
    }

    private fun setRecycleViewLayoutManager() {
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        home_recycler_view.addItemDecoration(divider)
        home_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun setNavigationItemSelectListener() {
        nav_view.setNavigationItemSelectedListener { item ->
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setActionBarToggle() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        observeAllNotes()
    }

    private fun observeAllNotes() {
        viewModel.getAllNotes().observe(this, { noteList ->
            if (noteList == null) {
                displayError("Falhou")
                return@observe
            }
            displayNotes(noteList)
        })
    }

    override fun onStop() {
        super.onStop()
    }

    fun callFormActivity(note: Note?) {
        val intent = Intent(baseContext, FormActivity::class.java)
        note?.let {
            intent.putExtra("noteId", note.id)
        }
        startActivity(intent)
    }

    fun displayEmptyNotes() {
        home_recycler_view.adapter = null
    }

    fun displayNotes(notes: List<Note>) {
        home_recycler_view.adapter = NoteAdapter(notes) { note ->
            //homePresenter.onClickNoteAdapter(note)
        }
    }

    fun displayError(customMessage: String) {
        showToast(customMessage)
    }

    fun getBackgroundScheduler(): Scheduler = Schedulers.io()

    fun getForegroundScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}