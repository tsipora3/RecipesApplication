package adi.mashmush.recipesapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EditMyRecipe extends MyMenu {
    ArrayList<Recipe> rArrayList;
    ListView rLV;
    InfoAdapter adapter;
    HelperDB helperDB;
    Context context;
    Recipe recipe;
    User user;
    Intent go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_recipe);
        user= (User) getIntent().getSerializableExtra("user");
        initcomp();


        rLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recipe=rArrayList.get(position);
                go=new Intent(EditMyRecipe.this, EditSpecificRecipe.class);
                go.putExtra("recipe",recipe);
                go.putExtra("user",user);

                startActivity(go);
            }


        });
    }

    private void initcomp() {
        rLV=findViewById(R.id.rLV);
        context=this;
        helperDB = new HelperDB(this);

        rArrayList=helperDB.getMyRecipies( user.getUserName());
        adapter= new InfoAdapter(this,R.layout.list,rArrayList);
        rLV.setAdapter(adapter);
    }
}