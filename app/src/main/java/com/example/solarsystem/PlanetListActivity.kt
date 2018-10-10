package com.example.solarsystem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.solarsystem.dummy.Planet
import com.example.solarsystem.dummy.PlanetsDataProvider
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_planet_list.*
import kotlinx.android.synthetic.main.planet_list.*
import kotlinx.android.synthetic.main.planet_list_content.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.warn

class PlanetListActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_list)

        //don't need with kotlin android extensions plugin
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val recyclerView = findViewById<RecyclerView>(R.id.planet_list)
        setupRecyclerView(planet_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        getPlanetsTimeConsuming()
            //simulating long running call using coroutines
//        recyclerView.adapter = PlanetsAdapter(PlanetsDataProvider.ITEMS)
    }

    private fun getPlanetsTimeConsuming() {
        launch(UI) {
            info("starting launch on ${Thread.currentThread().name}")

            val planets: Deferred<List<Planet>> = bg {
                info("starting bg on ${Thread.currentThread().name}")
                Thread.sleep(1_000L)
                PlanetsDataProvider.ITEMS
            }

            planet_list.adapter = PlanetsAdapter(planets.await())

        }
    }

    inner class PlanetsAdapter internal constructor(
            private val values: List<Planet>
    ) : RecyclerView.Adapter<PlanetsAdapter.ViewHolder>() {

        private val clickListener = OnClickListener { view ->
            val item = view.tag as Planet
//            val context = view.context
//            val intent = Intent(context, PlanetDetailActivity::class.java)
//            intent.putExtra(ARG_ITEM_ID, item.id)
//
//            context.startActivity(intent)

            // using Anko logger
            warn { "Clicked on a planet: $item"}

            // use Anko for intents
            // startActivity is extension on Context
            startActivity<PlanetDetailActivity>(ARG_ITEM_ID to item.id)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.planet_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val planet = values[position]
            //just change these names and import layout view after extending LayoutContainer

            holder.planet_name.text = planet.name
            holder.planet_image.setImageResource(planet.imageResourceId)

            holder.itemView.tag = planet
            holder.itemView.setOnClickListener(clickListener)
        }

        override fun getItemCount(): Int = values.size

        //implement LayoutContainer
        //override view
        //don't need to bind these views here
        inner class ViewHolder(override val containerView: View)
            : RecyclerView.ViewHolder(containerView), LayoutContainer {
//            val nameText: TextView = view.findViewById(R.id.planet_name)
//            val imageView: ImageView = view.findViewById(R.id.planet_image)
        }
    }
}
