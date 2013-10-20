package edu.neu.glass.EventTester;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	protected boolean interceptEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkInterceptEnabled();
		updateButtonState();
	}
	
	public void toggleEventIntercept(View view) {
		toggleIntercept();
	}
	
	public void updateButtonState()
	{
		ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton1);	
		button.setChecked(interceptEnabled);
	}
	
	public void toggleIntercept() 
	{
		ComponentName componentName = new ComponentName(this, EventReceiver.class);
		if (interceptEnabled)
		{
			Log.i(MainActivity.class.toString(), "Disabling event interceptiong");
			getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		}
		else
		{
			Log.i(MainActivity.class.toString(), "Enabling event interceptiong");
			getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		}
	}
	
	public boolean checkInterceptEnabled() 
	{
		ComponentName componentName = new ComponentName(this, EventReceiver.class);
        int result = getPackageManager().getComponentEnabledSetting(componentName);
        
        switch (result)
        {
        case PackageManager.COMPONENT_ENABLED_STATE_DEFAULT:
        case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
        	interceptEnabled = true;
        	break;
        case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
        	interceptEnabled = false;
        	break;
        default:
        	Log.e(MainActivity.class.toString(), "unknown component enabled state: " + result);
        }
		
		return interceptEnabled;
	}

}
