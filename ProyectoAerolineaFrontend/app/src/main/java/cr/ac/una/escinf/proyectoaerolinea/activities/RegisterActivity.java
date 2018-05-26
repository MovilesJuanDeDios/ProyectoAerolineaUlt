package cr.ac.una.escinf.proyectoaerolinea.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.adapters.PlaceAutocompleteAdapter;
import cr.ac.una.escinf.proyectoaerolinea.fragments.DatePickerFragment;


import butterknife.ButterKnife;
import butterknife.BindView;


import cr.ac.una.escinf.proyectoaerolinea.models.Usuario;
import cr.ac.una.escinf.proyectoaerolinea.utils.SharedPref;


public class RegisterActivity extends BaseActivity
        implements DatePickerDialog.OnDateSetListener {

    // UI references
    @BindView(R.id.register_name_editText) EditText name_editText;
    @BindView(R.id.register_lastname_editText) EditText lastname_editText;
    @BindView(R.id.registrer_email_editText) EditText email_editText;
    @BindView(R.id.register_birthdate_editText) EditText birthdate_editText;
    @BindView(R.id.register_address_autocomplete) AutoCompleteTextView address_editText;
    @BindView(R.id.register_cellphone_editText) EditText cellphone_editText;
    @BindView(R.id.register_phone_editText) EditText phone_editText;
    @BindView(R.id.register_username_editText) EditText username_editText;
    @BindView(R.id.register_password_editText) EditText password_editText;
    @BindView(R.id.confirm_password_editText) EditText confPassword_editText;

    @BindView(R.id.create_account_button) Button create_account_button;
    @BindView(R.id.cancel_register_button) Button cancel_register_button;

    @BindView(R.id.name_rg_input_layout) TextInputLayout name_input;
    @BindView(R.id.lastname_rg_input_layout) TextInputLayout lastname_input;
    @BindView(R.id.email_rg_input_layout) TextInputLayout email_input;
    @BindView(R.id.birthdate_rg_input_layout) TextInputLayout birthdate_input;
    @BindView(R.id.address_rg_input_layout) TextInputLayout address_input;
    @BindView(R.id.cellphone_rg_input_layout) TextInputLayout cellphone_input;
    @BindView(R.id.phone_rg_input_layout) TextInputLayout phone_input;
    @BindView(R.id.username_rg_input_layout) TextInputLayout username_input;
    @BindView(R.id.password_rg_input_layout) TextInputLayout password_input;
    @BindView(R.id.confirm_password_layout) TextInputLayout confPassword_input;

    private String confPassword;
    private String password;
    private String username;
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

    private static final String TAG = "RegisterActivity";

    private StringBuilder URL = new StringBuilder();

    private boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        cancel_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,
                        MainPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

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

    public void register() {
        Log.d(TAG, "Register");

        if (!validate()) {
            onRegisterFailed();
            return;
        }

        create_account_button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registrando datos...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // inserta el usuario en la base de datos

                        try {
                            URL.append(getResources().getString(R.string.base_url))
                                    .append("UsuarioServlet?accion=insertar")
                                    .append("&nombre=").append(URLEncoder.encode(name, "UTF-8"))
                                    .append("&apellidos=").append(URLEncoder.encode(lastname, "UTF-8"))
                                    .append("&correo=").append(URLEncoder.encode(email, "UTF-8"))
                                    .append("&fechaNacimiento=").append(URLEncoder.encode(birthdate, "UTF-8"))
                                    .append("&direccion=").append(URLEncoder.encode(address, "UTF-8"))
                                    .append("&celular=").append(URLEncoder.encode(cellphone, "UTF-8"))
                                    .append("&telefono=").append(URLEncoder.encode(phone, "UTF-8"))
                                    .append("&usuario=").append(URLEncoder.encode(username, "UTF-8"))
                                    .append("&contrasena=").append(URLEncoder.encode(password, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        PostUserConnection con = new PostUserConnection();
                        con.execute(URL.toString(), "POST");

                        progressDialog.dismiss();
                    }
                }, 1500);

    }

    public void onRegisterSuccess() {
        // guarda el nombre de usuario en el sharedpreferece
        SharedPref.saveString(this, getString(R.string.pref_username), username);

        Toast.makeText(getBaseContext(), "Register Successful", Toast.LENGTH_SHORT).show();
        create_account_button.setEnabled(true);
        Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRegisterFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_SHORT).show();
        create_account_button.setEnabled(true);
    }

    private boolean validate() {

        confPassword = confPassword_editText.getText().toString();
        password = password_editText.getText().toString();
        username = username_editText.getText().toString();
        phone = phone_editText.getText().toString();
        cellphone = cellphone_editText.getText().toString();
        address = address_editText.getText().toString();
        birthdate = birthdate_editText.getText().toString();
        email = email_editText.getText().toString();
        lastname = lastname_editText.getText().toString();
        name = name_editText.getText().toString();

        valid = true;

        // Check for a valid password confirm
        if (confPassword.isEmpty()) {
            confPassword_input.setError(getString(R.string.conf_password));
            confPassword_input.requestFocus();
            valid = false;
        } else if(!password.isEmpty() && !confPassword.equals(password)) {
            confPassword_input.setError(getString(R.string.dif_password));
            confPassword_input.requestFocus();
            valid = false;
        } else {
            confPassword_input.setError(null);
        }

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

        // Check for a valid username
        if (username.isEmpty()) {
            username_input.setError(getString(R.string.empty_username));
            username_input.requestFocus();
            valid = false;
        } else if (username.length() < 4 || username.length() > 16) {
            username_input.setError(getString(R.string.password_length_error));
            username_input.requestFocus();
            valid = false;
        } else {
            try {
                URL.append(getResources().getString(R.string.base_url))
                        .append("UsuarioServlet?accion=buscar")
                        .append("&usuario=").append(URLEncoder.encode(username, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            GetUserConnection con = new GetUserConnection();
            con.execute(URL.toString(), "GET");
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
                JSONObject items = new JSONObject(jString);
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        String nombre = items.getString("nombre");
                        String apellidos = items.getString("apellidos");
                        String correo = items.getString("correo");
                        String fechaNacimiento = items.getString("fechaNacimiento");
                        String direccion = items.getString("direccion");
                        String celular = items.getString("celular");
                        String telefono = items.getString("telefono");
                        String nomUsuario = items.getString("usuario");
                        String contrasena = items.getString("contrasena");
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
            if (usuario != null) {
                username_input.setError("Ese usuario ya existe");
                username_input.requestFocus();
                valid = false;
            } else {
                username_input.setError(null);
            }
        }
    }

    public class PostUserConnection extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            boolean result = true;

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.v("User-Response", "Usuario guardado exitosamente");
                }

            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                onRegisterFailed();
            } else {
                onRegisterSuccess();
            }
        }

    }

}
