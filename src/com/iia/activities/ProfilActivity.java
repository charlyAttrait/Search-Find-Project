package com.iia.activities;

import com.iia.searchandfind.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ProfilActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
      
        ImageView ivBack = (ImageView) this.findViewById(R.id.back);
        Button btnNewUser = (Button) this.findViewById(R.id.newuser);
        Button btnChangeUser = (Button) this.findViewById(R.id.changeuser);
        Button btnUpdate = (Button) this.findViewById(R.id.update);
        
        
        ivBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
				}
		});
        
        btnNewUser.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ProfilActivity.this,
            			NewUserActivity.class);
            	
            	startActivity(intent);
				}
		});
        
        btnChangeUser.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// Login / Password = null
				Intent intent = new Intent(ProfilActivity.this,
            			AuthenticationActivity.class);
            	
            	startActivity(intent);
				}
		});
 
        btnUpdate.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			// Requête select pour récupérer les infos
			Intent intent = new Intent(ProfilActivity.this,
					NewUserActivity.class);
        	
        	startActivity(intent);
			}
	});
        
	}
}
