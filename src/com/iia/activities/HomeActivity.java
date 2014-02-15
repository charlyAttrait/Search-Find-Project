package com.iia.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.iia.data.Classes.User;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

public class HomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		// get sharedPreferences to get idUser authentified
        SharedPreferences settings = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        // get user with the id save in preferences
        User user = new UserManager(this).GetUserByArgument(settings.getInt("IDUser", 0), "");
        
    	TextView tvName = (TextView) this.findViewById(R.id.user);
    	tvName.setText(user.getPrenom());
		
		Button btnPartager = (Button) this.findViewById(R.id.share);
		Button btnProfil = (Button) this.findViewById(R.id.profil);
		Button btnPoint = (Button) this.findViewById(R.id.point);
		TextView tvOut = (TextView) this.findViewById(R.id.out);
		
		btnPartager.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
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
				Intent intent = new Intent(HomeActivity.this,
						ProfilActivity.class);
        		
        		startActivity(intent);
			}
		});
		
		btnPoint.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeActivity.this,
						ListPointActivity.class);
        		
        		startActivity(intent);
			}
		});
		
		tvOut.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeActivity.this,
						AuthenticationActivity.class);
        		
        		startActivity(intent);
			}
		});
		
	}

}
