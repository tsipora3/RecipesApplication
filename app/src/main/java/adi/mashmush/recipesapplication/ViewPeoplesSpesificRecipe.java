package adi.mashmush.recipesapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewPeoplesSpesificRecipe extends AppCompatActivity {
    Recipe recipe;
    String recipeName, recipeUser;
    HelperDB helperDB;
    TextView ViewRTV,VlevelTV,VmakingTimeTV, VfoodTypeTV,VmakingOrdersTV,VnumRatings,VavgRatings;
    Button bGoFromViewR;
    ImageView imageView;
    Bitmap image;
    Intent go;
    Context context;
    User user;
    ActivityResultLauncher<Intent> arlSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_peoples_spesific_recipe);
        recipeName = getIntent().getStringExtra("recipeName");
        recipeUser = getIntent().getStringExtra("recipeUser");
        user= (User) getIntent().getSerializableExtra("user");
        if (recipeName == null || user == null) {
            Toast.makeText(this, "No recipe data", Toast.LENGTH_SHORT).show();
            finish();
            //return;
        }

        else {
            initcomp();

            Toast.makeText(context, user.getUserName() + " " + recipeName, Toast.LENGTH_SHORT).show();
            recipe = helperDB.getRecipeByNameAndUser(recipeName, recipeUser   );

            ViewRTV.setText("View" + " " + recipe.getRecipeName());
            VlevelTV.setText("Recipe Level:" + " " + recipe.getLevel());
            VmakingTimeTV.setText("Recipe making time:" + " " + recipe.getMakingTime());
            VfoodTypeTV.setText("Food type:" + " " + recipe.getFoodType());
            VmakingOrdersTV.setText("Recipe making orders:" + " " + recipe.getRecipeDescription());
            VnumRatings.setText("Recipe number of ratings:" + " " + recipe.getNumRatings());
            VavgRatings.setText("Recipe average of ratings:" + " " + recipe.getAvgRate());
            Bitmap bitmap = ImageHelper.base64ToBitmap(recipe.getImageBase64());
            imageView.setImageBitmap(bitmap);
            bGoFromViewR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go = new Intent(ViewPeoplesSpesificRecipe.this, ViewPeoplesRecipies.class);
                    go.putExtra("user", user);
                    go.putExtra("recipe", recipe);
                    startActivity(go);
                }
            });
        }
    }



    private void initcomp() {
        context = this;
        helperDB = new HelperDB(this);
        imageView = findViewById(R.id.vRecipeImg);
        ViewRTV = findViewById(R.id.ViewRTV);
        VlevelTV = findViewById(R.id.VlevelTV);
        VmakingTimeTV = findViewById(R.id.VmakingTimeTV);
        VfoodTypeTV = findViewById(R.id.VfoodTypeTV);
        VmakingOrdersTV = findViewById(R.id.VmakingOrdersTV);
        bGoFromViewR= findViewById(R.id.bGoFromViewR);
        VnumRatings= findViewById(R.id.VnumRatings);
        VavgRatings= findViewById(R.id.VavgRatings);

    }

}