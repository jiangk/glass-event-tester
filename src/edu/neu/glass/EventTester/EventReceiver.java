package edu.neu.glass.EventTester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


public class EventReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// abort broadcast if possible
		if (isOrderedBroadcast())
    	{
    		abortBroadcast();
    	}
    	else
    	{
    		Log.i("KEVIN", intent.getAction() + "is not an ordered broadcast. Not aborting");
    	}

		// prepare toast to show, so we know we received the event
        String toast = null;
        String intentAction = intent.getAction();
        
        if (intentAction.equalsIgnoreCase("com.google.glass.action.EYE_GESTURE"))
        {
        	String gesture = intent.getStringExtra("gesture");
        	if (gesture.equalsIgnoreCase("wink"))
        	{
        		toast = "I saw you wink";
        	}
        	else
        	{
        		toast = "Some unknown gesture: " + gesture;
        	}
        }
        else if (intentAction.equalsIgnoreCase("com.google.glass.LOG_HEAD_GESTURE"))
        {
        	toast = "You looked up"; 
        }
        else if (intentAction.equalsIgnoreCase("com.google.glass.action.DON_STATE"))
        {
        	toast = "Device on head state changed";
        }
        else if (intentAction.equalsIgnoreCase("android.intent.action.CAMERA_BUTTON"))
        {
        	KeyEvent keyEvent = (KeyEvent)intent.getExtras().get("android.intent.extra.KEY_EVENT");
        	int keyAction = keyEvent.getAction();
        	boolean screenOff = intent.getBooleanExtra("screen_off", false);
        	
        	if (screenOff)
        	{
        		//for some reason the long press doesnt when the screen is off
        		toast = "You pressed the camera button when the screen was off";
        	}
        	else 
        	{
	        	switch (keyAction)
	        	{
	        	case KeyEvent.ACTION_UP:
	        		toast = "You pressed the camera button";
	        		break;
	        	case KeyEvent.ACTION_DOWN:
	        		toast = "You held the camera button";
	        		break;
	    		default:
	    			toast = "You did *something else* with the camera button";
	    			break;
	        	}
        	}
        }
        else if (intentAction.equalsIgnoreCase("com.google.glass.action.LONG_PRESS"))
        {
        	toast = "Touchpad long press";
        }
        else if (intentAction.equalsIgnoreCase("com.google.glass.action.TOUCH_GESTURE"))
        {
        	toast = "Touch gesture";
        }
        
        
        StringBuffer intentExtraContents = new StringBuffer();
        if (intent.getExtras() != null)
        {
	        for (String extraKey : intent.getExtras().keySet())
	        {
	        	Object extra = intent.getExtras().get(extraKey);
	        	intentExtraContents.append(extraKey);
	        	intentExtraContents.append(" : ");
	        	intentExtraContents.append(extra.toString());
	        	intentExtraContents.append("\n");
	        }
        }
        
        Log.i("KEVIN", intentExtraContents.toString());

        
        // display toast message
    	if (toast != null)
    	{
    		Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    	}
        
	}
}
