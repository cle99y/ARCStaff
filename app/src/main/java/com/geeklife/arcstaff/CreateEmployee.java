package com.geeklife.arcstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class CreateEmployee extends AppCompatActivity {

    final String OFFICE = "office";
    final String BUSINESS = "business";

    EditText fName, lName, bUnit, hOffice, emailAdd, contact;

    ListView buList, offList;
    // required lists
    String buItems[] = {"Defence", "Nuclear", "Oil & Gas", "Operations", "Rail", "Resourcing"};
    String buNames[] = {"DBU", "NBU", "OBU", "OPS", "RBU", "RMG"};
    String countryItems[] = {"United Kingdom (+44)", "Australia (+61)", "Singapore (+51)"};
    String officeItems[] = {"Edinburgh", "Glasgow", "Manchester", "Bristol", "Taunton", "London",
            "Singapore", "Perth", "Sydney"};

    JSONObject employee;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_user );

        final InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );

        Button enter = findViewById( R.id.btn_enter );

        fName = findViewById( R.id.first_name );
        lName = findViewById( R.id.last_name );
        bUnit = findViewById( R.id.bus_unit );
        hOffice = findViewById( R.id.home_office );
        emailAdd = findViewById( R.id.email );
        contact = findViewById( R.id.contact );
        buList = findViewById( R.id.list_view );
        offList = findViewById( R.id.list_view );

        employee = new JSONObject();

        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange( View v, boolean hasFocus ) {

                switch ( v.getId() ) {
                    // focus changes to BU
                    case R.id.bus_unit:
                        if ( hasFocus ) {
                            Log.i("FORM", "BU has focus");
                            imm.hideSoftInputFromWindow( bUnit.getWindowToken(), 0 );
                            setArrayAdapter( buItems );
                            toggleVisibility();
                        }
                        break;
                    // focus changes to HO
                    case R.id.home_office:
                        if ( hasFocus ) {
                            Log.i("FORM", "OFFICE has focus");
                            imm.hideSoftInputFromWindow( hOffice.getWindowToken(), 0 );
                            setArrayAdapter( officeItems );
                            toggleVisibility();
                        }
                        break;
                }

            }
        };


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

        buList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int pos, long len ) {
                bUnit.setText( buNames[pos] );
                toggleVisibility();
            }
        } );

        offList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int pos, long len ) {
                hOffice.setText( officeItems[pos] );
                toggleVisibility();
            }
        } );

        bUnit.setOnFocusChangeListener( focusListener );
        hOffice.setOnFocusChangeListener( focusListener );

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

    private ArrayAdapter setArrayAdapter( String arrayToUse[] ) {

        ArrayAdapter mAdapter = new ArrayAdapter<>
                ( this, R.layout.lv_rows, arrayToUse );
        buList.setAdapter( mAdapter );

        return mAdapter;
    }

    private void toggleVisibility() {
        ConstraintLayout form = findViewById( R.id.new_user_form );
        Log.i( "FORM", Integer.toString( form.getChildCount() ) );

        for ( int i = 0; i < form.getChildCount(); i++ ) {
            View v = form.getChildAt( i );

            if ( v.getVisibility() == View.GONE ) {
                v.setVisibility( View.VISIBLE );
            } else {
                v.setVisibility( View.GONE );
            }
        }

    }
}
