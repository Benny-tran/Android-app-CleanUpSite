package rmit.aad.week5_maplocation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity {

    TextView createTxt;
    EditText tEmail, tPassword, trePassword;
    Button buttonAdd;
    private ActionBar toolBar;
    private Cursor cursor;
    DatabaseHelper DB;
    CheckBox cxMale, cxFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tEmail = findViewById(R.id.tEmail);
        tPassword = findViewById(R.id.tPassword);
        trePassword = findViewById(R.id.tRePassword);
        createTxt = findViewById(R.id.createTxt);
        buttonAdd = findViewById(R.id.buttonAdd);
        toolBar = getSupportActionBar();
        toolBar.setTitle("Register Form");
        DB = new DatabaseHelper(this);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tEmail.getText().toString();
                String password = tPassword.getText().toString();
                String repassword = trePassword.getText().toString();
                cxMale = findViewById(R.id.cxMale);
                cxFemale = findViewById(R.id.cxFemale);

                StringBuilder sb = new StringBuilder();
                if (cxMale.isChecked()){
                    sb.append("Male");
                }
                if (cxFemale.isChecked()){
                    sb.append("Female");
                }

                if (email.equals("")||password.equals("")||repassword.equals(""))
                    Toast.makeText(SignUpActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(repassword)){
                        Boolean checkemail= DB.checkemail(email);
                        if (checkemail==false){
                            Boolean insert = DB.insertData(email,password);
                            if (insert==true){
                                Toast.makeText(SignUpActivity.this,"Registered Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }else {
                                Toast.makeText(SignUpActivity.this,"Registered Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"User Already exists",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUpActivity.this,"Password not match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(SignUpActivity.this, "Successful move to Login page", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onCheckboxClicked(View view){
        CheckBox checkBox = (CheckBox)view;

        if(checkBox.isChecked()){
            Toast.makeText(this, "You've selected: "+checkBox.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
