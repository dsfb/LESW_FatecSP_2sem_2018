package com.lesw.tree_knowledge;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HRSignupFragment extends Fragment {

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_role) EditText _roleText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_pin) EditText _pinText;
    // @BindView(R.id.input_password) EditText _passwordText;
    // @BindView(R.id.input_password_confirmation) EditText _passwordConfirmationText;
    @BindView(R.id.btn_hrsignup) Button _signupButton;

    public HRSignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hrsignup, container, false);

        ButterKnife.bind(this, v);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        return v;
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String role = _roleText.getText().toString();
        String email = _emailText.getText().toString();
        String pin = _pinText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (role.isEmpty()) {
            _roleText.setError("Enter Valid Role");
            valid = false;
        } else {
            _roleText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        /*
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Entre 4 e 10 caracteres.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (!confirmationPassword.equals(password)) {
            _passwordConfirmationText.setError("Senhas não conferem.");
            valid = false;
        } else {
            _passwordConfirmationText.setError(null);
        }
        */

        if (pin.isEmpty() || pin.length() < 4 || pin.length() > 10) {
            _pinText.setError("between 4 and 10 numeric characters");
            valid = false;
        } else {
            _pinText.setError(null);
        }

        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Signup failed!", Toast.LENGTH_LONG).show();
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando conta...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String role = _roleText.getText().toString();
        String email = _emailText.getText().toString();
        // String password = _passwordText.getText().toString();
        String pin = _pinText.getText().toString();

        Employee employee = new Employee();

        employee.setName(name);
        employee.setRole(role);
        employee.setEmail(email);
        employee.setPassword(null);
        employee.setPin(pin);
        employee.setFunction(RoleEnum.USER);

        DummyDB.getInstance(getContext()).addEmployee(employee);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        Toast.makeText(getActivity(), "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                        _nameText.setText("");
                        _roleText.setText("");
                        _emailText.setText("");
                        _pinText.setText("");
                        progressDialog.dismiss();

                        SearchFragment.getInstance().refreshEmployeeList();
                        SearchFragment.getInstance().handleMyView();
                    }
                }, 3000);
    }

}
