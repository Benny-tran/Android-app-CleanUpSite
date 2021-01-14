package rmit.aad.week5_maplocation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class AddCleanUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Variables
    String latitudevalue, longitudevalue;
    EditText resEventName, resDescription, resContact, resDate, resLat, resLong;
    Button btnAddEvent;
    private ActionBar toolBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clean_up);

        //Hooks to all xml elements in activity_add_clean_up.xml
        resEventName = findViewById(R.id.resEventName);
        resDescription = findViewById(R.id.resDescription);
        resLat = findViewById(R.id.resLat);
        resLong = findViewById(R.id.resLong);
        resContact = findViewById(R.id.resContact);
        resDate = findViewById(R.id.resDate);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        toolBar = getSupportActionBar();
        toolBar.setTitle("Create Event Form");

        Intent intent = getIntent();
        latitudevalue = (String) intent.getExtras().get("latitude");
        resLat.setText(String.valueOf(latitudevalue));
        longitudevalue = (String) intent.getExtras().get("longitude");
        resLong.setText(String.valueOf(longitudevalue));


        //Save data in Firebase on button click
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get all the values
                String eventName = resEventName.getText().toString();
                String description = resDescription.getText().toString();
                String contact = resContact.getText().toString();
                String date = resDate.getText().toString();
                String resLa = resLat.getText().toString();
                String resLon = resLong.getText().toString();

                //Add Data
                Map<String, Object> events = new HashMap<>();
                events.put("Event Name",eventName);
                events.put("Description",description);
                events.put("Contact",contact);
                events.put("Date",date);
                events.put("Latitude",resLa);
                events.put("Longitude",resLon);

                // Add a new document with a generated ID
                db.collection("events")
                        .add(events)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("add","DocumentSnapshot added with ID: "
                                + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("fail","Error adding document",e);
                            }
                        });
                Toast.makeText(AddCleanUpActivity.this,"Registered Successful",Toast.LENGTH_SHORT).show();
                finish();
                }
            });
        }

    public void datePicker(View view){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }
    public void setDate(final Calendar calendar){
        final DateFormat dateFormat =
                DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((EditText)findViewById(R.id.resDate)).setText(dateFormat.format(calendar.getTime()));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateClick(View view) {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                EditText date = findViewById(R.id.resDate);
                date.setText(dayOfMonth + "-" + month + "-" + year);
            }
        });
    }
}
