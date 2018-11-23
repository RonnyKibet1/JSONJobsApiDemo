package com.ronsoftstudios.governmentjobs.App;

import android.app.Application;


/**
 * Created by Ronsoft on 11/9/2017.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


}
