package adi.mashmush.recipesapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class CreateMyRecipe extends MyMenu {
    HelperDB helperDB;
    EditText rName, rMakingTime, foodType, rDescription;
    SeekBar sBarLevel;
    Button bCamera, bAdd,bGoFromCreate;
    ImageView imageView;
    Bitmap image;
    Intent go;
    Context context;
    Recipe recipe;
    ActivityResultLauncher<Intent> arlSmall;
    ActivityResultLauncher<String> arlFormGallery;
    boolean flag = false;
    String[] info = new String[5];
    Snackbar snackbar;
    CoordinatorLayout coordinator;
    User user;
    private String level="", stUname="", stName="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_recipe);
        initComp();
        arlFormGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    Toast.makeText(CreateMyRecipe.this, "Gallery", Toast.LENGTH_SHORT).show();
                    imageView.setImageURI(result);
                }}});

            arlSmall = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                        }}
            });
        ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        sBarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            level = "easy";
                if(progress==0)
                    level="hard";
                if(progress==1)
                    level="medium";
                if(progress==2)
                    level="easy";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stName=rName.getText().toString();
                stUname= user.getUserName();
                inputInfo();

            }
        });


        bCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(CreateMyRecipe.this);
            }

            private void chooseImage(Context context) {
                final CharSequence[] optionMenu = {"Take photo", "Choose from gallery", "Exit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(optionMenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (optionMenu[i].equals("Take photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            arlSmall.launch(intent);
                        } else if (optionMenu[i].equals("Choose from gallery")) {
                            arlFormGallery.launch("image/*");
                        } else if (optionMenu[i].equals("Exit")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        bGoFromCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(CreateMyRecipe.this, Junction.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
            private void inputInfo(){
                image=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                recipe=new Recipe();
                recipe.setRecipeUser(stUname);
                recipe.setLevel(level);
                recipe.setRecipeDescription(rDescription.getText().toString());
                String imageBase64=ImageHelper.bitmapToBase64(image);
                recipe.setImageBase64(imageBase64);
                recipe.setFoodType(foodType.getText().toString());
                recipe.setRecipeName(stName);
                recipe.setMakingTime(rMakingTime.getText().toString());
                if(!checkRecipeNameByUser()) {
                    helperDB.insertRecipe(recipe);
                    Snackbar snackbar = Snackbar.make(coordinator, "Recipe added", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("UNDO?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helperDB.deleteRecipe(stName, stUname);
                            Toast.makeText(CreateMyRecipe.this, "recipe deleted !", Toast.LENGTH_SHORT).show();
                        }

                    });
                    snackbar.show();
                }
            }
            private boolean checkRecipeNameByUser(){
                String stName=rName.getText().toString();
                String stUname= user.getUserName();
                if(helperDB.isRecipeFoundByUserName(stName,stUname)){
                    Snackbar snackbar = Snackbar.make(coordinator, "You already have a recipe in that name.", Snackbar.LENGTH_INDEFINITE);
                    rName.setBackgroundColor(Color.RED);
                    snackbar.show();
                    return true;
                }
                return false;
            }



        private void initComp() {
            context=this;
            helperDB = new HelperDB(this);
            imageView = findViewById(R.id.recipeImg);
            rName = findViewById(R.id.rName);
            rDescription = findViewById(R.id.rDescirption);
            rMakingTime = findViewById(R.id.rMakingTime);
            foodType = findViewById(R.id.foodType);
            sBarLevel = findViewById(R.id.sBarLevel);
            bCamera = findViewById(R.id.bCamera);
            bAdd = findViewById(R.id.bAdd);
            bGoFromCreate=findViewById(R.id.bGoFromCreate);
            coordinator=findViewById(R.id.coordinator);
            user= (User) getIntent().getSerializableExtra("user");
        }
    }



