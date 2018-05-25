package cr.ac.una.escinf.proyectoaerolinea.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.adapters.PlaceAutocompleteAdapter;
import cr.ac.una.escinf.proyectoaerolinea.fragments.DatePickerFragment;
import cr.ac.una.escinf.proyectoaerolinea.models.Usuario;
import cr.ac.una.escinf.proyectoaerolinea.utils.SharedPref;

public class MyProfileActivity extends BaseActivity implements
        DatePickerDialog.OnDateSetListener {

    // UI references
    @BindView(R.id.profile_name_editText) EditText name_editText;
    @BindView(R.id.profile_lastname_editText) EditText lastname_editText;
    @BindView(R.id.profile_email_editText) EditText email_editText;
    @BindView(R.id.profile_birthdate_editText) EditText birthdate_editText;
    @BindView(R.id.profile_address_autocomplete) AutoCompleteTextView address_editText;
    @BindView(R.id.profile_cellphone_editText) EditText cellphone_editText;
    @BindView(R.id.profile_phone_editText) EditText phone_editText;
    @BindView(R.id.profile_password_editText) EditText password_editText;

    @BindView(R.id.update_profile_button) Button update_profile_button;
    @BindView(R.id.cancel_profile_button) Button cancel_profile_button;

    @BindView(R.id.name_ed_input_layout) TextInputLayout name_input;
    @BindView(R.id.lastname_ed_input_layout) TextInputLayout lastname_input;
    @BindView(R.id.email_ed_input_layout) TextInputLayout email_input;
    @BindView(R.id.birthdate_ed_input_layout) TextInputLayout birthdate_input;
    @BindView(R.id.address_ed_input_layout) TextInputLayout address_input;
    @BindView(R.id.cellphone_ed_input_layout) TextInputLayout cellphone_input;
    @BindView(R.id.phone_ed_input_layout) TextInputLayout phone_input;
    @BindView(R.id.password_ed_input_layout) TextInputLayout password_input;

    private String password;
    private String phone;
    private String cellphone;
    private String address;
    private String birthdate;
    private String email;
    private String lastname;
    private String name;

    protected GeoDataClient geoDataClient;

    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71, 136));

    private static final String TAG = "MyProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);

        geoDataClient = Places.getGeoDataClient(this, null);

        PlaceAutocompleteAdapter adapter = new PlaceAutocompleteAdapter(this, geoDataClient,
                LAT_LNG_BOUNDS, null);

        address_editText.setAdapter(adapter);

        birthdate_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        cancel_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,
                        MainPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        update_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        execute();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyProfileActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void execute() {
        String username = SharedPref.getString(this, getString(R.string.pref_username));
        StringBuilder URL = new StringBuilder();

        try {
            URL.append(getResources().getString(R.string.base_url))
                    .append("UsuarioServlet?accion=buscar")
                    .append("&usuario=").append(URLEncoder.encode(username, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new GetUserConnection().execute(URL.toString(), "GET");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());

        birthdate_editText.setText(currentDateString);
    }

    public void setInfo(Usuario usuario) {
        name_editText.setText(usuario.getNombre());
        lastname_editText.setText(usuario.getApellidos());
        email_editText.setText(usuario.getCorreo());
        birthdate_editText.setText(usuario.getFechaNacimiento());
        address_editText.setText(usuario.getDireccion());
        cellphone_editText.setText(usuario.getCelular());
        phone_editText.setText(usuario.getTelefono());
        password_editText.setText(usuario.getContrasena());
    }

    public void update() {
        Log.d(TAG, "Update");

        if (!validate()) {
            onUpdateFailed();
            return;
        }

        update_profile_button.setEnabled(false);

        final String username = SharedPref.getString(this, getString(R.string.pref_username));

        final ProgressDialog progressDialog = new ProgressDialog(MyProfileActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Actualizando información...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Usuario usuario = new Usuario(name, lastname, email, birthdate, address,
                                cellphone, phone, username, password);
                        // inserta el usuario en la base de datos

                      /*  int result = usuarioDAO.update(usuario);

                        if (result == 0) {
                            onUpdateFailed();
                        } else {
                            onUpdateSuccess();
                        }*/

                        progressDialog.dismiss();
                    }
                }, 1500);

    }

    public void onUpdateSuccess() {
        Toast.makeText(getBaseContext(), "Update Successful", Toast.LENGTH_SHORT).show();
        update_profile_button.setEnabled(true);
        Intent intent = new Intent(MyProfileActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void onUpdateFailed() {
        Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_SHORT).show();
        update_profile_button.setEnabled(true);
    }

    private boolean validate() {

        boolean valid = true;

        password = password_editText.getText().toString();
        phone = phone_editText.getText().toString();
        cellphone = cellphone_editText.getText().toString();
        address = address_editText.getText().toString();
        birthdate = birthdate_editText.getText().toString();
        email = email_editText.getText().toString();
        lastname = lastname_editText.getText().toString();
        name = name_editText.getText().toString();


        // Check for a valid password
        if (password.isEmpty()) {
            password_input.setError(getString(R.string.empty_password));
            password_input.requestFocus();
            valid = false;
        } else if (password.length() < 4 || password.length() > 16) {
            password_input.setError(getString(R.string.password_length_error));
            password_input.requestFocus();
            valid = false;
        } else {
            password_input.setError(null);
        }

        // Check for a valid phone
        if (phone.isEmpty()) {
            phone_input.setError(getString(R.string.empty_phone));
            phone_input.requestFocus();
            valid = false;
        } else {
            phone_input.setError(null);
        }

        // Check for a valid cellphone
        if (cellphone.isEmpty()) {
            cellphone_input.setError(getString(R.string.empty_cellphone));
            cellphone_input.requestFocus();
            valid = false;
        } else {
            cellphone_input.setError(null);
        }

        // Check for a valid address
        if (address.isEmpty()) {
            address_input.setError(getString(R.string.empty_address));
            address_input.requestFocus();
            valid = false;
        } else {
            address_input.setError(null);
        }

        // Check for a valid birthdate
        if (birthdate.isEmpty()) {
            birthdate_input.setError(getString(R.string.empty_birthdate));
            birthdate_input.requestFocus();
            valid = false;
        } else {
            birthdate_input.setError(null);
        }

        // Check for a valid email
        if (email.isEmpty()) {
            email_input.setError(getString(R.string.empty_email));
            email_input.requestFocus();
            valid = false;
        } else if (!email.contains("@")) {
            email_input.setError(getString(R.string.error_incorrect_email));
            email_input.requestFocus();
            valid = false;
        } else {
            email_input.setError(null);
        }

        // Check for a valid lastname
        if (lastname.isEmpty()) {
            lastname_input.setError(getString(R.string.empty_lastname));
            lastname_input.requestFocus();
            valid = false;
        } else {
            lastname_input.setError(null);
        }

        // Check for a valid name
        if (name.isEmpty()) {
            name_input.setError(getString(R.string.empty_name));
            name_input.requestFocus();
            valid = false;
        } else {
            name_input.setError(null);
        }

        return valid;

    }

    public class GetUserConnection extends AsyncTask<String, String, Usuario> {

        @Override
        protected Usuario doInBackground(String... params) {
            Usuario usuario = null;
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("User-Response", responseString);
                    usuario = parseData(responseString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return usuario;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();

            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

        private Usuario parseData(String jString) {

            Usuario usuario = null;
            try {
                JSONArray items = new JSONArray(jString);
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        String nombre = items.getJSONObject(i).getString("nombre");
                        String apellidos = items.getJSONObject(i).getString("apellidos");
                        String correo = items.getJSONObject(i).getString("correo");
                        String fechaNacimiento = items.getJSONObject(i).getString("fechaNacimiento");
                        String direccion = items.getJSONObject(i).getString("direccion");
                        String celular = items.getJSONObject(i).getString("celular");
                        String telefono = items.getJSONObject(i).getString("telefono");
                        String nomUsuario = items.getJSONObject(i).getString("usuario");
                        String contrasena = items.getJSONObject(i).getString("contrasena");
                        //the value of progress is a placeholder here....
                        usuario = new Usuario(nombre, apellidos, correo, fechaNacimiento, direccion,
                                celular, telefono, nomUsuario, contrasena);
                    }
                }
            } catch (JSONException e) {
                Log.e("User", "unexpected JSON exception", e);
            }

            return usuario;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            setInfo(usuario);
        }
    }
}
