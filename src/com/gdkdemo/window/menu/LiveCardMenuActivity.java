package com.gdkdemo.window.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import com.gdkdemo.window.menu.service.MenuDemoLocalService;


// The activity for the livecard menu.
// Ref: https://developers.google.com/glass/develop/gdk/ui/live-card-menus
public class LiveCardMenuActivity extends Activity
{
    @SuppressWarnings("unused")
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
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("LiveCardMenuActivity","onCreate() called.");

        setContentView(R.layout.activity_livecard_menu);
        openOptionsMenu();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("LiveCardMenuActivity","onResume() called.");

        // tbd...
        // ...

        // For live card menu handling
        //openOptionsMenu();

    }



    // Context menu
    // ...


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.d("LiveCardMenuActivity","onCreateOptionsMenu() called.");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menudemo_livecard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("LiveCardMenuActivity","onOptionsItemSelected() called.");

        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.menu_goto_menudemo_main:
                gotoMain();
                return true;
            case R.id.menu_item_ask_menudemo_livecard:
                ask_tutorior();
                return true;
            case R.id.menu_item_search_menudemo_main:
            	wiki();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu)
    {
        Log.d("LiveCardMenuActivity","onOptionsItemSelected() called.");

        // Nothing else to do, closing the activity.
        finish();
    }


    // Opens the "main" (voice launcher) activity.
    // TBD: Does this work?
    // This seems to open the activity "on top of the live card"
    //    (based on the behavior when the activity is dismissed).
    // How to open an activity "on top of the clock screen" ????
    private void gotoMain()
    {
        Intent intent = new Intent(this, MenuDemoActivity.class);
        // ???
        startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    // Stops the service that published the livecard.
    // (TBD: Lifetime of the service and the lifetime of the live card(s) should be decoupled.)
    private void wiki()
    {
        // ???
        doStopService();
    }
    private void ask_tutorior()
    {
        Intent intent = new Intent(this, AskTutor.class);
        // ???
        startActivity(intent.setFlags(    // Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

}
