package com.push.pushapplication;

import java.net.URI;
import java.net.URISyntaxException;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.unifiedpush.PushConfig;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.Registrations;

import android.app.Application;
import android.widget.Toast;

public class PushApplication extends Application {
													// TODO configure settings for your project
    private final String VARIANT_ID       = "";		// id for Aerogear variant
    private final String SECRET           = "";		// secret key for Aerogear variant
    private final String GCM_SENDER_ID    = "";		// Google project ID
    private final String UNIFIED_PUSH_URL = "";		// URL for Aerogear Unified Push Server
    private final String MY_ALIAS         = "";  	// set alias for mobile client

    private PushRegistrar registration;

    
    // Accessor method for Activities to access the 'PushRegistrar' object
    public PushRegistrar getRegistration() {
        return registration;
    }
     @Override
       public void onCreate() {
           super.onCreate();

           try {

               Registrations registrations = new Registrations();

               PushConfig config = new PushConfig(new URI(UNIFIED_PUSH_URL), GCM_SENDER_ID);
               config.setVariantID(VARIANT_ID);
               config.setSecret(SECRET);
               config.setAlias(MY_ALIAS);

               registration = registrations.push("unifiedpush", config);

               registration.register(getApplicationContext(), new Callback() {
                   @Override
                   public void onSuccess(Object ignore) {
                       Toast.makeText(getApplicationContext(), "Registration Succeeded!",
                               Toast.LENGTH_LONG).show();
                   }

                   @Override
                   public void onFailure(Exception exception) {
                       Toast.makeText(getApplicationContext(), exception.getMessage(),
                               Toast.LENGTH_LONG).show();
                   }
               });

           } catch (URISyntaxException e) {
               throw new RuntimeException(e);
           }
       }
   }