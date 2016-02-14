package maw.org.wedding.map.hotel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import maw.org.wedding.R;
import maw.org.wedding.map.MarkerViewModel;

public class HotelInformationActivity extends AppCompatActivity {

    public static final String VIEW_MODEL = "viewModel";

    private MarkerViewModel mViewModel;

    @Bind(R.id.hotel_information_body)
    TextView mHotelInformationBody;

    @Bind(R.id.topImage)
    ImageView mTopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = getIntent().getParcelableExtra(VIEW_MODEL);

        setContentView(R.layout.activity_hotel_information);

        ButterKnife.bind(this);

        if (mViewModel.imageUrls.size() > 0) {
            Picasso.with(this).load(mViewModel.imageUrls.get(0)).into(mTopImage);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mViewModel.title);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mHotelInformationBody.setText(mViewModel.address);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
