package comp3350.breadtunes.presentation;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.hsqldb.lib.tar.TarFileOutputStream;

import java.util.Observable;
import java.util.Observer;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.observables.ParentalControlStatusObservable;
import comp3350.breadtunes.business.observables.PlayModeObservable;
import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ServiceGateway;

import static comp3350.breadtunes.presentation.HomeActivity.sList;


// REFERENCE : https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments

public class SongListFragment extends Fragment implements Observer {


    public HomeActivity homeActivity;
    ListView activitySongList;
    String[] songNameList;
    private final String TAG = "HomeActivity";
    public static TextView parentalControlStatus;
    public static Button nowPlayingSongGui;
    String[] menuItems = new String[3];
    // make a updateQueue status

    public SongListFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) getActivity();
        setHasOptionsMenu(true);
        //registerForContextMenu(activitySongList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MusicPlayerState.getInstance().subscribeToSongChange(this);
        MusicPlayerState.getInstance().subscribeToParentalControlStatusChange(this);
        MusicPlayerState.getInstance().subscribeToPlayModeChange(this);
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }



    public void onViewCreated(View view, Bundle savedInstanceState){
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
        parentalControlStatus = (TextView) getView().findViewById(R.id.parental_control_status);
    }

    public void onResume(){
        super.onResume();
        MusicPlayerState.getInstance().subscribeToSongChange(this);
        MusicPlayerState.getInstance().subscribeToParentalControlStatusChange(this);
        getSongNames();
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
        parentalControlStatus = (TextView) getView().findViewById(R.id.parental_control_status);
        parentalControlStatus.setText(MusicPlayerState.getInstance().getParentalControlStatus());
        nowPlayingSongGui.setText(MusicPlayerState.getInstance().getCurrentlyPlayingSongName()+"\n"+MusicPlayerState.getInstance().getPlayMode());

    }

    public void onStop(){
        super.onStop();
    }

    public void onPause(){
        super.onPause();
    }

    public void onDestroyView(){
        super.onDestroyView();
    }


    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


    public void onStart(){
        super.onStart();

    }

    @Override
    public void update(Observable observable, Object o) {

        if(observable instanceof SongObservable){

            SongObservable songObservable = (SongObservable) observable;
            Song song = songObservable.getSong();
            String songName = song.getName();
            nowPlayingSongGui.setText(songName+"\n"+MusicPlayerState.getInstance().getPlayMode());
        }else if(observable instanceof PlayModeObservable){

            PlayModeObservable playModeObservable = (PlayModeObservable) observable;
            String playMode = playModeObservable.getPlayMode();
            String songName = MusicPlayerState.getInstance().getCurrentlyPlayingSong().getName();
            nowPlayingSongGui.setText(songName+"\n"+playMode);
        }else{
            //ELSE its a parental control status observable notification

            ParentalControlStatusObservable parentalControlStatusObservable = (ParentalControlStatusObservable) observable;
            parentalControlStatus.setText(parentalControlStatusObservable.getParentalControlStatus());

        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) homeActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(homeActivity.getComponentName()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.parental_lock_on){

            CredentialManager credentialManager = ServiceGateway.getCredentialManager();
            if(credentialManager.credentialsHaveBeenSet()){

                if(!MusicPlayerState.getInstance().getParentalControlModeOn()){     //only proceeed if parental control is off
                    showPINInputDialog(true);
                }else{
                    Toast.makeText(homeActivity, "Parental Control is already on", Toast.LENGTH_LONG).show();

                }


            }else{
                homeActivity.showParentalControlSetupFragment();
            }
        }
        else if(id == R.id.parental_lock_off){
            if(MusicPlayerState.getInstance().getParentalControlModeOn()){        //only proceed if parental control is on
                showPINInputDialog(false);
            }else {
                Toast.makeText(homeActivity, "Parental Control is already off", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void getSongNames(){
        songNameList = homeActivity.songNamesToDisplay.clone();
    }


    public void populateSongListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.songlist_element, homeActivity.songNamesToDisplay);
        activitySongList = (ListView) getView().findViewById(R.id.songList);
        activitySongList.setAdapter(adapter);
        registerForContextMenu(activitySongList);
    }

    public void registerOnClickForSonglist(){
        activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
                Log.i(TAG, "Clicked on "+selectedSongName);
                //get the song object associated with the song name that was clicked
                Song selectedSong = LookUpSongs.getSong(sList, selectedSongName);

                homeActivity.playSong(selectedSong);

            }
        });// on item click listener for listview
    }


    public void registerOnClickForNowPlayingButton(){
        //get reference to the now playing song gui
        nowPlayingSongGui = (Button) getView().findViewById(R.id.song_name);

        nowPlayingSongGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerState.getInstance().isSongPaused() || MusicPlayerState.getInstance().isSongPlaying()) {
                    homeActivity.showNowPlayingFragment();
                }else{
                    Log.e(TAG, "no song playing or paused");
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(sList.get(info.position).getName());
            menu.add(Menu.NONE, 0,0, "Add to Queue");
            menu.add(Menu.NONE, 1,1, "Play Next");
            String thirdMenuItem = "error";

            if(!MusicPlayerState.getInstance().getParentalControlModeOn()){  //can only flag songs if not in parental control mode on

                boolean songIsFlagged = ServiceGateway.getSongFlagger().songIsFlagged(sList.get(info.position));
                if(songIsFlagged){
                    menu.add(Menu.NONE, 2, 2, "Remove song flag");
                    thirdMenuItem = "Remove song flag";
                }else{
                    menu.add(Menu.NONE, 2, 2, "Flag Song");
                    thirdMenuItem = "Flag Song";
                }
            }

            menuItems[0]= "Add to Queue";
            menuItems[1]= "Play Next";
            menuItems[2] = thirdMenuItem;
    }

    // TODO: 27/03/19  callback for context menu
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String listItemName = sList.get(info.position).getName();

        switch(item.getItemId()) {
            case 0:
                MusicPlayerState.getInstance().addToQueue(LookUpSongs.getSong(sList, listItemName));
                homeActivity.queueFragSongsDisplay = MusicPlayerState.getInstance().getQueueSongNames(); //refresh the songs to be displayed
                return true;
            case 1:
                MusicPlayerState.getInstance().addSongToPlayNext(LookUpSongs.getSong(sList, listItemName));
                homeActivity.queueFragSongsDisplay = MusicPlayerState.getInstance().getQueueSongNames();
                return true;
            case 2:
                if(!MusicPlayerState.getInstance().getParentalControlModeOn()){    //only proceed if parental control is not on
                    boolean songIsFlagged = ServiceGateway.getSongFlagger().songIsFlagged(LookUpSongs.getSong(sList, listItemName));
                    if(songIsFlagged){
                        ServiceGateway.getSongFlagger().flagSong(LookUpSongs.getSong(sList, listItemName), false); //if the song was flagged, it means we are unflagging it
                    }else{
                        ServiceGateway.getSongFlagger().flagSong(LookUpSongs.getSong(sList, listItemName), true);
                    }
                }

            default:
                return super.onContextItemSelected(item);
        }

    }



    private void showPINInputDialog(boolean turnOn) {
        final EditText taskEditText = new EditText(homeActivity);
        AlertDialog dialog = new AlertDialog.Builder(homeActivity)
                .setTitle("Parental Lock")
                .setMessage("What is your PIN?")
                .setView(taskEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pin = String.valueOf(taskEditText.getText());
                        CredentialManager credentialManager = ServiceGateway.getCredentialManager();
                        boolean correctPIN = credentialManager.validatePIN(pin);

                        if(correctPIN){

                            if(turnOn){
                                MusicPlayerState.getInstance().turnParentalControlOn(true); //parental control mode activated
                                Toast.makeText(homeActivity, "Parental Control mode activated", Toast.LENGTH_LONG).show();
                            }else{
                                MusicPlayerState.getInstance().turnParentalControlOn(false); //turn it off
                                Toast.makeText(homeActivity, "Parental Control mode deactivated", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(homeActivity, "Incorrect PIN, please try again", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNeutralButton("Forgot Password?", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        homeActivity.showPINResetFragment();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }



}
