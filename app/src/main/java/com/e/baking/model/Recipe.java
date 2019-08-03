package com.e.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e.baking.RecipeDetailsActivity;

import java.util.List;

public class Recipe implements Parcelable {

    private String id;
    private String name;
    private String servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public Recipe(){}

    protected Recipe(Parcel in) {
        id = in.readString();
        name=in.readString();
        servings = in.readString();
        image = in.readString();
        ingredients = in.readArrayList(Recipe.class.getClassLoader());
        steps = in.readArrayList(Recipe.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(servings);
        parcel.writeString(image);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
