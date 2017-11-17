package script.scribble.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;

public class Input implements OnTouchListener {
    private ArrayList<Touch> touches = new ArrayList<Touch>();
    private float minScrollDist;

    public Input(float minScrollDist) {
        this.minScrollDist = minScrollDist;
    }

     //Use this method at the start or end of the method or thread that updates
     //the program based on input
    public void refresh() {
        for(int i = 0; i < touches.size(); i++) {
            Touch t = touches.get(i);
            if(t.state == Touch.HELD && Vector2f.dist(t.start, t.current) >= minScrollDist) {
                t.isSwipe = true;
            }
            else if(t.state == Touch.PRESSED) {
                t.state = Touch.HELD;
            }
            else if(t.state == Touch.RELEASED) {
                touches.remove(i);
                i--;
            }
            t.last = new Vector2f(t.current);
        }
    }

    public boolean isRectPressed(float x, float y, float width, float height) {
        for(Touch t : touches) {
            if(t.isPressed()
                    && Rectf.isInRect(t.current, new Rectf(x, y, width, height))) {
                return true;
            }
        }
        return false;
    }

    public boolean isRectReleased(int x, int y, int width, int height) {
        for(Touch t : touches) {
            if(t.isReleased()
                    && Rectf.isInRect(t.current, new Rectf(x, y, width, height))) {
                return true;
            }
        }
        return false;
    }

    public boolean isRectTouched(float x, float y, float width, float height) {
        for(Touch t : touches) {
            if(Rectf.isInRect(t.current, new Rectf(x, y, width, height))) {
                return true;
            }
        }
        return false;
    }

    public boolean wasRectTouched(float x, float y, float width, float height) {
        for(Touch t : touches) {
            if(Rectf.isInRect(t.last, new Rectf(x, y, width, height))) {
                return true;
            }
        }
        return false;
    }

    // returns null if no touch is in the rect
    public Touch getTouchInRect(float x, float y, float width, float height) {
        for(Touch t : touches) {
            if(Rectf.isInRect(t.current, new Rectf(x, y, width, height))) {
                return new Touch(t);
            }
        }
        return null;
    }

    public ArrayList<Touch> getTouches() {
        ArrayList<Touch> ret = new ArrayList<Touch>();
        for(Touch t : touches) {
            ret.add(new Touch(t));
        }
        return ret;
    }

    public ArrayList<Touch> getSwipes() {
        ArrayList<Touch> swipes = new ArrayList<Touch>();
        for(Touch t : touches) {
            if(t.isSwipe) {
                swipes.add(new Touch(t));
            }
        }
        return swipes;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        v.performClick();

//        int pointerCount = e.getPointerCount();
        int pointerIndex = e.getActionIndex();
        int pointerId = e.getPointerId(pointerIndex);

        switch(e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                Vector2f p = new Vector2f(e.getX(pointerIndex),
                        e.getY((pointerIndex)));
                Touch t = new Touch(pointerId, p, p, System.currentTimeMillis(),
                        Touch.PRESSED);
                touches.add(new Touch(t));
            } break;
            case MotionEvent.ACTION_MOVE: {
                for(int i = 0; i < touches.size(); i++) {
                    Touch t = touches.get(i);
                    if(t.id == pointerId) {
                        t.current.x = e.getX(pointerIndex);
                        t.current.y = e.getY(pointerIndex);
                    }
                }
            } break;
//                for(int i = 0; i < pointerCount; i++) {
//                    Vector2f p1 = new Vector2f(e.getX(i), e.getY(i));
//                    Touch t1 = new Touch();
//                    t1.id = e.getPointerId(i);
//                    t1.current = p1;
//                    for(int j = 0; j < touches.size(); j++) {
//                        Touch touch = touches.get(j);
//                        if(touch.id == t1.id) {
//                            touches.set(j, new Touch(t1.id, touch.start,
//                                    t1.current, touch.pressedTime, touch.state));
//                        }
//                    }
//                }
//                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                for(int i = 0; i < touches.size(); i++) {
                    Touch t = touches.get(i);
                    if(t.id == pointerId) {
                        t.state = Touch.RELEASED;
                    }
                }
            } break;
//                Vector2f p2 = new Vector2f(e.getX(pointerIndex),
//                        e.getY(pointerIndex));
//                Touch t2 = new Touch();
//                t2.id = pointerId;
//                t2.current = p2;
//                t2.state = Touch.RELEASED;
//                for(int j = 0; j < touches.size(); j++) {
//                    Touch touch = touches.get(j);
//                    if(touch.id == t2.id) {
//                        touches.set(j, new Touch(t2.id, touch.start, t2.current,
//                                touch.pressedTime, t2.state));
//                    }
//                }
//                break;
        }

        return true;
    }
}
