package comp3350.breadtunes.presentation;

import comp3350.breadtunes.R;
import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends Activity {


    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Get the list of songs from application services
        Services services = new Services();
        final SongPersistenceStub songPersistenceStub = (SongPersistenceStub) services.getSongPersistence(); //get interface for getting songs from persistance

        final List<Song> songList = songPersistenceStub.getAll(); //get all songs in persistance
        String[] songNames = new String[songList.size()]; //array where all song names will be stored

        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }

        //create adapter to populate list items in the listView in the main activity
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.songlist_element, songNames);
        final ListView activitySongList = (ListView) findViewById(R.id.songList);
        activitySongList.setAdapter(adapter); //populate the items!


        //set on item click listener to know which song is being played
       activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
               Toast.makeText(HomeActivity.this, "Playing "+selectedSongName, Toast.LENGTH_SHORT).show();

               //get the song object associated with the songname that was clicked
               Song selectedSong = songPersistenceStub.getSong(selectedSongName);

               if(selectedSong != null) {
                   String songRawName = selectedSong.getRawName();// get the rawName of the song in order to find its Id
                   Toast.makeText(HomeActivity.this, "RawName is "+songRawName, Toast.LENGTH_LONG).show();
                   int songId = getResources().getIdentifier(songRawName, "raw", getPackageName());
                   if (songId == 0) {
                       Toast.makeText(HomeActivity.this, "Failed to find id", Toast.LENGTH_LONG).show(); //song not found
                   } else {
                       MediaPlayer mediaPlayer = MediaPlayer.create(HomeActivity.this, songId); //song found, play!
                       mediaPlayer.start();
                   }
               }
           }
       });

    }//on create

    @Override
    protected void onDestroy() {
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


    public void onClickPlaySongButton(View view){
        Toast.makeText(HomeActivity.this, "play music clicked!", Toast.LENGTH_SHORT).show();
        MediaPlayer mediaPlayer = MediaPlayer.create(HomeActivity.this, R.raw.nocturne);
        mediaPlayer.start();

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
