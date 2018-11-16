package com.lesw.tree_knowledge.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lesw.tree_knowledge.R;
import com.lesw.tree_knowledge.dao.DummyDB;
import com.lesw.tree_knowledge.dao.RoomDbManager;
import com.lesw.tree_knowledge.model.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    DummyDB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        RoomDbManager.initialize(getApplicationContext());
        DummyDB.initializeInstance(getApplicationContext());
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        final Employee employee = DummyDB.getInstance().findEmployee(_emailText.getText().toString(),
                _passwordText.getText().toString());

        if (employee != null) {
            progressDialog.setMessage("Carregando dados...");

            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        DummyDB.getInstance().loginEmployee(_emailText.getText().toString());
                        onLoginSuccess(employee);
                        progressDialog.setMessage("Autenticação bem-sucedida!");
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1000);
        } else {
            // Ação de Login falhou!
            progressDialog.setMessage("Usuário ou senha inválidos.");

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // onLoginFailed();
                            progressDialog.dismiss();
                            _loginButton.setEnabled(true);
                        }
                    }, 300);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess(Employee employee) {
        _loginButton.setEnabled(true);

        Intent intent = new Intent(this, MenuActivity.class);

        intent.putExtra("EMAIL", employee.getEmail());

        startActivity(intent);
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Falha na autenticação", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Informe um e-mail válido.");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Senha entre 4 e 10 caracteres.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
