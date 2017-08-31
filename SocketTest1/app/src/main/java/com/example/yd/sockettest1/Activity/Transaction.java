package com.example.yd.sockettest1.Activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.http.ResponseHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.yd.sockettest1.Urls.CommonUrl.trans_url;

public class Transaction extends BaseActivity {
    int mYear, mMonth, mDay;
    Button btn1,btn2;
    TextView dateDisplay1;
    Button btnOK;
    EditText tvname;
    EditText tvphone;
    Spinner sptype;
    TextView tvstarttime,tvendtime;
    EditText tvnotes;
    int date_bool;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        btn1 = (Button) findViewById(R.id.button_date);
        btnOK = (Button) findViewById(R.id.button_OK);
        tvname = (EditText) findViewById(R.id.editText_name);
        tvphone = (EditText) findViewById(R.id.editText_phone);
        sptype = (Spinner) findViewById(R.id.spinner_pettype);
        tvstarttime = (TextView) findViewById(R.id.textView_starttime);
        tvnotes = (EditText) findViewById(R.id.editView_notes);
        btn2=(Button)findViewById(R.id.button_end);
        tvendtime=(TextView)findViewById(R.id.textView_endtime);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date_bool=0;
                showDialog(DATE_DIALOG);
            }
        });//选日期

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date_bool=1;
                showDialog(DATE_DIALOG);
            }
        });//选日期

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tvname.getText().toString().equals("") || tvphone.getText().toString().equals("") || sptype.getSelectedItem().toString().equals("")
                        || tvendtime.getText().toString().equals("") || tvstarttime.getText().toString().equals("")
                        || tvnotes.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请完善订单！", Toast.LENGTH_SHORT).show();
                } else {
                    // 生成订单数据
                    CommonRequest request = new CommonRequest();

                        request.addRequestParam("user_id", "2");
                        request.addRequestParam("to_user_id", "2");
                        request.addRequestParam("user_name",tvname.getText().toString());

                        request.addRequestParam("user_phone",tvphone.getText().toString());
                        request.addRequestParam("kinds",sptype.getSelectedItem().toString());
                        request.addRequestParam("end_time", tvstarttime.getText().toString());
                        request.addRequestParam("start_time", tvendtime.getText().toString());
                        request.addRequestParam("notes", tvnotes.getText().toString());

                    sendHttpPostRequest(trans_url, request, new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {

                                Toast.makeText(getApplicationContext(), "订单提交成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Transaction.this, MainActivity.class);
                                startActivity(intent);

                        }

                        @Override
                        public void fail(String failCode, String failMsg) {
                        }
                    }, true);
                }

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() throws ParseException {

        String d=mYear+"-"+mMonth +"-"+mDay;


        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date=df.parse(df.format(new Date()));
        Date dd=df.parse(d);
        if(date_bool==0) {
            tvstarttime.setText(df.format(dd));
        }else {
            tvendtime.setText(df.format(dd));
        }


    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            try {
                display();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


}


