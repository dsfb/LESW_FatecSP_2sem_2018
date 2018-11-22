package com.lesw.tree_knowledge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalActivity extends AppCompatActivity {

    @BindView(R.id.btnApprove) Button _approveButton;
    @BindView(R.id.btnDisapprove) Button _disapproveButton;
    @BindView(R.id.input_user) EditText _txtUserName;
    @BindView(R.id.input_knowledge) EditText _txtKnowledge;
    @BindView(R.id.input_test_date) EditText _txtDate;
    @BindView(R.id.input_test) EditText _txtCertification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);
        final String knowledge = getIntent().getStringExtra("Knowledge");
        final String username = getIntent().getStringExtra("Username");
        final String date = getIntent().getStringExtra("Date");
        final String certification = getIntent().getStringExtra("Certification");

        _txtUserName.setText(username);
        _txtKnowledge.setText(knowledge);
        _txtDate.setText(date);
        _txtCertification.setText(certification);

        _approveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //public boolean disapproveCertification(String knowledge, String userName, String date, String certification)
                if (DummyDB.getInstance().approveCertificationInCommonCase(knowledge, username, date, certification)) {
                    Toast.makeText(getBaseContext(), "A competência foi aprovada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ApprovalActivity.this, PlaceTreeActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("KNOWLEDGE", knowledge);

                    startActivity(intent);
                }
                finish();
            }
        });

        _disapproveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (DummyDB.getInstance().disapproveCertification(knowledge, username, date, certification)) {
                    Toast.makeText(getBaseContext(), "A competência foi reprovada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Opa! Infelizmente, a competência não foi reprovada com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
