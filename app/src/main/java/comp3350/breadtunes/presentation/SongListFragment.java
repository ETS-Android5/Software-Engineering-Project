package comp3350.breadtunes.presentation;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.Service;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import comp3350.breadtunes.services.ObservableService;
import comp3350.breadtunes.services.ServiceGateway;

import static comp3350.breadtunes.presentation.HomeActivity.sList;

// REFERENCE : https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments
public class SongListFragment extends Fragment implements Observer {


    public HomeActivity homeActivity;
    ListView activitySongList;
    String[] songNameList;
    private final String TAG = "SongListFragment";
    public static TextView parentalControlStatus;
    public static Button nowPlayingSongGui;
    String[] menuItems = new String[4];
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
        // Set subscriptions
        ObservableService.subscribeToSongChanges(this);
        ObservableService.subscribeToParentalModeStatus(this);
        ObservableService.subscribeToPlayModeChange(this);
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }



    public void onViewCreated(View view, Bundle savedInstanceState){
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
        parentalControlStatus = (TextView) getView().findViewById(R.id.parental_control_status);  //set play button according to the playing mode

        if(ServiceGateway.getMusicPlayerState().isSongPlaying()){
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.pause);
        }else{
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.play);
        }
    }

    public void onResume(){
        super.onResume();

        // Set subscriptions
        ObservableService.subscribeToSongChanges(this);
        ObservableService.subscribeToParentalModeStatus(this);
        ObservableService.subscribeToPlayModeChange(this);

        getSongNames();
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();

        MusicPlayerState mps = ServiceGateway.getMusicPlayerState();

        parentalControlStatus = (TextView) getView().findViewById(R.id.parental_control_status);
        parentalControlStatus.setText(mps.getParentalControlStatus());
        nowPlayingSongGui.setText(mps.getCurrentlyPlayingSongName() + "\n" + mps.getPlayMode());

        if(ServiceGateway.getMusicPlayerState().isSongPlaying()){
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.pause);
        }else{
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.play);
        }

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
            Song song = songObservable.getValue();
            String songName = song.getName();
            nowPlayingSongGui.setText(songName + "\n" + ServiceGateway.getMusicPlayerState().getPlayMode());
        }else if(observable instanceof PlayModeObservable){

            PlayModeObservable playModeObservable = (PlayModeObservable) observable;
            String playMode = playModeObservable.getValue();
            String songName = ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName();
            nowPlayingSongGui.setText(songName+"\n"+playMode);
        } else if (observable instanceof ParentalControlStatusObservable) {

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

                // Only proceed if parental control is off
                if (!ServiceGateway.getMusicPlayerState().getParentalControlModeOn()) {
                    showPINInputDialog(true);
                } else {
                    Toast.makeText(homeActivity, "Parental Control is already on", Toast.LENGTH_LONG).show();
                }
            }else{
                homeActivity.showParentalControlSetupFragment();
            }
        }
        else if(id == R.id.parental_lock_off){
            // Only proceed if parental control is on
            if (ServiceGateway.getMusicPlayerState().getParentalControlModeOn()){
                showPINInputDialog(false);
            } else {
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

                ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
                button.setImageResource(R.drawable.pause);

                homeActivity.playSong(selectedSong);

            }
        });// on item click listener for listview
    }



    public void registerOnClickForNowPlayingButton(){
        //get reference to the now playing song gui
        nowPlayingSongGui = (Button) getView().findViewById(R.id.song_name);

        nowPlayingSongGui.setOnClickListener(view -> {
            MusicPlayerState mps = ServiceGateway.getMusicPlayerState();
            if (mps.isSongPaused() || mps.isSongPlaying()) {
                homeActivity.showNowPlayingFragment();
            } else {
                Log.e(TAG, "no song playing or paused");
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
            MusicPlayerState mps = ServiceGateway.getMusicPlayerState() ;

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(mps.getCurrentSongList().get(info.position).getName());
            menu.add(Menu.NONE, 0,0, "Add to Queue");
            menu.add(Menu.NONE, 1,1, "Play Next");

            if(!mps.getParentalControlModeOn()){
                menu.add(Menu.NONE, 2, 2, "Flag song");
                menu.add(Menu.NONE, 3, 3, "Remove song flag");
                menuItems[2] ="Flag song";
                menuItems[3] = "Remove song flag";
            }

            menuItems[0]= "Add to Queue";
            menuItems[1]= "Play Next";
    }


    public boolean onContextItemSelected(MenuItem item){
        MusicPlayerState mps = ServiceGateway.getMusicPlayerState();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String listItemName = mps.getCurrentSongList().get(info.position).getName();

        switch(item.getItemId()) {
            case 0:
                mps.addToQueue(LookUpSongs.getSong(sList, listItemName));
                homeActivity.queueFragSongsDisplay = mps.getQueueSongNames(); //refresh the songs to be displayed
                return true;
            case 1:
                mps.addSongToPlayNext(LookUpSongs.getSong(sList, listItemName));
                homeActivity.queueFragSongsDisplay = mps.getQueueSongNames();
                return true;
            case 2:
                // Add flag if parental control is off
                if(!mps.getParentalControlModeOn()){
                    boolean songIsFlagged = ServiceGateway.getSongFlagger().songIsFlagged(LookUpSongs.getSong(sList, listItemName));
                    if(!songIsFlagged){
                        ServiceGateway.getSongFlagger().flagSong(LookUpSongs.getSong(sList, listItemName), true);
                    }else{
                        Toast.makeText(homeActivity, "Song is already flagged", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case 3:
                // Remove flag if parental control is off
                if(!mps.getParentalControlModeOn()){
                    boolean songIsFlagged = ServiceGateway.getSongFlagger().songIsFlagged(LookUpSongs.getSong(sList, listItemName));
                    if(songIsFlagged){
                        ServiceGateway.getSongFlagger().flagSong(LookUpSongs.getSong(sList, listItemName), false);
                    }else{
                        Toast.makeText(homeActivity, "Song has no flag to remove", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
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
                .setPositiveButton("Submit", (dialogPositive, which) -> {
                    String pin = String.valueOf(taskEditText.getText());
                    CredentialManager credentialManager = ServiceGateway.getCredentialManager();
                    boolean correctPIN = credentialManager.validatePIN(pin);

                    if(correctPIN){

                        if(turnOn){
                            ServiceGateway.getMusicPlayerState().turnParentalControlOn(true); //parental control mode activated
                            Toast.makeText(homeActivity, "Parental Control mode activated", Toast.LENGTH_LONG).show();
                        }else{
                            ServiceGateway.getMusicPlayerState().turnParentalControlOn(false); //turn it off
                            Toast.makeText(homeActivity, "Parental Control mode deactivated", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(homeActivity, "Incorrect PIN, please try again", Toast.LENGTH_LONG).show();
                    }

                })
                .setNeutralButton("Forgot Password?", (dialogNeutral, which) -> homeActivity.showPINResetFragment())
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    //used to update buttons if the state is changed in another fragment
    public void updateButtons(){
        if(ServiceGateway.getMusicPlayerState().isSongPlaying()){
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.pause);
        }else{
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause);
            button.setImageResource(R.drawable.play);
        }
    }

}
