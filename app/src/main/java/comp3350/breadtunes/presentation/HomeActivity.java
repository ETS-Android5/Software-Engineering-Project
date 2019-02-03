package comp3350.breadtunes.presentation;

import comp3350.breadtunes.R;
import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.business.SongInfoReader;
import comp3350.breadtunes.persistence.SongPersistence;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TODO: Remove
        try {
            // Open mp3 asset and create a temporary file for it
            AssetManager assetManager = getAssets();
            InputStream mp3FilePerth = assetManager.open("perth.mp3");
            File tempFile = File.createTempFile("perth", ".mp3");
            tempFile.deleteOnExit();
            FileUtils.copyToFile(mp3FilePerth, tempFile);

            // Try to get tags
            ID3v1 id3v1Tag = SongInfoReader.getID3v1Tag(tempFile);

            if (id3v1Tag == null) {
                ID3v2 id3v2Tag = SongInfoReader.getID3v2Tag(tempFile);

                String artist = id3v2Tag.getArtist();
                String track = id3v2Tag.getTrack();
                String name = id3v2Tag.getTitle();
                int i = 0;
            }

        } catch (Exception e) { }
        // TODO: End Remove

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);


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


    public void playSong(View view){
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
