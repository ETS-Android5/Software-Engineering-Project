package comp3350.breadtunes.presentation.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 * Partial credit due to Karim Abou Zeid for his work on the Phonograph app:
 * https://github.com/kabouzeid/Phonograph
 */
public class BaseActivity extends Activity {
    private static final int MY_PERMISSION_READ_EXTERNAL_REQUEST = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        requestReadExternalStoragePermission();
    }

    protected void requestReadExternalStoragePermission() {
        String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        // If build version is at least Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(readExternalStoragePermission) != PackageManager.PERMISSION_GRANTED) {
                // User wants to see the rationale for accepting read external storage
                if (shouldShowRequestPermissionRationale(readExternalStoragePermission)) {
                    Toast.makeText(getApplicationContext(),
                            "Permission is required to read your audio files.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                // User does not want to see rationale, just ask for permission
                else {
                    String[] permissions = {readExternalStoragePermission};
                    requestPermissions(permissions, MY_PERMISSION_READ_EXTERNAL_REQUEST);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_READ_EXTERNAL_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We can now read the external storage
                }
            }
        }
    }
}
