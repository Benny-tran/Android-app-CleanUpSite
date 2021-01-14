package rmit.aad.week5_maplocation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import rmit.aad.week5_maplocation.R;

public class MyListFragment extends Fragment {
    public MyListFragment (){
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    TextView txtViewData;
    private CollectionReference notebookRef = db.collection("events");


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);
        txtViewData = view.findViewById(R.id.txtViewData);

                //Read data to print out the Event in the database
                notebookRef
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                String data = "";
                                for (QueryDocumentSnapshot documentSnapshot: querySnapshot){
                                    String name = (String) documentSnapshot.getData().get("Event Name");
                                    String des = (String) documentSnapshot.getData().get("Description");
                                    String con = (String) documentSnapshot.getData().get("Contact");
                                    String dat = (String) documentSnapshot.getData().get("Date");

                                    if (name==null || des==null|| con==null || dat==null) continue;
                                    data += "\n\nEvent: " + name + "\nDescription: " + des + "\nContact: " + con + "\nDate: " + dat;
                                }
                                txtViewData.setText(String.valueOf(data));
                            }
                        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }
}