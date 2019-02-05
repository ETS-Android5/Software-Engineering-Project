package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
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


public class HomeActivity extends Activity {

    // Media Player class https://developer.android.com/reference/android/media/MediaPlayer
    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java

    boolean songPlaying = false;
    boolean songPaused = false;
    MediaPlayer mediaPlayer;


    MusicPlayerState logicLayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logicLayer = new MusicPlayerState(); // initialize the music players state
        String[] songNames = logicLayer.getSongNames();  //get the names of all songs to be displayed in the ListView

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
               Song selectedSong = logicLayer.getSong(selectedSongName);

               if(selectedSong != null) {
                   int songId = getResources().getIdentifier(selectedSong.getRawName(), "raw", getPackageName());
                   String playStatus = logicLayer.playSong(selectedSong,HomeActivity.this,songId);
                   Toast.makeText(HomeActivity.this, playStatus, Toast.LENGTH_LONG).show(); //song not found
               }
           }
       });// on item click listener for listview

    }//on create






    @Override
    protected void onDestroy() {
        mediaPlayer.release(); //
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

//

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    public void onClickPause(View view){

        if(songPlaying){
            mediaPlayer.pause();
            songPaused = true;
        }else{
            Toast.makeText(HomeActivity.this, "No song playing", Toast.LENGTH_LONG).show();
        }

    }



    public void onCLickResume(View view){

        if(songPaused){
            //resume!

            songPlaying = true;
        }else{
            Toast.makeText(HomeActivity.this, "Song is not paused", Toast.LENGTH_LONG).show();
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
