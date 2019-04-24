package com.example.test09.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test09.R;
import com.example.test09.service.MusicService;

import java.util.List;


/* ------------------------------------------------------------------------------------------------
 * -- Test 09 --                                                    MediaSession + MediaController
 * ------------------------------------------------------------------------------------------------
 *
 * This test implements a simple audio player using dedicated media classes like:
 *      - MediaBrowserService
 *      - MediaController
 *
 */

public class MainActivity extends AppCompatActivity {

    /* --------------------------------------------------------------------------------------------
     * -- tag
     * --------------------------------------------------------------------------------------------
     */
    private static String TAG = "my_app_09 - " + MainActivity.class.getSimpleName() + "\t\t";
    private final int PREQUEST_READ_EXT_STO = 9;



    /* --------------------------------------------------------------------------------------------
     * -- variable
     * --------------------------------------------------------------------------------------------
     */
    // layout
    private TextView tvStatus, tvSong, tvArtist;

    // media player
    private MediaBrowserHelper mMediaBrowserHelper;




    /* --------------------------------------------------------------------------------------------
     * -- callback
     * --------------------------------------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate...");

        // Check for permissions
        checkForPermission();


        // Bind layout to private variables
        tvStatus = (TextView) findViewById(R.id.textView1);
        tvSong = (TextView) findViewById(R.id.textView);
        tvArtist = (TextView) findViewById(R.id.textView4);


        // Load SERVICE CLASS -> in this example: MusicService
        mMediaBrowserHelper = new MediaBrowserConnection(this);

        // Register CONTROLLER CALLBACKS (without this step, the session cannot send metadata to the
        // controller)
        mMediaBrowserHelper.registerCallback(new MediaBrowserListener());

        // Connect with SERVICE callbacks
        mMediaBrowserHelper.onStart();

        Log.d(TAG, "...onCreate \t\t OK!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

//        mMediaBrowserHelper.onStop();
    }




    /* --------------------------------------------------------------------------------------------
     * -- method
     * --------------------------------------------------------------------------------------------
     */
    public void playButton (View view) {
        Log.d(TAG, "\n");   Log.d(TAG, "playButton");
        mMediaBrowserHelper.getTransportControls().play();
    }

    public void pauseButton (View view) {
        Log.d(TAG, "\n");   Log.d(TAG, "pauseButton");
        mMediaBrowserHelper.getTransportControls().pause();
    }

    public void stopButton (View view) {
        Log.d(TAG, "\n");   Log.d(TAG, "stopButton");
        mMediaBrowserHelper.getTransportControls().stop();
    }

    public void prevButton (View view) {
        Log.d(TAG, "\n");   Log.d(TAG, "prevButton");
        mMediaBrowserHelper.getTransportControls().skipToPrevious();
    }

    public void nextButton (View view) {
        Log.d(TAG, "\n");   Log.d(TAG, "nextButton");
        mMediaBrowserHelper.getTransportControls().skipToNext();
    }

    private void checkForPermission ( ) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PREQUEST_READ_EXT_STO);
        } else {
            // notification
            Toast notification = Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG);
            notification.show();
        }
    }




    /* --------------------------------------------------------------------------------------------
     * -- class
     * --------------------------------------------------------------------------------------------
     */

    private class MediaBrowserConnection extends MediaBrowserHelper {

        // Constructor defines the SERVICE CLASS name (media session)
        private MediaBrowserConnection(Context context) {
            super(context, MusicService.class);
            Log.d(TAG, "MediaBrowserConnection");
        }

        // TODO: Implement SeekBarAudio
//        @Override
//        protected void onConnected(@NonNull MediaControllerCompat mediaController) {
//            mSeekBarAudio.setMediaController(mediaController);
//        }

        @Override
        protected void onChildrenLoaded(@NonNull String parentId,
                                        @NonNull List<MediaBrowserCompat.MediaItem> children) {
            super.onChildrenLoaded(parentId, children);
            Log.d(TAG, "onChildrenLoaded");

            final MediaControllerCompat mediaController = getMediaController();

//            // Queue up all media items for this simple sample.
//            for (final MediaBrowserCompat.MediaItem mediaItem : children) {
//                mediaController.addQueueItem(mediaItem.getDescription());
//            }

            // Call prepare now so pressing play just works.
            mediaController.getTransportControls().prepare();
        }
    }




    /**
     * Implementation of the {@link MediaControllerCompat.Callback} methods we're interested in.
     * <p>
     * Here would also be where one could override
     * {@code onQueueChanged(List<MediaSessionCompat.QueueItem> queue)} to get informed when items
     * are added or removed from the queue. We don't do this here in order to keep the UI
     * simple.
     */
    private class MediaBrowserListener extends MediaControllerCompat.Callback {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat playbackState) {
            Log.d(TAG, "onPlaybackStateChanged");
            long mPlayerStatus = ( playbackState != null ) ? playbackState.getActions() :
                                                        PlaybackStateCompat.ACTION_STOP;

            if (mPlayerStatus == PlaybackStateCompat.ACTION_PLAY)
                tvStatus.setText("PLAY");
            else if (mPlayerStatus == PlaybackStateCompat.ACTION_PAUSE)
                tvStatus.setText("PAUSE");
            else
                tvStatus.setText("STOP");
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadata) {
            Log.d(TAG, "onMetadataChanged");
            if (mediaMetadata == null) {
                return;
            }
            tvSong.setText(
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            tvArtist.setText(
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PREQUEST_READ_EXT_STO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // notification
                    Toast notificacion = Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG);
                    notificacion.show();
                } else {
                    // notification
                    Toast notificacion = Toast.makeText(this, "Permissions denied", Toast.LENGTH_LONG);
                    notificacion.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}



