package script.scribble;

public class Touch {
    public int id;
    public Vector2f start, current, last;
    public long pressedTime;
    public static final int IDLE = 0, PRESSED = 1, RELEASED = 2, HELD = 5;
    public int state;
    public boolean isSwipe;

    public Touch() {
        this.id = 0;
        this.start = new Vector2f();
        this.current = new Vector2f();
        this.last = new Vector2f();
        this.pressedTime = System.currentTimeMillis();
        this.state = 0;
    }

    public Touch(int id, Vector2f start, Vector2f current, long pressedTime,
                 int state) {
        this.id = id;
        this.start = new Vector2f(start);
        this.current = new Vector2f(current);
        this.last = new Vector2f(current);
        this.pressedTime = pressedTime;
        this.state = state;
    }

    public Touch(Touch t) {
        this.id = t.id;
        this.start = new Vector2f(t.start);
        this.current = new Vector2f(t.current);
        this.last = new Vector2f(t.last);
        this.pressedTime = t.pressedTime;
        this.state = t.state;
    }

    public boolean isPressed() {
        return state == Touch.PRESSED;
    }

    public boolean isReleased() {
        return state == Touch.RELEASED;
    }

    public Vector2f getSwipeDist() {
        return start.sub(current);
    }

    public boolean isInRect(float x, float y, float width, float height) {
        return Rectf.isInRect(current, new Rectf(x, y, width, height));
    }

    public boolean startedInRect(float x, float y, float width, float height) {
        return Rectf.isInRect(start, new Rectf(x, y, width, height));
    }
}
