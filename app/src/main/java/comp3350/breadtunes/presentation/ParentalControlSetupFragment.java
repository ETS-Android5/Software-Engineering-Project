package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.breadtunes.R;
import comp3350.breadtunes.business.CredentialManager;
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

        final EditText secretPinView = (EditText) getView().findViewById(R.id.pin_field);
        final EditText secretQuestionView = (EditText) getView().findViewById(R.id.secret_question);
        final EditText secretQuestionAnswerView = (EditText) getView().findViewById(R.id.secret_question_answer);

        //input validation start
        String PIN = String.valueOf(secretPinView.getText());
        boolean secretPINOk = (PIN.length() > 3);

        String secretQuestion = String.valueOf(secretQuestionView.getText());
        boolean secretQuestionOK = (secretQuestion.length() > 4 && (secretQuestion.length() < 80));

        String secretQuestionAnswer = String.valueOf(secretQuestionAnswerView.getText());
        boolean secretQuestionAnswerOK = (secretQuestionAnswer.length() > 4);

        if(secretPINOk && secretQuestionOK && secretQuestionAnswerOK){
            //WRITE NEW CREDENTIALS
            CredentialManager credentialManager = ServiceGateway.getCredentialManager();
            credentialManager.writeNewCredentials(PIN, secretQuestion, secretQuestionAnswer);
            homeActivity.showSongListFragment();
            Toast.makeText(homeActivity, "Credentials created", Toast.LENGTH_LONG).show();
        }else{

            if(!secretPINOk)
                Toast.makeText(homeActivity, "PIN must be at least 3 characters long", Toast.LENGTH_LONG).show();
            if(!secretQuestionOK)
                Toast.makeText(homeActivity, "Secret question must be at least 5 characters long", Toast.LENGTH_LONG).show();
            if(!secretQuestionAnswerOK)
                Toast.makeText(homeActivity, "Secret question answer must be at least 5 characters long", Toast.LENGTH_LONG).show();
        }

    }
}
