/**
 * Authors : @SaiDeep , @ManoTeja , @TRKSaran
 * An application to register teams for COP290 undergraduate course
 * HTTP POST method is used to submit requests from the client side
 * Server will send the response in JSON format
 * In this assignment, we use Volley, an HTTP library that makes networking for Android apps easier
 */
package com.example.saideep.cop290_registration;

import java.util.HashMap;
import java.util.Map;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String URL = "http://agni.iitd.ernet.in/cop290/assign0/register/"; // URL of the server to which data is to be posted

    EditText TeamName,Entry1,Name1,Entry2,Name2,Entry3,Name3; // Text fields to be entered for registration

    Button RegisterButton; // Register button

    ProgressBar progressBar; // Progress Bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TeamName = (EditText) findViewById(R.id.TeamName);
        Entry1 = (EditText) findViewById(R.id.Entry1);
        Name1 = (EditText) findViewById(R.id.Name1);
        Entry2 = (EditText) findViewById(R.id.Entry2);
        Name2 = (EditText) findViewById(R.id.Name2);
        Entry3 = (EditText) findViewById(R.id.Entry3);
        Name3 = (EditText) findViewById(R.id.Name3);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        RegisterButton = (Button) findViewById(R.id.RegisterButton);

        RegisterButton.setOnClickListener(this);

        /**
          Clear all fields when the register button is long clicked
         */
        RegisterButton.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        clearForm((ViewGroup) findViewById(R.id.linear_layout));
                        return true;
                    }
    });
    }

    /** Registration of team (Submission of data to server) when register button is clicked with all the entered fields being valid
     * @source : https://www.simplifiedcoding.net/android-volley-post-request-tutorial/
     */
    private void register()
    {
        progressBar.setVisibility(View.VISIBLE); // Progressbar appears once the register button is clicked . Stays till response is received from the server

         /*
          Strings to represent Text extracted from various Edit Text fields input by the user - with leading and trailing whitespace removed
         */

        final String TeamName_value = TeamName.getText().toString().trim();
        final String Entry1_value   = Entry1.getText().toString().trim();
        final String Name1_value    = Name1.getText().toString().trim();
        final String Entry2_value   = Entry2.getText().toString().trim();
        final String Name2_value    = Name2.getText().toString().trim();
        final String Entry3_value   = Entry3.getText().toString().trim();
        final String Name3_value    = Name3.getText().toString().trim();


        /*
           Make a HTTP POST request to the server to submit the data from client side
         */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    /**
                      Performs actions on receiving server's response
                      @params: response of the Server in String format
                     */
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE); // On receiving the server's response make the progress bar disappear

                        String message=""; // Message to be displayed to the user on receiving server's response

                        if(response.contains("not")) // CASE 1: {"RESPONSE_MESSAGE": "Data not posted!", "RESPONSE_SUCCESS": 0}
                        {
                            message = "Data not posted";
                        }
                        else if(response.contains("completed")) //CASE 2: {"RESPONSE_MESSAGE": "Registration completed", "RESPONSE_SUCCESS": 1}
                        {
                            message = "Registration Successful!";

                        }
                        else if(response.contains("already")) // CASE 3: {"RESPONSE_MESSAGE": "User Already Registered", "RESPONSE_SUCCESS": 0}
                        {
                            message = "User already registered!";
                        }

                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show(); // Display the appropriate message to the user
                    }
                },
                /**
                 * Error Handling
                 */
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })

            {
            /**
             *
             * @return  a HashMap with Key Value pairs that we will put the data to be sent
             */
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("teamname",TeamName_value);
                params.put("entry1",Entry1_value);
                params.put("name1", Name1_value);
                params.put("entry2",Entry2_value);
                params.put("name2", Name2_value);
                params.put("entry3",Entry3_value);
                params.put("name3", Name3_value);
                return params;
            }

        };

        // Add the request to a queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    /**
     * To clear all the Edit Text fields simultaneously
     * @source http://stackoverflow.com/questions/5740708/android-clearing-all-edittext-fields-with-clear-button
     */
    public void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i)
        {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
        Toast.makeText(this, "All fields clear!", Toast.LENGTH_SHORT).show();
    }




     /**
      * Performs actions on clicking the Register button
      * Appropriate errors are displayed if any of the EditText fields are invalid
      */

    @Override
    public void onClick(View v) {

        String EmptyField   = "This is a required field!" ; // Error message to be displayed in case of an empty field
        String InvalidName  = "Name can contain only alphabet and spaces"; // Error message to be displayed in case of entry of an invalid name
        String InvalidEntry = "Enter a valid entry number"; // Error message to be displayed in case of invalid entry number

        /*
          Strings to represent Text extracted from various Edit Text fields input by the user - with leading and trailing whitespace removed
         */

        final String TeamName_value = TeamName.getText().toString().trim();
        final String Entry1_value   = Entry1.getText().toString().trim();
        final String Name1_value    = Name1.getText().toString().trim();
        final String Entry2_value   = Entry2.getText().toString().trim();
        final String Name2_value    = Name2.getText().toString().trim();
        final String Entry3_value   = Entry3.getText().toString().trim();
        final String Name3_value    = Name3.getText().toString().trim();

        if(v == RegisterButton) {                  // when Register button is clicked

            if (TeamName_value.length() == 0) {  // if the teamname field is empty
                TeamName.setError(EmptyField);  // display the error
                TeamName.requestFocus();
            }

            else if (Entry1_value.length() == 0) {    // if the first entry number field is empty
                Entry1.setError(EmptyField);         // display the error
                Entry1.requestFocus();
            }

            else if (!Validate.ValidateEntry(Entry1_value)) {  // if the entry number is invalid
                Entry1.setError(InvalidEntry);                // display the error
                Entry1.requestFocus();
            }

            /*
              Similarly....s.Error handling for rest of the fields follows
            */

            else if (Name1_value.length() == 0) {
                Name1.setError(EmptyField);
                Name1.requestFocus();
            }

            else if (!Validate.ValidateName(Name1_value)){
                Name1.setError(InvalidName);
                Name1.requestFocus();
            }

            else if (Entry2_value.length() == 0){
                Entry2.setError(EmptyField);
                Entry2.requestFocus();
            }

            else if (!Validate.ValidateEntry(Entry2_value)){
                Entry2.setError(InvalidEntry);
                Entry2.requestFocus();
            }

            else if (Name2_value.length() == 0){
                Name2.setError(EmptyField);
                Name2.requestFocus();
            }

            else if (!Validate.ValidateName(Name2_value)){
                Name2.setError(InvalidName);
                Name2.requestFocus();
            }


            /*
             Name3,Entry3 are optional but one cannot be empty when the other is non empty
             */

            else if(!(Entry3_value.length()==0 && Name3_value.length()==0))
            {
                  if(Entry3_value.length()==0)
                  {
                      Entry3.setError("If you are submitting the third member's name, you should fill his entry number too!");
                      Entry3.requestFocus();
                  }
                  else if(!Validate.ValidateEntry(Entry3_value))
                  {
                      Entry3.setError(InvalidEntry);
                      Entry3.requestFocus();
                  }
                  else if(Name3_value.length()==0)
                  {
                      Name3.setError("If you are submitting the third member's entry number, you should fill his name too!");
                      Name3.requestFocus();
                  }
                  else if(!Validate.ValidateName(Name3_value))
                  {
                      Name3.setError(InvalidName);
                      Name3.requestFocus();
                  }
                  else
                  register();
            }

            else
                register(); // register if all the entered fields are valid and not empty
        }
    }



    /**
        Confirm app exit with toast - clicking the back button TWICE to exit
        @source: http://stackoverflow.com/questions/8430805/android-clicking-twice-the-back-button-to-exit-activity
     */
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {  // if back button has been pressed once already
            super.onBackPressed();
            return;
        }

        //else

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        /*
         * reset the doubleBackToExitPressedOnce to false after 2 secs on inactivity
         *
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}