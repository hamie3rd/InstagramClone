package com.example.instagramclone;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UsersImageFeed extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_image_feed);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);


        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("User Selected");
        setTitle(currentUser + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username", currentUser);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        for (ParseObject parseObject: objects){
                            ParseFile file = (ParseFile)parseObject.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null && data != null){
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ImageView imageView = new ImageView(getApplicationContext());
                                        //imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                        //        ViewGroup.LayoutParams.MATCH_PARENT,
                                        //        ViewGroup.LayoutParams.WRAP_CONTENT
                                        //));
                                        imageView.setImageBitmap(bitmap);
                                        linearLayout.addView(imageView);
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(UsersImageFeed.this, "This feed has no images", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
