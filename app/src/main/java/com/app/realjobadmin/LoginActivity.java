package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.ROLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.realjobadmin.helper.Session;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail,edPassword;
    Button btnLogin;
    boolean login = false;
    String role = "";
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(LoginActivity.this);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edEmail.getText().toString().trim().equals("admin@gmail.com") && edPassword.getText().toString().trim().equals("123456")){
                    login = true;
                    role = "Admin";


                }else if (edEmail.getText().toString().trim().equals("superadmin@gmail.com") && edPassword.getText().toString().trim().equals("123456")){
                    login = true;
                    role = "Super Admin";

                }
                if (login){
                    signIn();
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void signIn()
    {
        session.setBoolean("is_logged_in",true);
        session.setData(ROLE,role);
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(ROLE,role);
        startActivity(intent);



    }
}