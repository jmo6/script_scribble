package script.scribble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    CustomView myCustomView = null;
    Input input = new Input(250);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // initialize stuff here

        // temporarily just go straight to the custom view
        myCustomView = new CustomView(this, input);
        //myCustomView.setOnTouchListener(input);
        //setContentView(myCustomView);
    }

    public void goCustomView (View v){
        //myCustomView = new CustomView(this, input);
        myCustomView.setOnTouchListener(input);
        setContentView(myCustomView);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myCustomView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCustomView.resume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // handle button presses here
        }
    }

}
