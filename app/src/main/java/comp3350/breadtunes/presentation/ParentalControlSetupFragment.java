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
import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.persistence.CredentialPersistence;
import comp3350.breadtunes.services.ServiceGateway;

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

        String pin = secretPin.getText().toString();
        String secretQ = secretQuestion.getText().toString();
        String secretQAns = secretQuestionAnswer.getText().toString();
        CredentialManager credentialManager = ServiceGateway.getCredentialManager();
        credentialManager.writeNewCredentials(pin, secretQ, secretQAns);

        // TODO: Remove! Just checking something out here
        CredentialPersistence persistence = ServiceGateway.getCredentialPersistence();
        SecureCredentials credentials = persistence.getMostRecentCredentials();

        // homeActivity.showSongListFragment
    }
}
