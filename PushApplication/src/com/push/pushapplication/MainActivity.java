package com.push.pushapplication;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.unifiedpush.MessageHandler;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.Registrations;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements MessageHandler{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    // access the registration object
	    PushRegistrar push = ((PushApplication) getApplication())
	            .getRegistration();  // 1

	    // fire up registration..

	    // The method will attempt to register the device with GCM and the UnifiedPush server
	    push.register(getApplicationContext(), new Callback<Void>() {   
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void onSuccess(Void ignore) {
	            Toast.makeText(MainActivity.this, "Registration Succeeded!", 
	                    Toast.LENGTH_LONG).show();
	        }

	        @Override
	        public void onFailure(Exception exception) {
	            Log.e("MainActivity", exception.getMessage(), exception);  
	        }
	    });
	}

	
	@Override
	protected void onResume() {
	    super.onResume();
	    Registrations.registerMainThreadHandler(this);  
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    Registrations.unregisterMainThreadHandler(this); 
	}

	@Override
	public void onMessage(Context context, Bundle message) {  
	    // display the message contained in the payload
	    TextView text = (TextView) findViewById(R.id.label);
	    text.setText(message.getString("alert"));
	    text.invalidate();
	}

	@Override
	public void onDeleteMessage(Context context, Bundle message) {
	    // handle GoogleCloudMessaging.MESSAGE_TYPE_DELETED
	}

	@Override
	public void onError() {
	    // handle GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
