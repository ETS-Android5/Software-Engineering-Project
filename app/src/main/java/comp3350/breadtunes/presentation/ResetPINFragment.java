package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import comp3350.breadtunes.R;
import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.services.ServiceGateway;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPINFragment extends Fragment {


    public HomeActivity homeActivity;
    private final String TAG = "HomeActivity";
    private static TextView secretQuestion;
    private static Button submitButton;


    public ResetPINFragment() {
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
        return inflater.inflate(R.layout.fragment_reset_pin, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        secretQuestion = (TextView) getView().findViewById(R.id.secret_question);
        CredentialManager credentialManager = ServiceGateway.getCredentialManager();
        secretQuestion.setText(credentialManager.getSecretQuestion());
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
                resetCredentials();
            }
        });
    }

    private void resetCredentials(){
        final EditText secretQuestionAnswerView = (EditText) getView().findViewById(R.id.secret_question_answer);

        String secretQuestionAnswer = String.valueOf(secretQuestionAnswerView.getText());
        boolean secretQuestionAnswerOK = (secretQuestionAnswer.length() > 4);

        final EditText secretPinView = (EditText) getView().findViewById(R.id.pin_field);
        String PIN = String.valueOf(secretPinView.getText());
        boolean secretPINOk = (PIN.length() > 3);

        if(secretQuestionAnswerOK && secretPINOk){

               CredentialManager credentialManager = ServiceGateway.getCredentialManager();
               if(credentialManager.validateSecretQuestionAswer(secretQuestionAnswer)){   //validate secret question answer and update records with new pin
                   credentialManager.updatePIN(PIN);
                   Toast.makeText(homeActivity, "New PIN saved.", Toast.LENGTH_LONG).show();
                   homeActivity.showSongListFragment();
               }else{
                   Toast.makeText(homeActivity, "Incorrect secret question answer", Toast.LENGTH_LONG).show();
               }

        }else{

            if(!secretPINOk)
                Toast.makeText(homeActivity, "PIN must be at least 3 characters long", Toast.LENGTH_LONG).show();

            if(!secretQuestionAnswerOK)
                Toast.makeText(homeActivity, "Secret question answer must be at least 5 characters long", Toast.LENGTH_LONG).show();

        }

    }
}
