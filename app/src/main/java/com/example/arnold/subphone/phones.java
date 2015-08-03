package com.example.arnold.subphone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class phones extends AppCompatActivity {

    private EditText numberET;
    private EditText contentET;
    private Button sendSMSB;
    private Button makeCallB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones);

        numberET = new EditText(this);
        contentET = new EditText(this);
        sendSMSB = new Button(this);
        makeCallB = new Button(this);

        contentET = (EditText)findViewById(R.id.sms_content_editText);
        numberET = (EditText)findViewById(R.id.phone_number_editText);
        sendSMSB = (Button) findViewById(R.id.send_sms_button);
        makeCallB = (Button) findViewById(R.id.make_call_button);

        sendSMSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberET.getText().toString();
                String content = contentET.getText().toString();
                send(number, content);
            }
        });
        makeCallB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberET = (EditText)findViewById(R.id.phone_number_editText);
                String number = numberET.getText().toString();
                call(number);
            }
        });
    }

    private void send(String number, String content){
        if(!number.equals("") && !content.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            if(content.length() > 70){
                List<String> subContents = smsManager.divideMessage(content);
                for(String sms: subContents){
                    smsManager.sendTextMessage(number, null, sms, null, null);
                }
            }else{
                smsManager.sendTextMessage(number, null, content, null, null);
            }
        }else{
            Toast.makeText(this, "Phone number and Content cannot be empty", Toast.LENGTH_LONG).show();
        }
    }
    private void call(String number){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
