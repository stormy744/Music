package de.sto.Music;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainScreen extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.main);

        buttonsUpDown();

    }

    private void buttonsUpDown() {
        final ImageButton buttonUp = (ImageButton) findViewById(R.id.upButton);
        final ImageButton buttonDown = (ImageButton) findViewById(R.id.downButton);
        final RelativeLayout playerBar = (RelativeLayout) findViewById(R.id.playerBar);
        //final LinearLayout nowPlayingBar = (LinearLayout) findViewById(R.id.nowPlayingBar);

        if (buttonDown.getVisibility() == View.INVISIBLE) {
            buttonUp.setVisibility(View.VISIBLE);
        } else {
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.INVISIBLE);
        }

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeLayoutWeight(nowPlayingBar, 100, 3);
                playerBar.setVisibility(View.INVISIBLE);
                changeVisibleButton(buttonUp, buttonDown);
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeLayoutWeight(nowPlayingBar, 10, 3);
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
}
