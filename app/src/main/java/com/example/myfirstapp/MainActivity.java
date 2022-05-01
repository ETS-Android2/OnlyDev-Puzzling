package com.example.myfirstapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;//modifLeon
import androidx.core.content.FileProvider;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logicClasses.SQLManager;
import logicClasses.User;
import logicClasses.UserFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // ---- Member references ------- //
    private Button logIn, signIn, help, exit;
    private EditText mail, password;

    // ----- AUDIO ------------ //
    MediaPlayer player;

    // ----- ATTRIBUTES ------------ //
    SQLManager sqlManager = new SQLManager(MainActivity.this, "PuzzlingDatabase",
            null, 1);
    User user;


    @Override
    public void onResume() {
        super.onResume();
        Intent i= new Intent(this,MusicManager.class);
        startService(i);
    }
    @Override
    public void onPause() {
        super.onPause();
        Intent i= new Intent(this,MusicManager.class);
        stopService(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_main);
        //iniciar();
        Intent i= new Intent(this,MusicManager.class);
        startService(i);

        //Setting value for the member references from the first layout
        this.logIn = findViewById(R.id.logIn);
        this.signIn = findViewById(R.id.signIn);
        this.help = findViewById(R.id.help);
        this.exit = findViewById(R.id.exit);
        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password);


        //Button listeners
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
                        MainActivity.this.saveSharedPreferences(userRetrieved.getIdUser());
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
                            MainActivity.this.setUser(userRetrieved); //Setting user to later store score
                            MainActivity.this.saveSharedPreferences(userRetrieved.getIdUser());
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

        this.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpLayout = new Intent(getApplicationContext(), Help.class);
                startActivity(helpLayout);
            }
        });

        this.exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Close connection to database
//                MainActivity.this.sqlManager.close();
//                finish();
//            }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SeleccionarPuzzle.class);
                startActivity(intent);
            }
        });

    }

    public boolean areFieldsFulfill() {
        String mail = MainActivity.this.mail.getText().toString();
        String password = MainActivity.this.password.getText().toString();
        //Instantiate object if there are valid values inside the input fields
        if (mail.isEmpty() || password.isEmpty() || !(mail.contains("@"))) {
            Toast.makeText(MainActivity.this,
                    "Please, enter valid mail and password.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // ----- AUDIO METHODS------------ //

    public void play(View view) {
        Intent i= new Intent(this,MusicManager.class);
        startService(i);
    }



    public void pause(View view) {
        if (player != null){
            player.pause();
        }
    }

    public void stop(View view) {
        Intent i= new Intent(this,MusicManager.class);
        stopService(i);
    }


    @Override
    public void onClick(View view) {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveSharedPreferences(int valueToSave){
        /*
        Once the user is sign in or log in, this information will be stored in a shared preference
        in order to use this information for the next layouts.
        */
        //Creating the shared preferences file
        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        //Put information in the file
        sharedPreferencesEditor.putInt("UserID", valueToSave);
        sharedPreferencesEditor.commit();
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    public void Foto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}

