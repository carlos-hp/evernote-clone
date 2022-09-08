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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvargas.evernoteclone.App
import com.cvargas.evernoteclone.Home
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.databinding.ActivityHomeBinding
import com.cvargas.evernoteclone.interactor.HomeInteractor
import com.cvargas.evernoteclone.presenter.HomePresenter
import com.cvargas.evernoteclone.view.adapters.NoteAdapter
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator

class HomeActivity : AppCompatActivity(), Home.View {

    private lateinit var presenter: Home.Presenter
    private lateinit var binding: ActivityHomeBinding

    private val navigator = object : AppNavigator(this, R.id.container) {
         override fun applyCommands(commands: Array<out Command>) {
             for (command in commands) {
                    if (command !is Forward) continue
                    when (command.screen.screenKey) {
                        FormActivity.TAG -> startActivity(
                            Intent(this@HomeActivity, FormActivity::class.java)
                        )
                    }
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HomePresenter(this, HomeInteractor(), App.INSTANCE.router)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.navigatorHolder.removeNavigator()
    }

    private fun setupViews() {
        setSupportActionBar(binding.appBarHome.toolbar)

        setActionBarToggle()

        setNavigationItemSelectListener()

        setRecycleViewLayoutManager()
    }

    fun goToAddActivity(view: View) {
        presenter.addNoteClick()
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
        presenter.onStart()
    }

    override fun displayNotes(notes: List<Note>) {
        binding.appBarHome.contentHome.homeRecyclerView.adapter = NoteAdapter(notes) { note ->
            val intent = Intent(baseContext, FormActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
    }

    override fun displayError(message: String) {
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