package maw.org.wedding.map.hotel

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotterknife.bindView
import maw.org.wedding.R
import maw.org.wedding.map.MarkerViewModel

class HotelInformationActivity : AppCompatActivity() {

    private var mViewModel: MarkerViewModel? = null

    val mHotelInformationBody: TextView by bindView(R.id.hotel_information_body)

    val mTopImage: ImageView by bindView(R.id.topImage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = intent.getParcelableExtra<MarkerViewModel>(VIEW_MODEL)

        setContentView(R.layout.activity_hotel_information)

        if (mViewModel!!.imageUrls!!.size > 0) {
            Picasso.with(this).load(mViewModel!!.imageUrls!![0]).into(mTopImage)
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = mViewModel!!.title

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }

        mHotelInformationBody.text = mViewModel!!.address
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val VIEW_MODEL = "viewModel"
    }
}
