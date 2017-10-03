package com.example.instagramclone;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("b24d318ea30c9b6b389afbb1616eb8641e031f93")
                .clientKey("a90ba49ff5ba5ec6185f85f4d0631ac60a21eb49")
                .server("http://ec2-18-220-213-119.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );


        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
