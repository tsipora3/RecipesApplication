package adi.mashmush.recipesapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewPeoplesRecipies extends MyMenu {
    ArrayList<Recipe> rArrayList;
    ListView viewRecLV;
    InfoAdapter adapter;
    HelperDB helperDB;
    Context context;
    Recipe recipe;
    User user;
    Intent go;
    String recipeName, recipeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_peoples_recipies);
        user = (User) getIntent().getSerializableExtra("user");

        initcomp();
        Toast.makeText(context, user.getUserName(), Toast.LENGTH_SHORT).show();

        viewRecLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    recipe=rArrayList.get(position);
                    go=new Intent(ViewPeoplesRecipies.this, ViewPeoplesSpesificRecipe.class);
                    go.putExtra("recipeName",recipe.getRecipeName());
                    go.putExtra("recipeUser",recipe.getRecipeUser());
                    go.putExtra("user",user);
                    startActivity(go);
            }


        });
    }

    private void initcomp() {
        viewRecLV = findViewById(R.id.viewRecLV);
        context = this;
        helperDB = new HelperDB(this);
        rArrayList = helperDB.getAllRecipes();
        adapter = new InfoAdapter(this, R.layout.list, rArrayList);
        viewRecLV.setAdapter(adapter);
    }
}