package com.example.covoiturageapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajetChauffeurFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajetChauffeurFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText villeDepart, villeArrivee;

    TextView heureDepart , dateTrajet;

    Button posterTrajetBtn;

    FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    public TrajetChauffeurFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrajetChauffeurFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrajetChauffeurFragment newInstance(String param1, String param2) {
        TrajetChauffeurFragment fragment = new TrajetChauffeurFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View v= inflater.inflate(R.layout.fragment_trajet_chauffeur, container, false);
//init_________

        dateTrajet=v.findViewById(R.id.date_trajet_chauffeur);
        heureDepart=v.findViewById(R.id.heure_trajet_chauffeur);
        villeDepart=v.findViewById(R.id.depart_trajet_chauffeur);
        villeArrivee=v.findViewById(R.id.destination_trajet_chauffeur);
        posterTrajetBtn=v.findViewById(R.id.poster_trajet_chauffeur);
//fireBase___________
        firebaseDatabase=FirebaseDatabase.getInstance("https://covoiturage-8e6b9-default-rtdb.europe-west1.firebasedatabase.app/");
        auth=FirebaseAuth.getInstance();
        FirebaseUser user= auth.getCurrentUser();
        String uid= user.getUid();



        dateTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    datePickerPopUp(dateTrajet);
            }
        });

        heureDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerPopUp(heureDepart);
            }
        });


        posterTrajetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String heureDepartTxt = heureDepart.getText().toString();
                String villeDepartTxt = villeDepart.getText().toString().trim().toLowerCase();
                String villeArriveeTxt = villeArrivee.getText().toString().trim().toLowerCase();
                String dateTrajetTxt = dateTrajet.getText().toString();

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(c.YEAR);
                int mMonth = c.get(c.MONTH);
                int mDay = c.get(c.DAY_OF_MONTH);
                String dateToday= ( mDay+""+mMonth+1+""+ mYear);

                boolean empty_blanks= TextUtils.isEmpty(villeDepartTxt) || TextUtils.isEmpty(villeArriveeTxt)
                        || heureDepartTxt.equals("00:00") || dateTrajetTxt.equals("00/00/00");

                boolean dateError=  dateTrajetTxt.replace("/","").compareTo(dateToday)<0;

                if(empty_blanks){
                    Toast.makeText(getContext(), R.string.empty_blanks, Toast.LENGTH_SHORT).show();
                } else if (dateError) {
                    Toast.makeText(getContext(), "Date erronée", Toast.LENGTH_SHORT).show();
                }else{

                    Trajet trajet = new Trajet(heureDepartTxt,villeDepartTxt,villeArriveeTxt,dateTrajetTxt,"userName");
                    firebaseDatabase.getReference("chauffeur").child(uid).child("trajet").setValue(trajet);
                    Toast.makeText(getContext(), "trajet posté", Toast.LENGTH_SHORT).show();

                }


            }
        });







        return v;}


//funct___________________________________________________________________________________________

  private  void datePickerPopUp(View viewInput){

      final Calendar c = Calendar.getInstance();
      int mYear = c.get(c.YEAR);
      int mMonth = c.get(c.MONTH);
      int mDay = c.get(c.DAY_OF_MONTH);


      if (viewInput instanceof EditText) {
          ((EditText) viewInput).setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
      } else if (viewInput instanceof TextView) {
          ((TextView) viewInput).setText (mDay + "/" + (mMonth + 1) + "/" + mYear);
      }else if (viewInput instanceof Button) {
          ((Button) viewInput).setText (mDay + "/" + (mMonth + 1) + "/" + mYear);
      }



      DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
              new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year,
                                        int monthOfYear, int dayOfMonth) {
                      // Set the selected date on the button
                      dateTrajet.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                  }
              }, mYear, mMonth, mDay);
      datePickerDialog.show();
  }



    private  void timePickerPopUp(View viewInput){


        Calendar calendarNow= Calendar.getInstance();
        int hour= calendarNow.get(Calendar.HOUR_OF_DAY);
        int minutes=calendarNow.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timePickerListner= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (viewInput instanceof EditText) {
                    ((EditText) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                } else if (viewInput instanceof TextView) {
                    ((TextView) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                }else if (viewInput instanceof Button) {
                    ((Button) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                }
            }
        };

        TimePickerDialog timePickerDialog= new TimePickerDialog(
                getContext(),2,timePickerListner,hour,minutes,true);

        timePickerDialog.show();

    }//timePickerShowUp function

}