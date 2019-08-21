package com.e.baking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;
import com.e.baking.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAndInstructionsActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String PLAYER_POSITION="player_position";
    private static final String STATE_AUTO_PLAY="state_auto_play";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;

    private int listIndex;
    private List<Step> steps = new ArrayList();

    private long position;
    private boolean stateAutoPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayout_video_instruction);

        mPlayerView = findViewById(R.id.playerView);

       final TextView textView = findViewById(R.id.step_description);


        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        Step step = intent.getParcelableExtra("step");

       textView.setText(step.getDescription());

        ImageView thumbNailImageView = findViewById(R.id.thumbnail);

        String thumbNail = step.getThumbnailURL();

        if (thumbNail != null && !"".equals(thumbNail)) {
            Picasso.get().load(thumbNail).into(thumbNailImageView);
        }

        if(mPlayerView != null) {
            initializePlayer(Uri.parse(step.getVideoURL()));

        }
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void closeOnError() {
        releasePlayer();
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        position = mExoPlayer.getCurrentPosition();
        stateAutoPlay = mExoPlayer.getPlayWhenReady();

        savedInstanceState.putBoolean(STATE_AUTO_PLAY, stateAutoPlay);
        savedInstanceState.putLong(PLAYER_POSITION, position);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            position = savedInstanceState.getLong(PLAYER_POSITION);
            stateAutoPlay = savedInstanceState.getBoolean(STATE_AUTO_PLAY);

            mExoPlayer.seekTo(position);

        }
    }
}