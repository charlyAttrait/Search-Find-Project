package com.iia.activities;

import com.iia.searchandfind.CompassActivity;
import com.iia.searchandfind.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity {
	
//	public final static String BUNDLE_USER = "login";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
    	Bundle bundle = getIntent().getExtras();
    	String name = bundle.getString("passUser");
    	
    	TextView tvName = (TextView) this.findViewById(R.id.user);
    	tvName.setText(name);
		
		Button btnPartager = (Button) this.findViewById(R.id.share);
		Button btnProfil = (Button) this.findViewById(R.id.profil);
		Button btnPoint = (Button) this.findViewById(R.id.point);
		TextView tvOut = (TextView) this.findViewById(R.id.out);
		
		btnPartager.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final Intent MessIntent = new Intent(Intent.ACTION_SEND);
				MessIntent.setType("text/plain");
				MessIntent.putExtra(Intent.EXTRA_TEXT,
									"SearchANDFind".toString());
				startActivity(Intent.createChooser(MessIntent,
													"Partager avec..."));
				}
		});
		
		btnProfil.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(HomeActivity.this,
						ProfilActivity.class);
        		
        		startActivity(intent);
				}
		});
		
		btnPoint.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(HomeActivity.this,
						ListPointActivity.class);
        		
        		startActivity(intent);
				}
		});
		
		tvOut.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// Login / password = null
				Intent intent = new Intent(HomeActivity.this,
						AuthenticationActivity.class);
        		
        		startActivity(intent);
				}
		});
		
		
	}

}
