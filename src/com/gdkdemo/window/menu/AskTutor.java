package com.gdkdemo.window.menu;

import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class AskTutor extends Activity{
	private GestureDetector mGestureDetector;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("AskTutor","onCreate() called.");
        Card card = new Card(this);

        card.setText("Tutor is busy! Ask him next time. ");
        setContentView(R.layout.ask_tutor_menu);
        //openOptionsMenu();
        mGestureDetector = createGestureDetector(this);
        

    }
	
	 protected void onResume()
	    {
	    	/*
	        super.onResume();
	        Log.d("MenuDemoActivity","onResume() called.");

	        Bundle extras = getIntent().getExtras();
	        ArrayList<String> voiceResults = null;
	        if(extras != null) {
	            voiceResults = extras.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
	            // ...
	        }
	        */
	    	super.onResume();

	    }
	   @Override
	    public boolean onGenericMotionEvent(MotionEvent event)
	    {
	        if (mGestureDetector != null) {
	            return mGestureDetector.onMotionEvent(event);
	        }
	        return false;
	    }

	    private GestureDetector createGestureDetector(final Context context)
	    {
	        GestureDetector gestureDetector = new GestureDetector(context);
	        //Create a base listener for generic gestures
	        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
	            @Override
	            public boolean onGesture(Gesture gesture) {
	                Log.d("MenuDemoActivity","gesture = " + gesture);
	                if (gesture == Gesture.TAP) {
	                	Intent intent = new Intent(context, MenuDemoActivity.class);
	                    // ???
	                    startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
	                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                            Intent.FLAG_ACTIVITY_SINGLE_TOP));
	                    return true;
	                } else if (gesture == Gesture.TWO_TAP) {
	                	Intent intent = new Intent(context, MenuDemoActivity.class);
	                    // ???
	                    startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
	                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                            Intent.FLAG_ACTIVITY_SINGLE_TOP));
	                    return true;
	                } else if (gesture == Gesture.SWIPE_RIGHT) {
	                	Intent intent = new Intent(context, MenuDemoActivity.class);
	                    // ???
	                    startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
	                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                            Intent.FLAG_ACTIVITY_SINGLE_TOP));
	                    return true;
	                } else if (gesture == Gesture.SWIPE_LEFT) {
	                	Intent intent = new Intent(context, MenuDemoActivity.class);
	                    // ???
	                    startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
	                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                            Intent.FLAG_ACTIVITY_SINGLE_TOP));
	                    return true;
	                }
	                return false;
	            }
	        });
//	        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//	            @Override
//	            public void onFingerCountChanged(int previousCount, int currentCount) {
//	                // do something on finger count changes
//	            }
//	        });
//	        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//	            @Override
//	            public boolean onScroll(float displacement, float delta, float velocity) {
//	                // do something on scrolling
	//
//	                // ????
//	                return false;
//	            }
//	        });
	        return gestureDetector;
	    }
}
