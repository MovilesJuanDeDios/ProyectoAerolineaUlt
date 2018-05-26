package cr.ac.una.escinf.proyectoaerolinea.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.BindView;

import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.models.Usuario;
import cr.ac.una.escinf.proyectoaerolinea.utils.SharedPref;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username_editText) EditText login_username_editText;
    @BindView(R.id.login_password_editText) EditText login_password_editText;
    @BindView(R.id.login_button) Button login_button;
    @BindView(R.id.cancel_login_button) Button cancel_login_button;
    @BindView(R.id.register_textView) TextView register_textView;

    @BindView(R.id.username_lg_input_layout) TextInputLayout username_input;
    @BindView(R.id.password_lg_input_layout) TextInputLayout password_input;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    String username;
    String password;

   private boolean valid;

    private StringBuilder URL = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        cancel_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        register_textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        login_button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 1500);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        SharedPref.saveString(this, getString(R.string.pref_username), username);
        Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        login_button.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
        login_button.setEnabled(true);
    }

    private boolean validate() {

        // Store values at the time of the login attempt.
        username = login_username_editText.getText().toString();
        password = login_password_editText.getText().toString();

        valid = true;

        // Check for a valid password
        if (password.isEmpty()) {
            password_input.setError(getString(R.string.empty_password));
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
        } else {
            username_input.setError(null);
        }

        try {
            URL.append(getResources().getString(R.string.base_url))
                    .append("UsuarioServlet?accion=buscar")
                    .append("&usuario=").append(URLEncoder.encode(username, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        GetUserConnection con = new GetUserConnection();
        con.execute(URL.toString(), "GET");

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
                if (!usuario.getContrasena().equals(password)) {
                    username_input.setError("Usuario no encotrado");
                    username_input.requestFocus();
                    valid = false;
                }
            } else {
                username_input.setError(null);
            }
        }
    }
}
