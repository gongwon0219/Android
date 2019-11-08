package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextView viewDatePick;
    EditText edtDiary;
    Button btnSave;

    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("로봇고 일기장");

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        viewDatePick = (TextView) findViewById(R.id.viewDatePick);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);

        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        checkedDay(cYear, cMonth, cDay);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                checkedDay(year, monthOfYear, dayOfMonth);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName);
            }
        });
    }

    // 일기 파일 읽기
    private void checkedDay(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;

        viewDatePick.setText(year + " - " + monthOfYear + " - " + dayOfMonth);


        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData, "utf-8");

            Toast.makeText(getApplicationContext(), "일기 써둔 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str);
            btnSave.setText("수정하기");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("1교시:\n2교시:\n3교시:\n4교시:\n5교시:\n6교시:\n7교시:");
            btnSave.setText("새 일기 저장");
            e.printStackTrace();
        }

    }

    private void saveDiary(String readDay) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = edtDiary.getText().toString();

            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(getApplicationContext(), "일기 저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }
    }

}
