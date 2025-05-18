package adi.mashmush.recipesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class HelperDB  extends SQLiteOpenHelper {
    public static final String DB_FILE = "all_info.db";
    public static final String USERS_TABLE = "Users";
    public static final String USER_NAME = "UserName";
    public static final String USER_PWD = "UserPassword";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_PHONE = "UserPhone";
    public static final String RECIPES_TABLE = "Recipes";
    public static final String RECIPE_NAME = "RecipeName";
    public static final String RECIPE_USER = "RecipeUser";
    public static final String RECIPE_LEVEL = "RecipeLevel";
    public static final String RECIPE_MAKING_TIME = "RecipeMakingTime";
    public static final String RECIPE_FOOD_TYPE = "RecipeFoodType";
    public static final String RECIPE_DESCRIPTION = "RecipeDescription";
    public static final String RECIPE_AVG_RATE = "avgRate";
    public static final String RECIPE_NUM_RATINGS = "numRatings";
    public static final String RECIPE_SUM_RATINGS = "sumRatings";
    public static final String RECIPES_PICTURE = "Picture";


    public HelperDB(Context context) {
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String st = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE;
        st += " ( " + USER_NAME + " TEXT, ";
        st += USER_PWD + " TEXT, ";
        st += USER_EMAIL + " TEXT, ";
        st += USER_PHONE + " TEXT);";
        db.execSQL(st);
        st = "CREATE TABLE IF NOT EXISTS " + RECIPES_TABLE;
        st += " ( " + RECIPE_NAME + " TEXT, ";
        st += RECIPE_USER + " TEXT, ";
        st += RECIPE_LEVEL + " TEXT, ";
        st += RECIPE_MAKING_TIME + " TEXT, ";
        st += RECIPE_FOOD_TYPE + " TEXT, ";
        st += RECIPES_PICTURE + " TEXT, ";
        st += RECIPE_DESCRIPTION + " TEXT, ";
        st += RECIPE_AVG_RATE + " REAL, ";
        st += RECIPE_SUM_RATINGS + " INTEGER, ";
        st += RECIPE_NUM_RATINGS + " INTEGER);";
        db.execSQL(st);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getUserName());
        cv.put(USER_PWD, user.getPassword());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PHONE, user.getPhone());
        db.insert(USERS_TABLE, null, cv);
        db.close();
    }

    public void insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_USER, recipe.getRecipeUser());
        cv.put(RECIPE_NAME, recipe.getRecipeName());
        cv.put(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        cv.put(RECIPE_LEVEL, recipe.getLevel());
        cv.put(RECIPE_FOOD_TYPE, recipe.getFoodType());
        cv.put(RECIPE_MAKING_TIME, recipe.getMakingTime());
        cv.put(RECIPE_NUM_RATINGS, recipe.getNumRatings());
        cv.put(RECIPE_AVG_RATE, recipe.getAvgRate());
        cv.put(RECIPE_SUM_RATINGS, recipe.getSumRatings());
        cv.put(RECIPES_PICTURE, recipe.getImageBase64());
        db.insert(RECIPES_TABLE, null, cv);
        db.close();
    }

    public User isUserFound(String name, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String s = USER_NAME + "=? AND " + USER_PWD + "=?";
        String[] sA = new String[]{name, password};

        Cursor cursor = sqLiteDatabase.query(USERS_TABLE, null, s, sA, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setUserName(cursor.getString((int) cursor.getColumnIndex(USER_NAME)));
            user.setPassword(cursor.getString((int) cursor.getColumnIndex(USER_PWD)));
            user.setEmail(cursor.getString((int) cursor.getColumnIndex(USER_EMAIL)));
            user.setPhone(cursor.getString((int) cursor.getColumnIndex(USER_PHONE)));
            return user;


        } else return null;
    }

    public boolean isUserNameFound(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String s = USER_NAME + "=?";
        String[] sA = new String[]{name};
        Cursor cursor = sqLiteDatabase.query(USERS_TABLE, null, s, sA, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }

    public boolean isRecipeFoundByUserName(String rName, String uName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String s = RECIPE_NAME + "=? AND " + RECIPE_USER + "=?";
        String[] sA = new String[]{rName, uName};
        Cursor cursor = sqLiteDatabase.query(RECIPES_TABLE, null, s, sA, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }

    public void deleteRecipe(String recipeToDelete, String uName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String s = RECIPE_NAME + "=? AND " + RECIPE_USER + "=?";
        String[] sA = new String[]{recipeToDelete, uName};
        sqLiteDatabase.delete(RECIPES_TABLE, s, sA);
        sqLiteDatabase.close();

    }
    public void updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        cv.put(RECIPE_LEVEL, recipe.getLevel());
        cv.put(RECIPE_FOOD_TYPE, recipe.getFoodType());
        cv.put(RECIPE_MAKING_TIME, recipe.getMakingTime());
        cv.put(RECIPES_PICTURE, recipe.getImageBase64());

        String whereClause = RECIPE_NAME + "=? AND " + RECIPE_USER + "=?";
        String[] whereArgs = {recipe.getRecipeName(), recipe.getRecipeUser()};

        db.update(RECIPES_TABLE, cv, whereClause, whereArgs);
        db.close();
    }


    public ArrayList<Recipe> getMyRecipies(String currentUser) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selection = RECIPE_USER + " = ?";
        String[] selectionArgs = {currentUser};
        Cursor c = sqLiteDatabase.query(RECIPES_TABLE, null, selection, selectionArgs, null,
                null,
                null
        );
        int col1 = c.getColumnIndex(RECIPE_NAME);
        int col2 = c.getColumnIndex(RECIPE_DESCRIPTION);
        int col3 = c.getColumnIndex(RECIPE_LEVEL);
        int col4 = c.getColumnIndex(RECIPE_FOOD_TYPE);
        int col5 = c.getColumnIndex(RECIPE_MAKING_TIME);
        int col6 = c.getColumnIndex(RECIPES_PICTURE);
        int col7 = c.getColumnIndex(RECIPE_USER);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String sRecipeName = c.getString(col1);
                String sRecipeDescription = c.getString(col2);
                String sRecipeLevel = c.getString(col3);
                String sFoodType = c.getString(col4);
                String sRecipeMakingTime = c.getString(col5);
                String image = c.getString(col6);
                String sRecipeUser = c.getString(col7);
                Recipe recipe = new Recipe();
                recipe.setRecipeUser(sRecipeUser);
                recipe.setLevel(sRecipeLevel);
                recipe.setRecipeName(sRecipeName);
                recipe.setMakingTime(sRecipeMakingTime);
                recipe.setFoodType(sFoodType);
                recipe.setRecipeDescription(sRecipeDescription);
                recipe.setImageBase64(image);

                arrayList.add(recipe);
                c.moveToNext();
            }
        }

        c.close();
        sqLiteDatabase.close();
        return arrayList;

    }
    public ArrayList<Recipe> getAllRecipesExceptMyOwn(String currentUser) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selection = RECIPE_USER + " != ?";
        String[] selectionArgs = { currentUser };

        Cursor c = sqLiteDatabase.query(RECIPES_TABLE, null, selection, selectionArgs, null, null, null);

        int col1 = c.getColumnIndex(RECIPE_NAME);
        int col2 = c.getColumnIndex(RECIPE_DESCRIPTION);
        int col3 = c.getColumnIndex(RECIPE_LEVEL);
        int col4 = c.getColumnIndex(RECIPE_FOOD_TYPE);
        int col5 = c.getColumnIndex(RECIPE_MAKING_TIME);
        int col6 = c.getColumnIndex(RECIPES_PICTURE);
        int col7 = c.getColumnIndex(RECIPE_USER);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String sRecipeName = c.getString(col1);
                String sRecipeDescription = c.getString(col2);
                String sRecipeLevel = c.getString(col3);
                String sFoodType = c.getString(col4);
                String sRecipeMakingTime = c.getString(col5);
                String image = c.getString(col6);
                String sRecipeUser = c.getString(col7);

                Recipe recipe = new Recipe();
                recipe.setRecipeUser(sRecipeUser);
                recipe.setLevel(sRecipeLevel);
                recipe.setRecipeName(sRecipeName);
                recipe.setFoodType(sFoodType);
                recipe.setMakingTime(sRecipeMakingTime);
                recipe.setRecipeDescription(sRecipeDescription);
                recipe.setImageBase64(image);
                //Toast.makeText(getContext(), recipe.toString(), Toast.LENGTH_LONG).show();
                arrayList.add(recipe);
                c.moveToNext();
            }
        }

        c.close();
        sqLiteDatabase.close();
        return arrayList;
    }
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RECIPES_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Recipe recipe = new Recipe();  // NEW object for each row

            recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_NAME)));
            recipe.setRecipeUser(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_USER)));
            recipe.setRecipeDescription(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_DESCRIPTION)));
            recipe.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_LEVEL)));
            recipe.setMakingTime(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_MAKING_TIME)));
            recipe.setFoodType(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_FOOD_TYPE)));
            recipe.setImageBase64(cursor.getString(cursor.getColumnIndexOrThrow(RECIPES_PICTURE)));

            // Add ratings fields
            recipe.setNumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_NUM_RATINGS)));
            recipe.setSumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_SUM_RATINGS)));
            recipe.setAvgRate(cursor.getFloat(cursor.getColumnIndexOrThrow(RECIPE_AVG_RATE)));

            recipes.add(recipe);
        }
        cursor.close();
        db.close();
        return recipes;
    }
    public Recipe getRecipeByNameAndUser(String recipeName, String recipeUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = RECIPE_NAME + "=? AND " + RECIPE_USER + "=?";
        String[] selectionArgs = {recipeName, recipeUser};

        Cursor cursor = db.query(RECIPES_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Recipe recipe = new Recipe();  // NEW object for each row

            recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_NAME)));
            recipe.setRecipeUser(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_USER)));
            recipe.setRecipeDescription(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_DESCRIPTION)));
            recipe.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_LEVEL)));
            recipe.setMakingTime(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_MAKING_TIME)));
            recipe.setFoodType(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_FOOD_TYPE)));
            recipe.setImageBase64(cursor.getString(cursor.getColumnIndexOrThrow(RECIPES_PICTURE)));

            // Add ratings fields
            recipe.setNumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_NUM_RATINGS)));
            recipe.setSumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_SUM_RATINGS)));
            recipe.setAvgRate(cursor.getFloat(cursor.getColumnIndexOrThrow(RECIPE_AVG_RATE)));


            cursor.close();
            return recipe;
        }


        db.close();
        return null;
    }


    public ArrayList<Recipe> searchRecipe(String foodType, String makingTime) {
        ArrayList<Recipe> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(RECIPES_TABLE, null, RECIPE_FOOD_TYPE + "=? AND " + RECIPE_MAKING_TIME + "=?", new String[]{foodType, makingTime}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = new Recipe();  // NEW object for each row

            recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_NAME)));
            recipe.setRecipeUser(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_USER)));
            recipe.setRecipeDescription(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_DESCRIPTION)));
            recipe.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_LEVEL)));
            recipe.setMakingTime(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_MAKING_TIME)));
            recipe.setFoodType(cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_FOOD_TYPE)));
            recipe.setImageBase64(cursor.getString(cursor.getColumnIndexOrThrow(RECIPES_PICTURE)));

            // Add ratings fields
            recipe.setNumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_NUM_RATINGS)));
            recipe.setSumRatings(cursor.getInt(cursor.getColumnIndexOrThrow(RECIPE_SUM_RATINGS)));
            recipe.setAvgRate(cursor.getFloat(cursor.getColumnIndexOrThrow(RECIPE_AVG_RATE)));

            arrayList.add(recipe);
            cursor.moveToNext();
        }

            sqLiteDatabase.close();



        return arrayList;
    }
    public void addRatingToRecipe(Recipe recipe, int newRating) {
        recipe.addRating(newRating);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECIPE_NUM_RATINGS, recipe.getNumRatings());
        values.put(RECIPE_SUM_RATINGS, recipe.getSumRatings());
        values.put(RECIPE_AVG_RATE, recipe.getAvgRate());
        String whereClause = RECIPE_NAME + "=? AND " + RECIPE_USER + "=?";
        String[] whereArgs = { recipe.getRecipeName(), recipe.getRecipeUser() };
        db.update(RECIPES_TABLE, values, whereClause, whereArgs);

        db.close();
    }


}
