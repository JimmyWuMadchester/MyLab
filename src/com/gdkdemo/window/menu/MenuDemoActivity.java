package com.gdkdemo.window.menu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import com.gdkdemo.window.menu.service.MenuDemoLocalService;
import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.util.ArrayList;
import java.util.List;



// The "main" activity...
public class MenuDemoActivity extends Activity
{

    // For tap event
    private GestureDetector mGestureDetector;
    private List<Card> d;
    private int idx = 0;
    // Service to handle liveCard publishing, etc...
    private boolean mIsBound = false;
    private MenuDemoLocalService menuDemoLocalService;
    
   
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d("MenuDemoActivity","onServiceConnected() called.");
            menuDemoLocalService = ((MenuDemoLocalService.LocalBinder)service).getService();
        }
        public void onServiceDisconnected(ComponentName className) {
            Log.d("MenuDemoActivity","onServiceDisconnected() called.");
            menuDemoLocalService = null;
        }
    };
    private void doBindService()
    {
        bindService(new Intent(this, MenuDemoLocalService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    private void doUnbindService() {
        if (mIsBound) {
            unbindService(serviceConnection);
            mIsBound = false;
        }
    }
    private void doStartService()
    {
        startService(new Intent(this, MenuDemoLocalService.class));
    }
    private void doStopService()
    {
        stopService(new Intent(this, MenuDemoLocalService.class));
    }


    @Override
    protected void onDestroy()
    {
        doUnbindService();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MenuDemoActivity","onCreate() called.");
        createCards();
        //setContentView(R.layout.activity_menudemo);
        setContentView(d.get(idx).toView());

        
        // For gesture handling.
        mGestureDetector = createGestureDetector(this);


        // TBD:
        // Create an asyncTask for publishing liveCard...
        // ????

        // Or,
        // Start MenuDemoIntentService and
        // delegate the liveCard publishing to MenuDemoIntentService.
        // ...

        // (Short-lived) IntentService does not work for us...
//        Intent ggIntent = new Intent(this, MenuDemoIntentService.class);
//        // trigger liveCard publishing ???   --> No, this is done in IntentService.onStart()...
//        ggIntent.putExtra(MenuDemoConstants.EXTRA_KEY_ACTION, MenuDemoConstants.DEMO_SERVICE_START);
//        this.startService(ggIntent);

        // We need a real service.
        // bind does not work. We need to call start() explilicitly...
        // doBindService();
        //doStartService();
        // TBD: We need to call doStopService() when user "closes" the app....
        // ...

    }
    private void createCards() {
    	Card card1 = new Card(this);
        card1.setText("Hello, Me-Lab"); // Main text area
        card1.setFootnote("Awesome Experience"); // Footer
        //View card1View = card1.toView();

        // Display the card we just created
        //setContentView(card1View);


        d = new ArrayList<Card>();
        
        Card card = new Card(this);

        card.setText("  Title\nPH indicator");
        d.add(card);
        
        card = new Card(this);

        card.setText("Equipment List:\nIndicator solution\nCitric acid solution\nToothpicks\nSpot plate\n");
        
        d.add(card);

        card = new Card(this);
        card.setText("Relational background:\nInteraction between H3O+ ions and OH− ions.\nAcidic solutions have a pH below 7 on the pH scale");
        card.setFootnote("-> wiki          -> print");

       

        d.add(card);
        card = new Card(this);

        card.setText("Warning:\nIndicator is flammable");

        d.add(card);
        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.LEFT);

        card.addImage(R.drawable.ic_1);

        card.setText("1.Label your equipment");

        d.add(card);
        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.LEFT);

        card.addImage(R.drawable.ic_2);

        card.setText("2.Make a citric acid solution\nAdd weater 5ml to citric acid\nPick up citric acid");
        d.add(card);
        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.LEFT);

        card.addImage(R.drawable.ic_3);
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.setText("3.Gently swirl until the sodium carbonate dissolves");
        d.add(card);
        
        card = new Card(this);
        card.setText("Glass' question: Why?");
        d.add(card);
        
        
        card = new Card(this);
        card.addImage(R.drawable.ic_4);
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.setText("4.Fill wells with the indicator solution.");
        d.add(card);
        
        card = new Card(this);
        card.addImage(R.drawable.ic_4);
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.setText("5.Add citric acid solution to the well.");
        d.add(card);
        
        card = new Card(this);
        card.addImage(R.drawable.ic_5);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.setFootnote("6.Compare and record.");
        d.add(card);
        
        card = new Card(this);
        card.setText("End\nHappy with that?");
        d.add(card);
	}


    @Override
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

    private GestureDetector createGestureDetector(Context context)
    {
        GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                Log.d("MenuDemoActivity","gesture = " + gesture);
                if (gesture == Gesture.TAP) {
                    // do something on tap
                    Log.i("MenuDemoActivity","Gesture.TAP");
                    handleGestureTap();
                    return true;
                } else if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    Log.i("MenuDemoActivity","Gesture.TWO_TAP");
                    handleGestureTwoTap();
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    // do something on right (forward) swipe
                    Log.i("MenuDemoActivity","Gesture.SWIPE_RIGHT");
                    handleGestureSwipeRight();
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    Log.i("MenuDemoActivity","Gesture.SWIPE_LEFT");
                    handleGestureSwipeLeft();
                    return true;
                }
                return false;
            }
        });
//        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//            @Override
//            public void onFingerCountChanged(int previousCount, int currentCount) {
//                // do something on finger count changes
//            }
//        });
//        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//            @Override
//            public boolean onScroll(float displacement, float delta, float velocity) {
//                // do something on scrolling
//
//                // ????
//                return false;
//            }
//        });
        return gestureDetector;
    }



    private void handleGestureTap()
    {
        Log.d("MenuDemoActivity","handleGestureTap() called.");

        // Terminate the service...
        // and exit the activity as well...
        //doStopService();
        //finish();
        Intent intent = new Intent(this, LiveCardMenuActivity.class);
        // ???
        startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void handleGestureTwoTap()
    {
        Log.d("MenuDemoActivity","handleGestureTwoTap() called.");
        doStopService();
        finish();

    }

    private void handleGestureSwipeRight()
    {
        Log.d("MenuDemoActivity","handleGestureSwipeRight() called.");
        idx += 1;
        if(idx >= d.size())
        {
        	idx = d.size()-1;
        	return;
        }
        setContentView(d.get(idx).toView());
    }

    private void handleGestureSwipeLeft()
    {
        Log.d("MenuDemoActivity","handleGestureSwipeLeft() called.");
        idx -= 1;
        if(idx <0)
        {
        	idx = 0;
        	return ;
        }
        setContentView(d.get(idx).toView());
    }


}
