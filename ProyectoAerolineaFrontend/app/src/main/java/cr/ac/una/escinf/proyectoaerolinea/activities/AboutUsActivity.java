package cr.ac.una.escinf.proyectoaerolinea.activities;


import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;


import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.utils.CustomLayout;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        CustomLayout customLayout = (CustomLayout) findViewById(R.id.about_us_layout);
        Picasso.with(this).load(R.drawable.airplane_background).into(customLayout);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutUsActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}
