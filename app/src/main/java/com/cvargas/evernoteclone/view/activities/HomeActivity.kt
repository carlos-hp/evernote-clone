package com.cvargas.evernoteclone.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.databinding.ActivityHomeBinding
import com.cvargas.evernoteclone.view.adapters.NoteAdapter
import com.cvargas.evernoteclone.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.appBarHome.toolbar)

        setActionBarToggle()

        setNavigationItemSelectListener()

        setRecycleViewLayoutManager()
    }

    fun onClickFloatingActionButton(view: View) {
        val intent = Intent(baseContext, FormActivity::class.java)
        startActivity(intent)
    }

    private fun setRecycleViewLayoutManager() {
        val binding = binding.appBarHome.contentHome
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.homeRecyclerView.addItemDecoration(divider)
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setNavigationItemSelectListener() {
        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setActionBarToggle() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        observeAllNotes()
    }

    private fun observeAllNotes() {
        viewModel.getAllNotes().observe(this) { noteList ->
            if (noteList == null) {
                displayError("Falhou")
                return@observe
            }
            displayNotes(noteList)
        }
    }

    private fun displayNotes(notes: List<Note>) {
        binding.appBarHome.contentHome.homeRecyclerView.adapter = NoteAdapter(notes) { note ->
            val intent = Intent(baseContext, FormActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
    }

    private fun displayError(message: String) {
        showToast(message)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
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