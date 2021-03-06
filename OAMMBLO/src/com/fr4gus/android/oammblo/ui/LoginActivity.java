package com.fr4gus.android.oammblo.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.R;
import com.fr4gus.android.oammblo.data.TwitterService;
import com.fr4gus.android.oammblo.util.LogIt;

public class LoginActivity extends OammbloActivity{
    
	TwitterService twitterService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        twitterService = OammbloApp.getInstance().getTwitterService();
    }

    public void authenticate(View view) {
        // Si no existe autenticacion previa
        if (! twitterService.checkForSavedLogin(this) ) {
            LogIt.d(this, "Solicitando autenticacion...");
            twitterService.requestOAuthAccessToken(this);
        } else {
            LogIt.d(this, "Datos de autenticacion previa, existente, iniciando aplicacion con normalidad");
            startActivityByClass(DashboardActivity.class);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        
        if (uri != null) {
            LogIt.d(this, "Posible authentication data received");
            if (twitterService.authorize(this, uri) ) {
                startActivityByClass(DashboardActivity.class);
                finish();
                
            } else {
                // Algun problema para autenticarnos
                toast(R.string.login_authentication_error);
            }
            
        } else {
            // En caso de que no exista el URi (por ejemplo que se 
            // inicie la actividad por primera vez,
            // forzar la autenticacion para verificar que exista
            authenticate(null);
        }
    }
}
