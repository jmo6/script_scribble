package script.scribble;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import script.scribble.util.Input;

public class MainMenu extends AppCompatActivity {
    CustomView myCustomView = null;
    Input input = new Input(25);

    private final String changeToFreeMd = "Free Mode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_menu);

    }

    public void gotoFreeMode (View view){
        Toast.makeText(MainMenu.this, changeToFreeMd, Toast.LENGTH_SHORT).show();
        myCustomView = new CustomView(this, input, this);
        myCustomView.setOnTouchListener(input);
        setContentView(myCustomView);
    }

    public void gotoMode2 (View view){

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
