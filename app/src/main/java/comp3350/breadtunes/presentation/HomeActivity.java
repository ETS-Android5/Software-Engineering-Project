package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.HomeActivityHelper;
import comp3350.breadtunes.business.MediaPlayerController;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


//==============================
// HELPFUL DOCUMENTATION
//    // Media Player class https://developer.android.com/reference/android/media/MediaPlayer
//    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
//    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java
//==============================



public class HomeActivity extends Activity {


    MediaPlayerController mediaPlayerController;  //business layer objects that help presentation layer carry out operations
    MusicPlayerState musicPlayerState ; //business layer object that contains the current state of the music player
    HomeActivityHelper homeActivityHelper; //populates this activity and provides small utilities such as

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        musicPlayerState = new MusicPlayerState();
        mediaPlayerController = new MediaPlayerController(HomeActivity.this, musicPlayerState); //init logic layer
        homeActivityHelper = new HomeActivityHelper();

        String[] songNames = homeActivityHelper.getSongNames();  //get the names of all songs to be displayed in the ListView

        //create adapter to populate list items in the listView in the main activity
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.songlist_element, songNames);
        final ListView activitySongList = (ListView) findViewById(R.id.songList);
        activitySongList.setAdapter(adapter); //populate the items!


        //set on item click listener to react to list clicks
        activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
               Toast.makeText(HomeActivity.this, "Playing "+selectedSongName, Toast.LENGTH_SHORT).show();

               //get the song object associated with the songname that was clicked
               Song selectedSong = homeActivityHelper.getSong(selectedSongName);

               if(selectedSong != null) {
                   int songId = getResources().getIdentifier(selectedSong.getRawName(), "raw", getPackageName());
                   String playStatus = mediaPlayerController.playSong(selectedSong, songId);
                   Toast.makeText(HomeActivity.this, playStatus, Toast.LENGTH_SHORT).show(); //song not found
               }
           }
       });// on item click listener for listview

    }//on create



    protected void onDestroy() {
        mediaPlayerController.releaseMediaPlayer();
        super.onDestroy();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    public void onClickPause(View view){
        String response = mediaPlayerController.pauseSong();
        Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
    }



    public void onClickResume(View view) {

        //make sure a song is actually paused
        if (musicPlayerState.isSongPaused()) {
            Song pausedSong = musicPlayerState.getCurrentlyPlayingSong();   //get the current playing song from the app state
            int resourceId = getResources().getIdentifier(pausedSong.getRawName(), "raw", getPackageName());    //get the resource pointer
            String response = mediaPlayerController.resumeSong(resourceId);                 // ask  media controller to resume
            Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show(); //display result of operation
        }else{
            Toast.makeText(HomeActivity.this, "No song to resume", Toast.LENGTH_SHORT).show();
        }
    }




    /*
    public void buttonStudentsOnClick(View v) {
        Intent studentsIntent = new Intent(HomeActivity.this, StudentsActivity.class);
        HomeActivity.this.startActivity(studentsIntent);
    }

    public void buttonCoursesOnClick(View v) {
        Intent coursesIntent = new Intent(HomeActivity.this, CoursesActivity.class);
        HomeActivity.this.startActivity(coursesIntent);
    }
    */
}
