package comp3350.breadtunes.presentation.base;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Partial credit due to Karim Abou Zeid for his work on the Phonograph app:
 * https://github.com/kabouzeid/Phonograph
 */
public class BaseActivity extends Activity {

    private final Permission[] requiredPermissions = {
            new Permission(Manifest.permission.READ_EXTERNAL_STORAGE, "Permission to read external storage has been denied.")
    };

    private ArrayList<Permission> permissionsNotAccepted = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getPermissionsNotAccepted();
        requestPermissions();
    }

    private void getPermissionsNotAccepted() {

    }

    private void requestPermissions() {
        for (Permission permission : requiredPermissions) {

        }
    }

    protected class Permission {
        protected String permissionName;
        protected String permissionDeniedMessage;

        protected Permission(String name, String deniedMessage) {
            permissionName = name;
            permissionDeniedMessage = deniedMessage;
        }
    }
}
