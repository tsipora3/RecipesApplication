package adi.mashmush.recipesapplication;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String recipeName;
    private String level;
    private String makingTime;
    private String foodType;
    private String recipeDescription;
    private String recipeUser;
    private String imageBase64;
    private float avgRate;
    private int numRatings;
    private int sumRatings;

    public Recipe(String recipeName,String recipeUser, String recipeDescription,String level,String makingTime,String foodType,String imageBase64 ){
        this.recipeName=recipeName;
        this.recipeUser=recipeUser;
        this.recipeDescription=recipeDescription;
        this.makingTime=makingTime;
        this.level=level;
        this.foodType=foodType;
        this.imageBase64 = imageBase64;
        this.numRatings = 0;
        this.avgRate = 0;
        this.sumRatings = 0;



    }

    public Recipe() {

    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", userName='" + recipeUser + '\'' +
                ", level='" + level + '\'' +
                ", makingTime='" + makingTime + '\'' +
                ", foodType='" + foodType + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", avgRate='" + avgRate + '\'' +
                ", sumRatings='" + sumRatings + '\'' +
                ", numRatings='" + numRatings + '\'' +
                ", image=" + imageBase64 +
                '}';
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeUser() {
        return recipeUser;
    }

    public void setRecipeUser(String userName) {
        this.recipeUser = userName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMakingTime() {
        return makingTime;
    }

    public void setMakingTime(String makingTime) {
        this.makingTime = makingTime;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public float getAvgRate() {
        return avgRate;
    }


    public int getNumRatings() {
        return numRatings;
    }


    public int getSumRatings() {
        return  sumRatings;
    }


    public String getImageBase64() {

        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {

        this.imageBase64 = imageBase64;
    }
    public void addRating(int newRating) {
        numRatings++;
        sumRatings += newRating;
        avgRate = (float) sumRatings / numRatings;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public void setSumRatings(int sumRatings) {
        this.sumRatings = sumRatings;
    }
}

