package com.androidapp.tri_fit_app.tfa2;

import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Login_Activity extends AppCompatActivity implements NewUser.AddNewUserListener{
    Button bNew, bLogin;
    TextView tvUserInfo;
    private String TAG = "Tri-Fit-App";
    SQLiteDatabase dtb;
    int btnBackPressCount = 0;
    dbhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        db = new dbhandler(this) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                super.onCreate(db);
            }
        };

        bNew = (Button)findViewById(R.id.bNew);
        bLogin = (Button)findViewById(R.id.bLogin);

        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewUser dialog = new NewUser();
                dialog.show(getFragmentManager(), TAG);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvUserInfo = (TextView) findViewById(R.id.tvUserInfo);

                tvUserInfo.setMovementMethod(ArrowKeyMovementMethod.getInstance());

                tvUserInfo.setText(""); // clear text area at each button press
                tvUserInfo.setTextColor(Color.MAGENTA);
                tvUserInfo.setPadding(5, 2, 5, 2);

                List<User> usersList = db.getAllUserList();

                for ( User usr : usersList ) {

                    String usrDetails = "\n\nID:" + usr.get_id()+ "\n\tEMAIL:" + usr.get_email();
                    tvUserInfo.append("\n"+usrDetails);

                    // Log.i("TAG", log)
                }
            }
        });

    }

    @Override
    public void onSaveButtonClick(DialogFragment dialog) {

        EditText entEmail = (EditText) dialog.getDialog().findViewById(R.id.etEmail);
        String email = entEmail.getText().toString();

        EditText entUsername = (EditText) dialog.getDialog().findViewById(R.id.etUsername);
        String username = entUsername.getText().toString();

        EditText entAge = (EditText) dialog.getDialog().findViewById(R.id.etAge);
        String age = entAge.getText().toString();

        EditText entWeight = (EditText) dialog.getDialog().findViewById(R.id.etWeight);
        String weight = entWeight.getText().toString();

        EditText entHeight = (EditText) dialog.getDialog().findViewById(R.id.etHeight);
        String height = entHeight.getText().toString();

        EditText entPassword = (EditText) dialog.getDialog().findViewById(R.id.etPassword);
        String password = entPassword.getText().toString();


        boolean check_email = checkEmail(email);

        boolean check_username = checkUsername(username);

        boolean check_age = checkAge(age);

        boolean check_weight = checkWeight(weight);

        boolean check_height = checkHeight(height);

        boolean check_password = checkPassword(password);

        if(check_email == false || check_username == false || check_age == false || check_weight == false || check_height == false || check_password == false ){

            Toast.makeText(getApplicationContext(),"Enter Data Again Please.. :P",Toast.LENGTH_LONG);
        }else{

            db.addNewUser(new User(email,username,age,weight,height,password));

            Toast.makeText(getApplicationContext(),"User has been authorized.. 0_o ",Toast.LENGTH_LONG);
        }

        Toast.makeText(getApplicationContext(),"\nEmail: " + email + "\nUsername: " + username + "\nAge " + age + "\nWeight: " + weight + "\nHeight " + height + "\nPassword " + password +)



        }
