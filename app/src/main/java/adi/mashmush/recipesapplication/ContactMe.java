package adi.mashmush.recipesapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactMe extends AppCompatActivity {
    Context context;
    Button bSendM;
    String stNumber;
    EditText etText;
    String stText;
    Intent go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_me);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                1);
        context = ContactMe.this;
        bSendM = findViewById(R.id.bSendM);
        etText = findViewById(R.id.etText);
        go = new Intent(ContactMe.this, Junction.class);
        bSendM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stText = etText.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("0547834382", null, stText, null, null);
                createDialog();
            }
        });
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This is the message that will be sent. Are you sure?");
        builder.setMessage(etText.getText());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(go);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}