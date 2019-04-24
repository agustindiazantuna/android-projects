package com.example.test09.activity;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompat.Callback;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;




public class MediaBrowserHelper {

    /* --------------------------------------------------------------------------------------------
     * -- tag
     * --------------------------------------------------------------------------------------------
     */
    private static String TAG = "my_app_09 - " + MediaBrowserHelper.class.getSimpleName() + "\t\t";




    /* --------------------------------------------------------------------------------------------
     * -- variable
     * --------------------------------------------------------------------------------------------
     */
    private final Context mContext;
    private final Class<? extends MediaBrowserServiceCompat> mMediaBrowserServiceClass;

    private final List<Callback> mCallbackList = new ArrayList<>();

    private final MediaBrowserConnectionCallback mMediaBrowserConnectionCallback;
    private final MediaControllerCallback mMediaControllerCallback;
    private final MediaBrowserSubscriptionCallback mMediaBrowserSubscriptionCallback;

    // Browses media content offered by a link MediaBrowserService.
    private MediaBrowserCompat mMediaBrowser;

    @Nullable
    private MediaControllerCompat mMediaController;




    /* --------------------------------------------------------------------------------------------
     * -- constructor
     * --------------------------------------------------------------------------------------------
     */
    public MediaBrowserHelper(Context context,
                              Class<? extends MediaBrowserServiceCompat> serviceClass) {
        Log.d(TAG, "MediaBrowserHelper -> constructor");

        mContext = context;

        // SERVICE CLASS -> MediaBrowserServiceCompat
        mMediaBrowserServiceClass = serviceClass;

        mMediaBrowserConnectionCallback = new MediaBrowserConnectionCallback();
        mMediaControllerCallback = new MediaControllerCallback();
        mMediaBrowserSubscriptionCallback = new MediaBrowserSubscriptionCallback();
    }




    /* --------------------------------------------------------------------------------------------
     * -- callback
     * --------------------------------------------------------------------------------------------
     */
    public void onStart() {
        Log.d(TAG, "onStart...");

        // Connect MediaBrowser (client) with MediaBrowserService
        if (mMediaBrowser == null) {

            // CREATES SERVICE!!!
            mMediaBrowser = new MediaBrowserCompat(
                                mContext,
                                new ComponentName(mContext, mMediaBrowserServiceClass),
                                mMediaBrowserConnectionCallback,
                                null);
            mMediaBrowser.connect();
        }

        Log.d(TAG, "...onStart \t\t OK!");
    }




    /* --------------------------------------------------------------------------------------------
     * -- method
     * --------------------------------------------------------------------------------------------
     */
    public void registerCallback(Callback callback) {
        Log.d(TAG, "registerCallback");

        if (callback != null) {
            mCallbackList.add(callback);
        }
    }

    private void performOnAllCallbacks(@NonNull CallbackCommand command) {
        Log.d(TAG, "performOnAllCallbacks");
        for (Callback callback : mCallbackList) {
            if (callback != null) {
                command.perform(callback);
            }
        }
    }




    public MediaControllerCompat.TransportControls getTransportControls() {
        Log.d(TAG, "getTransportControls");
        if (mMediaController == null) {
            Log.d(TAG, "getTransportControls: MediaController is null!");
            throw new IllegalStateException("MediaController is null!");
        }
        return mMediaController.getTransportControls();
    }




    /* --------------------------------------------------------------------------------------------
     * -- class
     * --------------------------------------------------------------------------------------------
     */
    // Receives callbacks from the MediaBrowser when it has successfully connected to the
    // MediaBrowserService (MusicService).
    private class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {

        // Happens as a result of onStart()
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected...");
            try {
                // Get a MediaController for the MediaSession.
                mMediaController =
                        new MediaControllerCompat(mContext, mMediaBrowser.getSessionToken());
                mMediaController.registerCallback(mMediaControllerCallback);

                // Sync existing MediaSession state to the UI.
                mMediaControllerCallback.onMetadataChanged(mMediaController.getMetadata());
                mMediaControllerCallback.onPlaybackStateChanged(
                        mMediaController.getPlaybackState());

            } catch (RemoteException e) {
                Log.d(TAG, String.format("onConnected: Problem: %s", e.toString()));
                throw new RuntimeException(e);
            }

            mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mMediaBrowserSubscriptionCallback);

            Log.d(TAG, "...onConnected \t\t OK!");
        }
    }


    // Receives callbacks from the MediaController and updates the UI state,
    // i.e.: Which is the current item, whether it's playing or paused, etc.
    private class MediaControllerCallback extends MediaControllerCompat.Callback {

        // Media controller makes callback of this method
        @Override
        public void onMetadataChanged(final MediaMetadataCompat metadata) {
            Log.d(TAG, "onMetadataChanged");

            performOnAllCallbacks(new CallbackCommand() {
                @Override
                public void perform(@NonNull Callback callback) {
                    callback.onMetadataChanged(metadata);
                }
            });
        }


        @Override
        public void onPlaybackStateChanged(@Nullable final PlaybackStateCompat state) {
            Log.d(TAG, "onPlaybackStateChanged");

            performOnAllCallbacks(new CallbackCommand() {
                @Override
                public void perform(@NonNull Callback callback) {
                    callback.onPlaybackStateChanged(state);
                }
            });
        }
    }








    /* --------------------------------------------------------------------------------------------
     * -- etc
     * --------------------------------------------------------------------------------------------
     */
    // Receives callbacks from the MediaBrowser when the MediaBrowserService has loaded new media
    // that is ready for playback.
    public class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback {

        @Override
        public void onChildrenLoaded(@NonNull String parentId,
                                     @NonNull List<MediaBrowserCompat.MediaItem> children) {
            Log.d(TAG, "onChildrenLoaded");
            MediaBrowserHelper.this.onChildrenLoaded(parentId, children);
        }
    }

    /**
     * Called after loading a browsable {@link MediaBrowserCompat.MediaItem}
     *
     * @param parentId The media ID of the parent item.
     * @param children List (possibly empty) of child items.
     */
    protected void onChildrenLoaded(@NonNull String parentId,
                                    @NonNull List<MediaBrowserCompat.MediaItem> children) {
        Log.d(TAG, "onChildrenLoaded");
    }

    /**
     * Helper for more easily performing operations on all listening clients.
     */
    private interface CallbackCommand {
        void perform(@NonNull Callback callback);
    }

    // Need to convert data
    @NonNull
    protected final MediaControllerCompat getMediaController() {
        Log.d(TAG, "getMediaController");
        if (mMediaController == null) {
            throw new IllegalStateException("MediaController is null!");
        }
        return mMediaController;
    }
}

