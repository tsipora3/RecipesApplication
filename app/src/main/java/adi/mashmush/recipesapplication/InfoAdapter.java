package adi.mashmush.recipesapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class InfoAdapter extends ArrayAdapter<Recipe> {
     private Context mcontext;
     private int mresourse;
    String stRname;
    Bitmap image;
    String imageBase64;
    RatingBar viewRatingBar;
    public InfoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Recipe> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresourse=resource;
    }
    private static class ViewHolder{
        TextView rName;
        ImageView img;
        RatingBar viewRatingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        stRname=getItem(position).getRecipeName();
        imageBase64=getItem(position).getImageBase64();
        image=ImageHelper.base64ToBitmap(imageBase64);
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView= inflater.inflate(mresourse,parent,false);
            holder=new ViewHolder();
            holder.rName=convertView.findViewById(R.id.tvRname);
            holder.img=convertView.findViewById(R.id.ivRecipe);
            holder.viewRatingBar=convertView.findViewById(R.id.viewRatingBar);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        holder.rName.setText(stRname);
        holder.img.setImageBitmap(image);
        holder.viewRatingBar.setRating((float)getItem(position).getAvgRate());
        holder.viewRatingBar.setIsIndicator(true);
        return convertView;
    }
}
