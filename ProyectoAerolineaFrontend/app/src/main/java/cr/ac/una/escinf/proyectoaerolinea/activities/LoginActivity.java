package cr.ac.una.escinf.proyectoaerolinea.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.ButterKnife;
import butterknife.BindView;

import cr.ac.una.escinf.proyectoaerolinea.R;
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

        boolean valid = true;

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

       /* if (!username.isEmpty() && !password.isEmpty()) {
            if (!usuarioDAO.searchUser(username, password)) {
                password_input.setError(getString(R.string.user_not_found));
                username_input.setError(getString(R.string.user_not_found));
                valid = false;
            } else {
                password_input.setError(null);
                username_input.setError(null);
            }
        }*/

        return valid;

    }
}
