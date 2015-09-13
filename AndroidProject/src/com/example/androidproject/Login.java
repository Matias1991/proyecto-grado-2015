package com.example.androidproject;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.UserController;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
    AlertDialogManager alert = new AlertDialogManager();
    
    UserSession session;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	     
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        session = new UserSession(getApplicationContext());  
        
		final Button button = (Button) findViewById(R.id.btnLoginApp);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText txtUsername = (EditText) findViewById(R.id.txtUsernameApp);
                EditText txtPassword = (EditText) findViewById(R.id.txtPasswordApp);
            	String username = txtUsername.getText().toString();
            	String password = txtPassword.getText().toString();
               
                if(username.trim().length() > 0 && password.trim().length() > 0){
	            	try {
	            		UserController userController = new UserController();
						VOUser voUser = userController.login(username, password);
						
						session.createUserLoginSession(voUser, password);
						
	                    Intent i = new Intent(getApplicationContext(), MainMenu.class);
	                    startActivity(i);
	                    finish();
						
					} catch (Exception e) {
	
						alert.showAlertDialog(Login.this, "Fallo el ingreso..", e.getMessage());
					}    
            	}
                else
                    alert.showAlertDialog(Login.this, "Fallo el ingreso..", "Porfavor ingrese los campos usuario y clave");
            }
        });
	}
}
