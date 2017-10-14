package script.scribble;

public class Touch {
    public int id;
    public Vector2f start, current;
    public long pressedTime;
    public static final int IDLE = 0, PRESSED = 1, RELEASED = 2,
            PRESSEDUSED = 3, RELEASEDUSED = 4, HELD = 5;
    int state;
    private boolean isTap, isScroll;

    public Touch() {
        this.id = 0;
        this.start = new Vector2f();
        this.current = new Vector2f();
        this.pressedTime = System.currentTimeMillis();
        this.state = 0;
    }

    public Touch(int id, Vector2f start, Vector2f current, long pressedTime,
                 int state) {
        this.id = id;
        this.start = new Vector2f(start);
        this.current = new Vector2f(current);
        this.pressedTime = pressedTime;
        this.state = state;
    }

    public Touch(Touch t) {
        this.id = t.id;
        this.start = new Vector2f(t.start);
        this.current = new Vector2f(t.current);
        this.pressedTime = t.pressedTime;
        this.state = t.state;
    }

    public boolean isPressed() {
        return state == Touch.PRESSEDUSED;
    }

    public boolean isReleased() {
        return state == Touch.RELEASEDUSED;
    }

    public boolean isTap() {
        return isTap;
    }

    public void setTap(boolean isTap) {
        this.isTap = isTap;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public boolean isSwipe() {
        return isScroll() && isTap();
    }
}
