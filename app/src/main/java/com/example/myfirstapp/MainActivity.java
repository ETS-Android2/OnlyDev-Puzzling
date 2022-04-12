package com.example.myfirstapp;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;

import logicClasses.SQLManager;
import logicClasses.User;
import logicClasses.UserFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // ---- Member references ------- //
    private Button logIn, signIn, help, exit;
    private EditText mail, password;

    // ----- ATTRIBUTES ------------ //
    SQLManager sqlManager = new SQLManager(MainActivity.this, "PuzzlingDatabase",
            null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_main);

        //Setting value for the member references from the first layout
        this.logIn = findViewById(R.id.logIn);
        this.signIn = findViewById(R.id.signIn);
        this.help = findViewById(R.id.help);
        this.exit = findViewById(R.id.exit);
        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password);

        //Button listeners
        this.logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mail = MainActivity.this.mail.getText().toString();
                String password = MainActivity.this.mail.getText().toString();
                //TODO: handler exceptions if needed. Test app with possible errors
                //Instantiate object if there are valid values inside the input fields
                if (mail.isEmpty() || password.isEmpty() || !(mail.contains("@"))){
                    Toast.makeText(MainActivity.this,
                            "Please, enter valid mail and password.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    User user = UserFactory.createUser(mail, password);
                    //TODO: Check if user already exists [NOT WORKING]
                    ArrayList<String> userRetrieved = MainActivity.this.sqlManager.retrieveUser(user.getMail());
                    if (userRetrieved.isEmpty()){
                        try{ //Add user to database
                            long createStatement = MainActivity.this.sqlManager.createOneUser(user);
                            Toast.makeText(MainActivity.this, "User \"" + user.getMail() +
                                    "\" created successfully.", Toast.LENGTH_LONG).show();
                            if (createStatement == -1){ //Catch return from createOne
                                throw new Exception("An error occur adding the user" + user.toString() +
                                        "\nPlease try again.");
                            }
                        } catch (Exception exception){
                            Toast.makeText(MainActivity.this, exception.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "This user already exists.",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You've pressed the sign in button",Toast.LENGTH_SHORT).show();
            }
        });

        this.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You've pressed the help button",Toast.LENGTH_SHORT).show();
            }
        });

        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You've pressed the exit button",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
//
//        switch (view.getId()){
//
//            case R.id.signIn:
//                Intent intent = new Intent(this, Menu.class);
//                startActivity(intent);
//                break;
//
//
//            case R.id.help: //go to help View
//                intent = new Intent(this, Help.class);
//                startActivity(intent);
//                break;
//
//                case R.id.exit: //sale de la aplicacion
//                finish();
//                break;
    }


    /*public void playMenu(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void helpPage(View view){
        Intent i = new Intent(this,Help.class);
        startActivity(i);
    }

    public void clickExit(View view){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }*/
    }
//}
