package com.fr4gus.android.oammblo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fr4gus.android.oammblo.R;

public class LoginActivity extends Activity{
    
	Button btnLogin = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initComponents();
    }
	
	private void initComponents(){
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(onBtnLoginClick);
	}
	
	OnClickListener onBtnLoginClick = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
			startActivity(intent);
		}
	};
}
