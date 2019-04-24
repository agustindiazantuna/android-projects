package com.example.test09.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.example.test09.R;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;



/* ------------------------------------------------------------------------------------------------
 * -- Don't forget...
 * ------------------------------------------------------------------------------------------------
 * Dependencies to use MediaSessionCompat must be on 'dependencies' list
 * on file app.gradle
 *
 *      implementation 'com.android.support:support-compat:28.0.0'
 *      implementation 'com.android.support:support-media-compat:28.0.0'
 *
 */

public class MusicService extends MediaBrowserServiceCompat {

    /* --------------------------------------------------------------------------------------------
     * -- tag
     * --------------------------------------------------------------------------------------------
     */
    private static String TAG = "my_app_09 - " + MusicService.class.getSimpleName() + "\t\t";




    /* --------------------------------------------------------------------------------------------
     * -- variable
     * --------------------------------------------------------------------------------------------
     */
    private MediaSessionCompat mSession;
    private MediaSessionCallback mCallback;
    private MediaPlayer mPlayback;
    private final Context mApplicationContext;
    private final AudioManager mAudioManager;
    private final AudioFocusHelper mAudioFocusHelper;
    private boolean mPlayOnAudioFocus = false;



    
    /* --------------------------------------------------------------------------------------------
     * -- callback
     * --------------------------------------------------------------------------------------------
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate...");

        // Creates a new MediaSession and MediaSessionCallback
        mSession = new MediaSessionCompat(this, "MusicService");
        mCallback = new MediaSessionCallback();

        // Bind mCallback with mSession
        // The callback class will receive all the user's actions, like play, pause, etc
        mSession.setCallback(mCallback);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        // Connect the MediaBrowserService to the mSession. Allows the MediaBrowser (client)
        // to work with the mSession
        setSessionToken(mSession.getSessionToken());

        // Create object MediaPlayer
        mPlayback = new MediaPlayer();


        // TODO: Add the following stuff
        // - MediaNotificationManager


        // Handles headphones coming unplugged. cannot be done through a manifest receiver
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(mNoisyReceiver, filter);

        Log.d(TAG, "...onCreate \t\t OK!");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        mSession.release();
        mPlayback.release();

        // TODO: Release
        // - MediaNotificationManager

        Log.d(TAG, "onDestroy -> OK");
    }




    /* --------------------------------------------------------------------------------------------
     * -- do not change this methods
     * --------------------------------------------------------------------------------------------
     */
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName,
                                 int clientUid,
                                 Bundle rootHints) {
        return new BrowserRoot(getString(R.string.app_name), null);
    }

    @Override
    public void onLoadChildren(
            @NonNull final String parentMediaId,
            @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(null);
    }




    /* --------------------------------------------------------------------------------------------
     * -- class
     * --------------------------------------------------------------------------------------------
     */
    // MediaSession Callback: Transport Controls -> MediaPlayerAdapter
    public class MediaSessionCallback extends MediaSessionCompat.Callback {

        private List<String> mSongList = Arrays.asList("inmigrant_song", "celebration_day",
                "la_culpa_al_viento");
        private int mListIndex = 0;


        @Override
        public void onPrepare() {
            Log.d(TAG, "onPrepare");

            // TODO: METADATA_KEY_ don't work
            // option 1: uri
            // option 2: fd
            // option 3: path

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//            FileDescriptor fd = getResources().openRawResourceFd(R.raw.la_culpa_al_viento).getFileDescriptor();
//            mmr.setDataSource(fd);
            mmr.setDataSource("/sdcard/Music/madera_prohibida/la_culpa_al_viento.mp3");

            String songName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songName)
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artistName)
                    .build();
            mSession.setMetadata(metadata);

            try {
                mPlayback.setDataSource(MusicService.this, Uri.parse(
                        "android.resource://com.example.test09/raw/" + mSongList.get(mListIndex)));
            } catch (Exception e) {
                Log.e(TAG, e.toString() + " - setDataSource");
            }

            try {
                mPlayback.prepare();
            } catch (Exception e) {
                Log.e(TAG, e.toString() + " - prepare");
            }

            // TODO: MediaSession.isActive() is needful?
//            if (!mSession.isActive()) {
//                mSession.setActive(true);
//            }
        }

        @Override
        public void onPlay() {
            Log.d(TAG, "onPlay");

            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);

            onPrepare();
            mPlayback.start();
        }

        @Override
        public void onPause() {
            Log.d(TAG, "onPause");

            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);

            mPlayback.pause();
        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop");

            setMediaPlaybackState(PlaybackStateCompat.STATE_STOPPED);

            mPlayback.reset();
            mPlayback.stop();
//            mSession.setActive(false);
        }

        @Override
        public void onSkipToNext() {
            Log.d(TAG, "onSkipToNext");
            mListIndex = (++mListIndex % mSongList.size());

            onStop();
            onPlay();
        }

        @Override
        public void onSkipToPrevious() {
            Log.d(TAG, "onSkipToPrevious");
            mListIndex = mListIndex > 0 ? mListIndex - 1 : mSongList.size() - 1;

            onStop();
            onPlay();
        }
    }


    // TODO: Not working
    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if( mPlayback != null && mPlayback.isPlaying() ) {
                mPlayback.pause();
            }
        }
    };





    /* --------------------------------------------------------------------------------------------
     * -- methods
     * --------------------------------------------------------------------------------------------
     */
    private void setMediaPlaybackState(int state) {
        PlaybackStateCompat.Builder playBackstateBuilder = new PlaybackStateCompat.Builder();

        if( state == PlaybackStateCompat.STATE_PLAYING ) {
            playBackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY);
        } else if ( state == PlaybackStateCompat.STATE_PAUSED ) {
            playBackstateBuilder.setActions(PlaybackStateCompat.ACTION_PAUSE);
        } else {
            playBackstateBuilder.setActions(PlaybackStateCompat.ACTION_STOP);
        }


        playBackstateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
        mSession.setPlaybackState(playBackstateBuilder.build());
    }

    // Only will work on target Android >= O 8.0
    private void audioFocus ( Context mContext ) {
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        AudioAttributes mAudioAttributes =
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();

        // AUDIOFOCUS_GAIN -> permanent audio focus
        AudioFocusRequest mAudioFocusRequest =
                new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(mAudioAttributes)
                        .setAcceptsDelayedFocusGain(true)
                        .setOnAudioFocusChangeListener(...) // Need to implement listener
               .build();

        int focusRequest = mAudioManager.requestAudioFocus(mAudioFocusRequest);

        switch (focusRequest) {
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                // don’t start playback
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                // actually start playback
        }
    }




    /**
     * Helper class for managing audio focus related tasks.
     */
    private final class AudioFocusHelper
            implements AudioManager.OnAudioFocusChangeListener {

        private boolean requestAudioFocus() {
            final int result = mAudioManager.requestAudioFocus(this,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }

        private void abandonAudioFocus() {
            mAudioManager.abandonAudioFocus(this);
        }

        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (mPlayOnAudioFocus && !isPlaying()) {
                        play();
                    } else if (isPlaying()) {
                        setVolume(MEDIA_VOLUME_DEFAULT);
                    }
                    mPlayOnAudioFocus = false;
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    setVolume(MEDIA_VOLUME_DUCK);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (isPlaying()) {
                        mPlayOnAudioFocus = true;
                        pause();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mAudioManager.abandonAudioFocus(this);
                    mPlayOnAudioFocus = false;
                    stop();
                    break;
            }
        }
    }
}
