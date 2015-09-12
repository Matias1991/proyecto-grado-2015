package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity {
	
    UserSession session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
        session = new UserSession(getApplicationContext());

        session.checkLogin();
         
		final Button btnCreateCategory = (Button) findViewById(R.id.btnCreateCategory);
		final Button btnCreateCharge = (Button) findViewById(R.id.btnCreateCharge);
		final Button btnLogout = (Button) findViewById(R.id.btnLogout);
		
		btnCreateCategory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainMenu.this)
				.setTitle("Atencion")
				.setMessage("No existe implementacion aún.")        				
			   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        }
			     })        			    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			}
		});
		
		btnCreateCharge.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainMenu.this)
				.setTitle("Atencion")
				.setMessage("No existe implementacion aún.")        				
			   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        }
			     })        			    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			}
		});
		
		btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                session.logoutUser();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.partner_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
