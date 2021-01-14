package rmit.aad.week5_maplocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.Map;

public class ShowEventActivity extends AppCompatActivity {

    TextView txtEventName, txtData;
    Button btnJoinEvent;
    private ActionBar toolBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notebookRef = db.collection("events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        toolBar = getSupportActionBar();
        toolBar.setTitle("Event Detail");
        txtEventName = findViewById(R.id.txtEventName);
        btnJoinEvent = findViewById(R.id.btnJoinEvent);
        final TextView txtNumber = findViewById(R.id.txtNumber);
        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use Dialog
                AlertDialog alertDialog = new AlertDialog.Builder(ShowEventActivity.this).create();
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Do you want to join this event?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int num = Integer.parseInt(txtNumber.getText().toString());
                        num++;
                        txtNumber.setText(num + "");
                        Toast.makeText(ShowEventActivity.this, "You have JOIN the event", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ShowEventActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        //Read data to print out the Event  in the database
        notebookRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String lat = (String) document.getData().get("Latitude");
                            String lng = (String) document.getData().get("Longitude");
                            String eventName = (String) document.getData().get("Event Name");
                            String description = (String) document.getData().get("Description");
                            String contact = (String) document.getData().get("Contact");
                            String date = (String) document.getData().get("Date");

                            if (date == null || contact == null ||
                                    description == null || eventName == null || lat == null || lng == null)
                                continue;

                            txtEventName.setText(eventName + "\n" + description +
                                    "\n" + contact + "\n" + date + "\n" + lat + "\n" + lng);
                        }
                    }
                });
    }
}