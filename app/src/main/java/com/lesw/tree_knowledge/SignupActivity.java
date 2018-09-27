package com.lesw.tree_knowledge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    // @BindView(R.id.input_name) EditText _nameText;
    // @BindView(R.id.role_input) EditText _roleText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_pin) EditText _pinText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;

    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        _signupButton.setEnabled(false);

        if (!validate()) {
            onSignupFailed();
            return;
        }

        String email = _emailText.getText().toString();

        String pin = _pinText.getText().toString();

        final Employee employee = DummyDB.getInstance(getApplicationContext()).findEmployeeByEmail(email);

        if (employee != null) {
            if (employee.isPrimeiroAcesso()) {
                if (pin.equals(employee.getPin())) {
                    String password = _passwordText.getText().toString();

                    employee.setPassword(password);

                    final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                            R.style.AppTheme_Dark_Dialog);

                    progressDialog.setMessage("Liberando conta...");

                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onSignupSuccess or onSignupFailed
                                    // depending on success
                                    onSignupSuccess(employee);
                                    // onSignupFailed();
                                    progressDialog.dismiss();
                                }
                            }, 3000);

                }
                else {
                    onSignupFailed("O PIN informado é inválido.");
                }
            }
            else {
                onSignupFailed("A conta já foi ativada.");
            }
        }
        else {
            onSignupFailed("A conta não foi cadastrada pelo RH.");
        }
    }


    public void onSignupSuccess(Employee employee) {
        setResult(RESULT_OK);

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("EMAIL", employee.getEmail());
        startActivity(intent);

        finish();
    }

    public void onSignupFailed() {
        this.onSignupFailed("Signup failed");
    }

    public void onSignupFailed(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String pin = _pinText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Insira um e-mail válido.");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (pin.length() != 4) {
            _pinText.setError("Insira um PIN com 4 caracteres.");
            valid = false;
        } else {
            _pinText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Entre 4 e 10 caracteres.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Entre 4 e 10 caracteres.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (!reEnterPassword.equals(password)) {
            _reEnterPasswordText.setError("Senhas não conferem.");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}