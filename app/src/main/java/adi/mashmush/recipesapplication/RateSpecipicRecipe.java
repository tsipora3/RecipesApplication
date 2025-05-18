package adi.mashmush.recipesapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RateSpecipicRecipe extends AppCompatActivity {
    Recipe recipe;
    HelperDB helperDB;
    TextView RateTV,VleveRate,VmakingTimeRate, VfoodTypeRate,VmakingOrdersRate;
    RatingBar ratingBar;
    Button bGoFromRateR;
    ImageView imageView;
    Intent go;
    Context context;
    User user;
    ActivityResultLauncher<Intent> arlSmall;
    String recipeName, recipeUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_specipic_recipe);
        recipeName = getIntent().getStringExtra("recipeName");
        recipeUser = getIntent().getStringExtra("recipeUser");
        user = (User) getIntent().getSerializableExtra("user");
        initcomp();
        recipe = helperDB.getRecipeByNameAndUser(recipeName,recipeUser);
        RateTV.setText("Rate" + " " + recipe.getRecipeName());
        VleveRate.setText("Recipe Level:" + " " + recipe.getLevel());
        VmakingTimeRate.setText("Recipe making time:" + " " + recipe.getMakingTime());
        VfoodTypeRate.setText("Food type:" + " " + recipe.getFoodType());
        VmakingOrdersRate.setText("Recipe making orders:" + " " + recipe.getRecipeDescription());
        Bitmap bitmap = ImageHelper.base64ToBitmap(recipe.getImageBase64());
        imageView.setImageBitmap(bitmap);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    int newRating = (int) rating;
                    //recipe.addRating(newRating);
                    HelperDB helperDB = new HelperDB(context);
                    helperDB.addRatingToRecipe(recipe,newRating);
                }

                    }
                });
        bGoFromRateR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go=new Intent(RateSpecipicRecipe.this,RateRecipes.class);
                go.putExtra("user",user);
                go.putExtra("recipeName", recipe.getRecipeName());
                startActivity(go);
            }
        });

    }
        private void initcomp () {
            context = this;
            helperDB = new HelperDB(this);
            imageView = findViewById(R.id.RateRecipeImg);
            RateTV = findViewById(R.id.RateTV);
            VleveRate = findViewById(R.id.VleveRate);
            VmakingTimeRate = findViewById(R.id.VmakingTimeRate);
            VfoodTypeRate = findViewById(R.id.VfoodTypeRate);
            VmakingOrdersRate = findViewById(R.id.VmakingOrdersRate);
            bGoFromRateR = findViewById(R.id.bGoFromRateR);
            ratingBar = findViewById(R.id.RatingBar);


        }
    }