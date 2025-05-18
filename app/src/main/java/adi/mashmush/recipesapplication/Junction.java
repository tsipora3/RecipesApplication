package adi.mashmush.recipesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Junction extends MyMenu {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junction);
        user= (User) getIntent().getSerializableExtra("user");
        Toast.makeText(this, user.getUserName(),Toast.LENGTH_LONG).show();
        ImageView buttonCreate=findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent=new Intent(Junction.this, CreateMyRecipe.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });
        ImageView buttonRate=findViewById(R.id.buttonRate);
        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(Junction.this, RateRecipes.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });
        ImageView buttonEditR=findViewById(R.id.buttonEditR);
        buttonEditR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent  intent=new Intent(Junction.this, EditMyRecipe.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });
        ImageView buttonPeopleR=findViewById(R.id.buttonPeopleR);
        buttonPeopleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(Junction.this, ViewPeoplesRecipies.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });
        }
    }
