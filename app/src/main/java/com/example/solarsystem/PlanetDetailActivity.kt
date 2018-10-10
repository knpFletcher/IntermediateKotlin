package com.example.solarsystem

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.solarsystem.R.layout.activity_planet_detail
import kotlinx.android.synthetic.main.activity_planet_detail.*
import kotlinx.android.synthetic.main.activity_planet_list.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.withArguments
import org.jetbrains.anko.toast

class PlanetDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_planet_detail)
        setContentView(activity_planet_detail)

//        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)

//        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {view ->
            //original
            // Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
            // .setAction("Action", null).show()

            //made into extension function
            // view.showSnackbar("My message")

            //anko also handles snackbar with design library
            snackbar(view, "My message", "Action") {
                // use an action inside snackbar!
                toast("inside the action")
            }
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            //use withArguments() instead of calling Bundle directly

//            val arguments = Bundle()
//            arguments.putString(ARG_ITEM_ID, intent.getStringExtra(ARG_ITEM_ID))

            val fragment = PlanetDetailFragment()
                    .withArguments(ARG_ITEM_ID to intent.getStringExtra(ARG_ITEM_ID))
//            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.planet_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, PlanetListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
