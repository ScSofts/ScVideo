package me.sc.video;

import java.util.Timer;
import java.util.TimerTask;

import com.tencent.smtt.sdk.QbSdk;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class Load extends Activity {


	private void startMain() {
		Intent i = new Intent(Load.this,Main.class);
		startActivity(i);
		this.finish();
	}
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      requestWindowFeature(Window.FEATURE_NO_TITLE);
	      setContentView(R.layout.activity_load);
	      Timer t = new Timer();
	      TimerTask task = new TimerTask(){
	    	  @Override
	    	public void run() {
	    		  QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
	    				
	    				@Override
	    				public void onViewInitFinished(boolean arg0) {
	    					// TODO Auto-generated method stub
	    					Toast.makeText(getApplicationContext(), arg0?"极速模式":"普通模式", Toast.LENGTH_SHORT).show();
	    					startMain();
	    				}
	    				
	    				@Override
	    				public void onCoreInitFinished() {
	    					// TODO Auto-generated method stub
	    					
	    				}
	    			});//初始化x5内核
	    	}
	      };
	      t.schedule(task, 3*1000);
	     
	   }
}
