package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

    // ---- POSSIBLE INTENTS ------- //


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
                if (MainActivity.this.areFieldsFulfill()) {
                    User user = UserFactory.createUser(MainActivity.this.mail.getText().toString(),
                            MainActivity.this.password.getText().toString());

                    User userRetrieved = MainActivity.this.sqlManager.retrieveUser(user.getMail());
                    if (userRetrieved.getMail().isEmpty()) { //There is no user with same account
                        try { //Add user to database
                            long createStatement = MainActivity.this.sqlManager.createOneUser(user);
                            Toast.makeText(MainActivity.this, "User \"" + user.getMail() +
                                    "\" created successfully.", Toast.LENGTH_LONG).show();
                            //Getting in Menu layout
                            Intent startLayout = new Intent(getApplicationContext(), Menu.class);
                            startActivity(startLayout);
                            if (createStatement == -1) { //Catch return from createOne
                                throw new Exception("An error occur adding the user" + user +
                                        "\nPlease try again.");
                            }
                        } catch (Exception exception) {
                            Toast.makeText(MainActivity.this, exception.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "This user already exists." +
                                userRetrieved, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MainActivity.this.areFieldsFulfill()) {
                    String mail = MainActivity.this.mail.getText().toString();
                    String password = MainActivity.this.password.getText().toString();
                    User userInserted = new User(mail, password);
                    User userRetrieved = MainActivity.this.sqlManager.
                            retrieveUser(mail);
                    if (userInserted.equals(userRetrieved)) {
                        //Getting in Menu layout
                        Intent startLayout = new Intent(getApplicationContext(), Menu.class);
                        startActivity(startLayout);
                    } else {
                        Toast.makeText(MainActivity.this, "The user or password given " +
                                "is not valid. Please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

        this.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpLayout = new Intent(getApplicationContext(), Help.class);
                startActivity(helpLayout);
            }
        });

        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close connection to database
                MainActivity.this.sqlManager.close();
                finish();
            }
        });
    }

    public boolean areFieldsFulfill() {
        String mail = MainActivity.this.mail.getText().toString();
        String password = MainActivity.this.password.getText().toString();
        //TODO: handler exceptions if needed. Test app with possible errors
        //Instantiate object if there are valid values inside the input fields
        if (mail.isEmpty() || password.isEmpty() || !(mail.contains("@"))) {
            Toast.makeText(MainActivity.this,
                    "Please, enter valid mail and password.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}

