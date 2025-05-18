package adi.mashmush.recipesapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Opening extends AppCompatActivity {
    Button bOpening;
    Intent go;
    CountDownTimer cdt;
    HelperDB helperDB;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        initcomp();
        bOpening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                Toast.makeText(Opening.this, "Welcome!", Toast.LENGTH_LONG).show();
                startActivity(go);
            }
        });
            cdt = new CountDownTimer(7000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    startActivity(go);
                }
            };
        cdt.start();



    }

    private void initcomp() {
        bOpening=findViewById(R.id.bOpening);
        go=new Intent(this, LogRegHello.class);
        helperDB=new HelperDB(this);
        db=helperDB.getWritableDatabase();
        db.close();
    }

}
