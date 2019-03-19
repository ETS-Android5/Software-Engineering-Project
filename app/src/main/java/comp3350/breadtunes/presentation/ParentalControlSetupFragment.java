package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.breadtunes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentalControlSetupFragment extends Fragment {

    public HomeActivity homeActivity;
    private final String TAG = "HomeActivity";
    public static Button submitButton;


    public ParentalControlSetupFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parental_control_setup, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        registerOnClickSubmit();
    }

    public void onResume(){
        super.onResume();
        registerOnClickSubmit();
    }

    private void registerOnClickSubmit(){
        submitButton = (Button) getView().findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupParentalControl();
            }
        });
    }


    private void setupParentalControl(){

        final EditText secretPin = (EditText) getView().findViewById(R.id.pin_field);
        final EditText secretQuestion = (EditText) getView().findViewById(R.id.secret_question);
        final EditText secretQuestionAnswer = (EditText) getView().findViewById(R.id.secret_question_answer);

        //do input validation here

        //if all is good
        // communicate with business object and hand over the data so it can be written into the database
        // homeActivity.showSongListFragment
    }

}
