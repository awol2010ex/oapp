package com.oapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.oapp.ui.MainActivity;
//应用程序启动类：显示欢迎界面并跳转到主界面
public class AppStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.app_start, null);
		setContentView(view);
		
		//渐变展示启动屏
				AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
				aa.setDuration(2000);
				view.startAnimation(aa);
				aa.setAnimationListener(new AnimationListener()
				{
					@Override
					public void onAnimationEnd(Animation an) {
						redirectTo();
					}
					@Override
					public void onAnimationRepeat(Animation animation) {}
					@Override
					public void onAnimationStart(Animation animation) {}
					
				});
	}

	/**
     * 跳转到...
     */
    private void redirectTo(){        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
