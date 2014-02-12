package com.iia.activities;

import com.iia.data.CLASS.Item;
import com.iia.data.CLASS.User;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;
import com.iia.searchandfind.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ListPointActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpoint);
     
        Button btnNew = (Button) this.findViewById(R.id.newpoint);
        ImageView ivBack = (ImageView) this.findViewById(R.id.back);
        ListView lvPoint = (ListView) this.findViewById(R.id.listpoint);
        
       /* int idUser = 1;
        User user = new User();
        
        UserManager.GetUsers();
        ItemManager.GetItems(idUser);
        
        */
        
        
        
      
        btnNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ListPointActivity.this,
						NewPointActivity.class);
        		
        		startActivity(intent);
				}
		});
        
        ivBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
				}
		});
	}
}
