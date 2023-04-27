package com.example.covoiturageapp;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajetClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajetClientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatePickerDialog datePickerDialog;

    TextView dateView;

    EditText villeDepart,villeArrivee;

    Button chercherTrajetBtn;

    public TrajetClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrajetClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrajetClientFragment newInstance(String param1, String param2) {
        TrajetClientFragment fragment = new TrajetClientFragment();
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
        View view = inflater.inflate(R.layout.fragment_trajet_client, container, false);

        // Date picker on a textView
        dateView= view.findViewById(R.id.date_trajet_client);
        chercherTrajetBtn=view.findViewById(R.id.recherche_trajet_client);
        villeDepart=view.findViewById(R.id.depart_trajet_client);
        villeArrivee=view.findViewById(R.id.destination_trajet_client);




        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(c.YEAR);
                int mMonth = c.get(c.MONTH);
                int mDay = c.get(c.DAY_OF_MONTH);

                dateView.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Set the selected date on the button
                                dateView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        chercherTrajetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String villeArriveeTxt= villeArrivee.getText().toString().trim().toLowerCase();
                String villeDepartTxt= villeDepart.getText().toString().trim().toLowerCase();
                String dateTrajetTxt= dateView.getText().toString();

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(c.YEAR);
                int mMonth = c.get(c.MONTH);
                int mDay = c.get(c.DAY_OF_MONTH);
                String dateToday= ( mDay+""+mMonth+1+""+ mYear);

                boolean empty_blanks= TextUtils.isEmpty(villeDepartTxt) || TextUtils.isEmpty(villeArriveeTxt);


                boolean dateError=  dateTrajetTxt.replace("/","").compareTo(dateToday)<0;

                if(empty_blanks){
                    Toast.makeText(getContext(), R.string.empty_blanks, Toast.LENGTH_SHORT).show();
                } else if (dateError) {
                    Toast.makeText(getContext(), "date erronée", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getContext(),RecherchTrajetClient.class);
                    intent.putExtra("villeArrivee",villeArriveeTxt);
                    intent.putExtra("villeDepart",villeDepartTxt);
                    intent.putExtra("dateTrajet",dateTrajetTxt);

                    startActivity(intent);

                }




            }
        });




        return view;
    }
}