package cr.ac.una.escinf.proyectoaerolinea.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.una.escinf.proyectoaerolinea.R;

import butterknife.ButterKnife;
import butterknife.BindView;
import cr.ac.una.escinf.proyectoaerolinea.utils.SharedPref;

public class ContactActivity extends BaseActivity {

    @BindView(R.id.contact_email_editText) EditText email;
    @BindView(R.id.comment_editText) EditText comment;

    @BindView(R.id.email_contact_input) TextInputLayout email_input;
    @BindView(R.id.comment_layout) TextInputLayout comment_input;

    @BindView(R.id.send_comment_button) Button send;
    @BindView(R.id.cancel_comment_button) Button cancel;

    private String _email;
    private String _comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        String username = SharedPref.getString(this, getString(R.string.pref_username));

        // si el usuario ya inicio sesion
        if (!username.equals("")) {
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void send() {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "No se pudo enviar la información", Toast.LENGTH_SHORT).show();
            send.setEnabled(true);
            return;
        }

        send.setEnabled(false);

        Toast.makeText(getBaseContext(), "Se ha enviado la información", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ContactActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate() {

        boolean valid = true;

        _email = email.getText().toString();
        _comment = comment.getText().toString();

        // Check for empty comment
        if (_comment.isEmpty()) {
            comment_input.setError(getString(R.string.empty_comment_error));
            comment_input.requestFocus();
            valid = false;
        } else {
            comment_input.setError(null);
        }

        // Check for a valid email
        if (_email.isEmpty()) {
            email_input.setError(getString(R.string.empty_email));
            email_input.requestFocus();
            valid = false;
        } else if (!_email.contains("@")) {
            email_input.setError(getString(R.string.error_incorrect_email));
            email_input.requestFocus();
            valid = false;
        } else {
            email_input.setError(null);
        }

        return valid;

    }
}
