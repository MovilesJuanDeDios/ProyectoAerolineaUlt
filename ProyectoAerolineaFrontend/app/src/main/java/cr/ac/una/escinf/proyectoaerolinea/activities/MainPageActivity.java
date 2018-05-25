package cr.ac.una.escinf.proyectoaerolinea.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.adapters.BannerSliderAdapter;
import cr.ac.una.escinf.proyectoaerolinea.utils.PicassoImageLoadingService;
import cr.ac.una.escinf.proyectoaerolinea.utils.SharedPref;
import ss.com.bannerslider.Slider;

import butterknife.ButterKnife;
import butterknife.BindView;


public class MainPageActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Slider slider;
    @BindView(R.id.country_spinner) Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        Slider.init(new PicassoImageLoadingService(this));
        setupViews();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_app_bar_logo);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayShowHomeEnabled(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paises_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void setupViews() {
        slider = findViewById(R.id.banner_slider);
        slider.setAdapter(new BannerSliderAdapter());
        slider.setSelectedSlide(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_bar, menu);

        // si el usuario ya inicio sesion
        if (!SharedPref.getString(this, getString(R.string.pref_username)).equals("")) {
            MenuItem logout = menu.findItem(R.id.icon_logout);
            logout.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.icon_login:
                // si el usuario no ha iniciado sesion
                if (SharedPref.getString(this, getString(R.string.pref_username)).equals("")) {
                    Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainPageActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.icon_logout:
                SharedPref.clear(this);
                recreate();
                Toast.makeText(getBaseContext(), "Logout", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
