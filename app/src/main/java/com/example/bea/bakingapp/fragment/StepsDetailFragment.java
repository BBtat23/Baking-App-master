package com.example.bea.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.activities.RecipeMainActivity;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.data.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class StepsDetailFragment extends Fragment implements ExoPlayer.EventListener {
    ArrayList<Steps> stepsArrayList;
    TextView descriptionTextView;
    TextView shortDescriptionTextView;
    TextView thumbNailURLTextView;
    TextView videoURLTextView;
    Button nextButton;
    Button previousButton;
    ImageView mImageView;
    ArrayList<Recipe> recipeArrayList;
    Steps steps;
    int position;
    private static MediaSessionCompat mediaSession;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private PlaybackStateCompat.Builder mStateBuilder;
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;


    public StepsDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_main, container, false);
        shortDescriptionTextView = (TextView) rootView.findViewById(R.id.step_short_description);
        descriptionTextView = (TextView) rootView.findViewById(R.id.step_description);
        mImageView = (ImageView) rootView.findViewById(R.id.step_image);
        // Initialize the player view.
        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video);

        if (savedInstanceState != null) {
            stepsArrayList = savedInstanceState.getParcelableArrayList(RecipeFragment.RECIPE_LIST);
            position = savedInstanceState.getInt(RecipeFragment.POSITION);
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!RecipeMainActivity.twoPane) {
            stepsArrayList = getActivity().getIntent().getParcelableArrayListExtra(RecipeFragment.RECIPE_LIST);
            if (savedInstanceState == null) {
                position = getActivity().getIntent().getExtras().getInt(RecipeFragment.POSITION);
            }
            steps = stepsArrayList.get(position);
            setDataPhone(steps);

        }else {
            recipeArrayList = getActivity().getIntent().getParcelableArrayListExtra(RecipeFragment.RECIPE_LIST);
            int position = getActivity().getIntent().getExtras().getInt(RecipeFragment.POSITION);
            stepsArrayList = recipeArrayList.get(position).getStepsArrayList();
            setDataTablet(stepsArrayList);

        }
    }

    private void setDataTablet(ArrayList<Steps> stepsList) {
        descriptionTextView.setText(stepsList.get(position).getDescription());
        shortDescriptionTextView.setText(stepsList.get(position).getShortDescription());
        // Initialize the Media Session.
        initializeMediaSession();
        //String URL
        String videoURL = stepsList.get(position).getVideoURL();
        // Initialize the player.
        initializePlayer(Uri.parse(videoURL));

        //Set up Image
        Picasso.Builder builder = new Picasso.Builder(getActivity());
        builder.build()
                .load(R.drawable.photo_cooking)
                .into(mImageView);
    }
    private void setDataPhone(Steps steps) {
        descriptionTextView.setText(steps.getDescription());
        shortDescriptionTextView.setText(steps.getShortDescription());
        // Initialize the Media Session.
        initializeMediaSession();
        //String URL
        String videoURL = steps.getVideoURL();
        // Initialize the player.
        initializePlayer(Uri.parse(videoURL));

        //Set up Image
        Picasso.Builder builder = new Picasso.Builder(getActivity());
        builder.build()
                .load(R.drawable.photo_cooking)
                .into(mImageView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);

    }
    //    @OnClick (R.id.next_Button)
//    void nextButton(){
//        if (position > stepsArrayList.size() - 1) {
//            if (nextButton.isEnabled()) nextButton.setEnabled(false);
//        }else {
//            position++;
//
//            if (!previousButton.isEnabled()) previousButton.setEnabled(true);
//
//            steps = stepsArrayList.get(position);
//            releasePlayer();
//            setDataPhone(steps);
//            setDataTablet(stepsArrayList);
//        }
//    }
//
//    @OnClick (R.id.previous_Button)
//    void previousButton(){
//        if (position == 0) {
//            if (previousButton.isEnabled()) previousButton.setEnabled(false);
//        }else {
//            position--;
//
//            if (!nextButton.isEnabled()) nextButton.setEnabled(true);
//            steps = stepsArrayList.get(position);
//            releasePlayer();
//            setDataPhone(steps);
//            setDataTablet(stepsArrayList);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {

            Uri videoURL = Uri.parse(steps.getVideoURL());
            initializePlayer(videoURL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            Uri videoURL = Uri.parse(steps.getVideoURL());
            initializePlayer(videoURL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (exoPlayer != null){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
//        exoPlayer.setPlayWhenReady(false);
        }
        if (mediaSession != null){
            mediaSession.setActive(false);
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mediaSession.setActive(false);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mediaSession = new MediaSessionCompat( getActivity(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession.setActive(true);

    }
    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(startAutoPlay);
        }
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            exoPlayer.seekTo(startWindow, startPosition);
        }
    }

        @Override
        public void onTimelineChanged (Timeline timeline, Object manifest){

        }

        @Override
        public void onTracksChanged (TrackGroupArray trackGroups, TrackSelectionArray
        trackSelections){

        }

        @Override
        public void onLoadingChanged ( boolean isLoading){

        }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(mStateBuilder.build());
    }

        @Override
        public void onPlayerError (ExoPlaybackException error){

        }

        @Override
        public void onPositionDiscontinuity () {

        }

        /**
         * Media Session Callbacks, where all external clients control the player.
         */
        private class MySessionCallback extends MediaSessionCompat.Callback {
            @Override
            public void onPlay() {
                exoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                exoPlayer.seekTo(0);
            }
        }

    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (exoPlayer != null) {
            startAutoPlay = exoPlayer.getPlayWhenReady();
            startWindow = exoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, exoPlayer.getCurrentPosition());
        }
    }
    }
