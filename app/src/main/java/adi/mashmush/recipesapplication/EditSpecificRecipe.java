package adi.mashmush.recipesapplication;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditSpecificRecipe extends AppCompatActivity {
    Recipe recipe;
    HelperDB helperDB;
    EditText ErMakingTime, EfoodType, ErDescription;
    TextView EditRTV;
    SeekBar sBarLevel;
    Button eBcamera, editRButton;
    ImageView imageView;
    Bitmap image;
    Intent go;
    Context context;
    ActivityResultLauncher<Intent>arlSmall;
    ActivityResultLauncher<String>arlFormGallery;
    String eRlevel = "";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_specific_recipe);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        user= (User) getIntent().getSerializableExtra("user");
        initcomp();
        fillFieldsFromRecipe();
        EditRTV.setText("Edit"+" "+ recipe.getRecipeName());
        arlFormGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                imageView.setImageURI(result);

            }});
            arlSmall = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
            sBarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(progress == 0) eRlevel = "hard";
                    else if(progress == 1) eRlevel = "medium";
                    else eRlevel = "easy";
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            eBcamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)  {chooseImage(EditSpecificRecipe.this);}
                private void chooseImage(Context context) {
                    final CharSequence[] optionMenu = {"Take photo", "Choose from gallery", "Exit"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(optionMenu, (dialog, i) -> {
                        if (optionMenu[i].equals("Take photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            arlSmall.launch(intent);
                        } else if (optionMenu[i].equals("Choose from gallery")) {
                            arlFormGallery.launch("image/*");
                        } else {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

                });


            editRButton.setOnClickListener(v -> {
                updateRecipeFromFields();
                helperDB.updateRecipe(recipe); //
                Toast.makeText(EditSpecificRecipe.this, "Recipe updated!", Toast.LENGTH_SHORT).show();
                go=new Intent(EditSpecificRecipe.this,Junction.class);
                go.putExtra("user",user);
                startActivity(go);
            });
        }
        private void fillFieldsFromRecipe() {
            ErDescription.setText(recipe.getRecipeDescription());
            ErMakingTime.setText(recipe.getMakingTime());
            EfoodType.setText(recipe.getFoodType());
            eRlevel = recipe.getLevel();
            if (eRlevel.equals("hard"))
                sBarLevel.setProgress(0);
            if (eRlevel.equals("medium"))
                sBarLevel.setProgress(1);
            if (eRlevel.equals("easy"))
                sBarLevel.setProgress(2);


            if (recipe.getImageBase64() != null) {
                Bitmap bitmap = ImageHelper.base64ToBitmap(recipe.getImageBase64());
                imageView.setImageBitmap(bitmap);
            }
        }
        private void updateRecipeFromFields() {
            recipe.setRecipeDescription(ErDescription.getText().toString());
            recipe.setMakingTime(ErMakingTime.getText().toString());
            recipe.setFoodType(EfoodType.getText().toString());
            recipe.setLevel(eRlevel);
            image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            recipe.setImageBase64(ImageHelper.bitmapToBase64(image));
            Toast.makeText(context, recipe.toString(),Toast.LENGTH_LONG).show();
        }



        private void initcomp() {
            context = this;
            helperDB = new HelperDB(this);
            imageView = findViewById(R.id.eRecipeImg);
            ErDescription = findViewById(R.id.ErDescirption);
            ErMakingTime = findViewById(R.id.ErMakingTime);
            EfoodType = findViewById(R.id.EfoodType);
            sBarLevel = findViewById(R.id.EsBarLevel);
            eBcamera = findViewById(R.id.eBcamera);
            EditRTV= findViewById(R.id.EditRTV);
            editRButton= findViewById(R.id.editRButton);

        }
    }
