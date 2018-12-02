package com.lesw.tree_knowledge;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

    @BindView(R.id.input_test_date) EditText testDateTxt;
    Calendar myCalendar;


    Button btnSend;
    @BindView(R.id.input_knowledge) EditText _txtKnowledge;
    @BindView(R.id.input_test) EditText _txtCertification;

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_PICTURE = 1;
    public static final int RESULT_OK = -1;

    private String selectedImagePath;

    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        ButterKnife.bind(this, view);

        myCalendar = Calendar.getInstance();

        btnSend = (Button) view.findViewById(R.id.btn_send);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        testDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDateTxt.setError(null);
                new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    DummyDB db = DummyDB.getInstance();

                    Certification ce = new Certification(_txtKnowledge.getText().toString(),
                            db.getLoggedEmployeeName(), testDateTxt.getText().toString(),
                            "PENDENTE", _txtCertification.getText().toString());
                    RoomDbManager.getInstance().insertCertification(ce);
                    Toast.makeText(getActivity(), "Competência adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    testDateTxt.setText("");
                    _txtKnowledge.setText("");
                    _txtCertification.setText("");
                } else {
                    Toast.makeText(getActivity(), "Não foi possivel adicionar a competência! Por favor, informe os dados faltantes!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public boolean validate() {
        boolean valid = true;

        String knowledge = _txtKnowledge.getText().toString();
        String certification = _txtCertification.getText().toString();
        String testDate = testDateTxt.getText().toString();

        if (knowledge.isEmpty()) {
            _txtKnowledge.setError("Informe uma competência!");
            valid = false;
        } else {
            _txtKnowledge.setError(null);
        }

        if (certification.isEmpty()) {
            _txtCertification.setError("Informe uma certificação!");
            valid = false;
        } else {
            _txtCertification.setError(null);
        }

        if (testDate.isEmpty()) {
            testDateTxt.setError("Informe uma certificação!");
            valid = false;
        } else {
            testDateTxt.setError(null);
        }

        return valid;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        testDateTxt.setText(sdf.format(myCalendar.getTime()));
    }

}
