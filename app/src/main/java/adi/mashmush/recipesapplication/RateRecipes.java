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

public class RateRecipes extends MyMenu {
    ArrayList<Recipe> rArrayList;
    ListView viewRateLV;
    InfoAdapter adapter;
    HelperDB helperDB;
    Context context;
    Recipe recipe;
    User user;
    Intent go;
    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_recipes);
        user = (User) getIntent().getSerializableExtra("user");
        initcomp();
        viewRateLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recipe=rArrayList.get(position);
                recipeName=recipe.getRecipeName();
                go=new Intent(RateRecipes.this, RateSpecipicRecipe.class);
                go.putExtra("recipeName",recipeName);
                go.putExtra("recipeUser",recipe.getRecipeUser());
                go.putExtra("user",user);
                startActivity(go);
            }
        });
         }

    private void initcomp() {
        viewRateLV = findViewById(R.id.viewRateLV);
        context = this;
        helperDB = new HelperDB(this);
        rArrayList = helperDB.getAllRecipesExceptMyOwn(user.getUserName());
        Toast.makeText(context,rArrayList.size()+"",Toast.LENGTH_LONG).show();
        adapter = new InfoAdapter(this, R.layout.list, rArrayList);
        viewRateLV.setAdapter(adapter);
    }

}