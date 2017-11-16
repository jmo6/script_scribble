package script.scribble;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import script.scribble.util.Input;


public class Fragment_FreeMd extends Fragment {

    Input input = new Input(25);
    View freemd_frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Fragment to handle free mode activity
        freemd_frag = new CustomView(getContext(), input);
        return freemd_frag;

    }
}
