package com.geeklife.arcstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class CreateEmployee extends AppCompatActivity {

    EditText fName, lName, bUnit, hOffice, emailAdd, contact;

    // required lists
    String buItems[] = {"Defence", "Nuclear", "Oil & Gas", "Operations", "Rail", "Resourcing"};
    String bu[] = {"DBU", "NBU", "OBU", "OPS", "RBU"};
    String countryList[] = {"United Kingdom (+44)", "Australia (+61)", "Singapore (+51)"};
    String officeList[] = {"Edinburgh", "Glasgow", "Manchester", "Bristol", "Taunton", "london",
            "Singapore", "Pert", "Sydney"};

    JSONObject employee;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_user );


        Button enter = findViewById( R.id.btn_enter );

        fName = findViewById( R.id.first_name );
        lName = findViewById( R.id.last_name );
        bUnit = findViewById( R.id.bus_unit );
        hOffice = findViewById( R.id.home_office );
        emailAdd = findViewById( R.id.email );
        contact = findViewById( R.id.contact );

        employee = new JSONObject();

        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange( View v, boolean hasFocus ) {

                switch ( v.getId() ) {
                    // focus changes to BU
                    case R.id.bus_unit:
                        if ( hasFocus )
                            Toast.makeText( CreateEmployee.this, "clicked BUS UNIT", Toast.LENGTH_SHORT ).show();
                        break;
                    // focus changes to HO
                    case R.id.home_office:
                        if ( hasFocus )
                            Toast.makeText( CreateEmployee.this, "clicked Office", Toast.LENGTH_SHORT ).show();
                        break;
                }

            }
        };


        bUnit.setOnFocusChangeListener( focusListener );
        hOffice.setOnFocusChangeListener( focusListener );

        enter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                // build JSON Object
                try {
                    employee.put( "first_name", fName.getText().toString() );
                    employee.put( "last_name", lName.getText().toString() );
                    employee.put( "bus_unit", bUnit.getText().toString() );
                    employee.put( "home_office", hOffice.getText().toString() );
                    employee.put( "email", emailAdd.getText().toString() );
                    employee.put( "contact", contact.getText().toString() );

                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
                Log.i( "BTN", employee.toString() );
                new AsyncCreateEmp().execute();

            }
        } );

    }

    private class AsyncCreateEmp extends AsyncTask<Void, Void, Void> {
        HttpURLConnection connection;
        URL url = null;
        ProgressBar progressBar = findViewById( R.id.progressbar );

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //set a process dialogue on UI thread
            progressBar.setVisibility( View.VISIBLE );

        }

        @Override
        protected Void doInBackground( Void... voids ) {
            return null;
        }

        @Override
        protected void onPostExecute( Void v ) {
            super.onPostExecute( v );
            progressBar.setVisibility( View.GONE );
            Toast.makeText( CreateEmployee.this, "DONE", Toast.LENGTH_SHORT ).show();
        }
    }


}
