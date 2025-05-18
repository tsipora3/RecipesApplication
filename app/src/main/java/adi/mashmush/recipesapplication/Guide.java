package adi.mashmush.recipesapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;

public class Guide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        TextView textView = findViewById(R.id.usreGuideText);
        InputStream inputStream = getResources().openRawResource(R.raw.user_guide);
        String text = "";
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            text = new String(buffer);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(text);

    }
}