package script.scribble;

import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;
import android.graphics.Canvas;

public class CustomView extends SurfaceView implements Runnable {
    Context context;
    Input input;
    Thread myThread;
    SurfaceHolder myHolder;
    int screen_width, screen_height;
    boolean isRunning;

    // declare variables here
    Paint tempPaint;

    public void pause() {

    }

    public void resume() {

    }

    public CustomView(Context context, Input input) {
        super(context);
        this.context = context;
        this.input = input;
        this.myThread = new Thread(this);
        isRunning = true;
        this.myThread.start();
        this.myHolder = getHolder();

        // get screen size
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width = size.x;
        screen_height = size.y;

        // initialize variables here
        tempPaint = new Paint();
    }

    @Override
    public void run() {
        while(isRunning) {
            // make sure we got valid stuff
            while(myHolder == null || !myHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Canvas canvas = myHolder.lockCanvas();
            canvas.drawRGB(127, 127, 127);

            /**************************************** Start Draw *********************************************/

            canvas.drawRect(20, 20, 100, 100, tempPaint);

            /**************************************** End Draw *********************************************/

            myHolder.unlockCanvasAndPost(canvas);

            input.refresh();

            /*********************************** Start Update ********************************************/



            /**************************************** End Update *********************************************/

            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
