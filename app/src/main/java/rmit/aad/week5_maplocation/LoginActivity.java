package rmit.aad.week5_maplocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private ActionBar toolBar;
    EditText editTextUserName, editTextPassword;
    ProgressBar progressBar3;
    Button btnLogin;
    TextView createBtn;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolBar = getSupportActionBar();
        toolBar.setTitle("Login/Register");


        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBar3 = findViewById(R.id.progressBar3);
        btnLogin = findViewById(R.id.btnLogin);
        createBtn = findViewById(R.id.createBtn);
        DB = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkemailpassword = DB.checkemailpassword(email,password);
                    if (checkemailpassword==true){
                        Toast.makeText(LoginActivity.this,"Sign In Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        progressBar3.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                        progressBar3.setVisibility(View.INVISIBLE);
                    }
                }




            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }
}