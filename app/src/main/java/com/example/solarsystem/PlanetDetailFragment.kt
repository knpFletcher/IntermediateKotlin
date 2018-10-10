package com.example.solarsystem

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.solarsystem.dummy.Planet
import com.example.solarsystem.dummy.PlanetsDataProvider
import com.example.solarsystem.dummy.composition
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.ctx

const val ARG_ITEM_ID = "item_id"

const val TERRESTRIAL = "Terrestrial"
const val GAS_GIANT = "Gas Giant"

class PlanetDetailFragment : Fragment() {

    private val ui by lazy { PlanetDetailUi() }

    //remove views when using Anko layout component implementation
//    private lateinit var planetDescription: TextView
//    private lateinit var planetComposition: TextView
//    private lateinit var planetMoons: TextView
//    private lateinit var planetOrbit: TextView

    private var planet: Planet? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.planet_detail, container, false)

        with(view) {
        }

        if (arguments.containsKey(ARG_ITEM_ID)) {
            planet = PlanetsDataProvider.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            planet?.let {
                val activity = this.activity
                val appBarLayout = activity.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
                appBarLayout.title = it.name

                val image = activity.findViewById<ImageView>(R.id.image)
                image.setImageResource(it.imageResourceId)
            }
        }

//        return view
        return ui.createView(AnkoContext.Companion.create(ctx, this))
    }

    override fun onResume() {
        super.onResume()

        planet?.let {
            ui.planetDescription.text = it.description
//            planetComposition.text = if (it.inner) TERRESTRIAL else GAS_GIANT
            ui.planetComposition.text = it.composition
            ui.planetMoons.text = getString(R.string.num_known_moons, it.knownMoons)
            ui.planetOrbit.text = getString(R.string.orbital_period_years, it.orbitalPeriod)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        planet?.let {
            arguments.putString(ARG_ITEM_ID, it.id)
        }
    }

    fun goToSpaceWebsite() {
        //anko extension
        browse("http://www.space.com")
    }

}
