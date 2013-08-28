package de.sto.Music;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.round;


public class MainScreen extends Activity {
    /**
     * Called when the activity is first created.
     */

    float progress = 0;
    MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Create instance and show in fullscreen mode
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.main);


        buttonsUpDown();
        buttonPlayPause();
    }

    private void buttonPlayPause() {
        final ImageButton playPause = (ImageButton) findViewById(R.id.playPauseMusic);
        final boolean[] playing = {false};
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.test);
                if (!playing[0]) {
                    playPause.setBackgroundResource(R.drawable.pause_music);
                    mPlayer.start();
                    playing[0] = true;
                    new music().execute("");
                    TimerTask task = new TimerTask() {
                        public void run() {
                            ImageButton progressViewer = (ImageButton) findViewById(R.id.progressFill);
                            progressViewer.setMinimumWidth(round(progress));
                            progressViewer.setMaxWidth(round(progress));
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 200, 85);
                } else {
                    playPause.setBackgroundResource(R.drawable.play_music);
                    //stop playing music
                    try {
                        if (mPlayer != null && mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        mPlayer.release();
                        mPlayer = null;
                        mPlayer = new MediaPlayer();  //I needed this, maybe you dont hac
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("I can't believe you get here !");
                    }
                    playing[0] = false;
                }
            }
        });
    }

    private void buttonsUpDown() {
        final ImageButton buttonUp = (ImageButton) findViewById(R.id.upButton);
        final ImageButton buttonDown = (ImageButton) findViewById(R.id.downButton);
        final RelativeLayout playerBar = (RelativeLayout) findViewById(R.id.playerBar);
        final LinearLayout nowPlayingBar = (LinearLayout) findViewById(R.id.nowPlayingBar);

        if (buttonDown.getVisibility() == View.INVISIBLE) {
            buttonUp.setVisibility(View.VISIBLE);
        } else {
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.INVISIBLE);
        }

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayoutWeight(nowPlayingBar, 400, 3);
                playerBar.setVisibility(View.INVISIBLE);
                changeVisibleButton(buttonUp, buttonDown);
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayoutWeight(nowPlayingBar, 10, 3);
                playerBar.setVisibility(View.VISIBLE);
                changeVisibleButton(buttonUp, buttonDown);
            }
        });
    }

    //changes visible button
    private void changeVisibleButton(ImageButton btn1, ImageButton btn2) {
        if (btn1.getVisibility() == View.VISIBLE) {
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.INVISIBLE);
        }
    }

    //changes layout weight to given value
    private void changeLayoutWeight(LinearLayout layout, float value, int modeAddSubIs) {
        //modeAddSubIs: 1 -> Add, 2 -> Sub, 0/3+ -> set to value
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        if (modeAddSubIs == 1) {
            lParams.weight += value;
        } else if (modeAddSubIs == 2) {
            lParams.weight -= value;
        } else {
            lParams.weight = value;
        }
        layout.setLayoutParams(lParams);
    }

    //Displays the App in Fullscreen-Mode
    public void makeFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private class music extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            progress = 0;
            Display display = getWindowManager().getDefaultDisplay();

            while (mPlayer.isPlaying()) {

                int width = display.getWidth();  // deprecated

                progress = mPlayer.getCurrentPosition() / mPlayer.getDuration();
                progress = width * progress;

            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String result) {
            ImageButton playPause = (ImageButton) findViewById(R.id.playPauseMusic);
            playPause.setBackgroundResource(R.drawable.play_music);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

