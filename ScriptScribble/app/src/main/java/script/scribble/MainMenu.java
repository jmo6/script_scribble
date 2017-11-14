package script.scribble;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import script.scribble.util.Input;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    CustomView myCustomView = null;
    Input input = new Input(25);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // initialize stuff here


    }

    /*
        Handle Button onClick
        Switches to different activity(game mode) when button click
        Handled by Fragment
     */
    public void changeFragment (View view){
        Fragment fragment;

        //Go to freemode activity
        if(view == findViewById(R.id.freemd_btn)){
            fragment = new Fragment_FreeMd();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_one, fragment);
            ft.commit();
        }

        // Go to another mode activity
        if(view == findViewById(R.id.mode2_btn)){
            fragment = new Fragment_Mode2();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_one, fragment);
            ft.commit();
        }
    }

    // Called from CodingArea class in goBack()
    // Clears activity stack when BACK button from CodingArea is pressed
    // Brings back to main menu
    public void comeBack(){
        //finish();
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //myCustomView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myCustomView.resume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // handle button presses here
        }
    }
}
