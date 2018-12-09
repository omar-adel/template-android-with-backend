package modules.general.utils;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by omar on 11/06/2017.
 */

public class Swipe_Touch_Listener {

    public boolean stayedWithinClickDistance = false;
    public boolean action_up_called = false;
    public float downX;
    public TOUCH_INTERFACE_EVENTS swipeTouchInterface;
    public static final int MAX_CLICK_DISTANCE = 15;
    public int swipe_min_distance;
    public Context context;
    public View view;

    public Swipe_Touch_Listener(Context context, TOUCH_INTERFACE_EVENTS swipeTouchInterface
            , View view, int swipe_min_distance)

    {
        this.context = context;
        this.swipe_min_distance = swipe_min_distance;
        this.swipeTouchInterface = swipeTouchInterface;
        this.view = view;
        view.setOnTouchListener(swipe_touch_listener);
    }

    public View.OnTouchListener swipe_touch_listener = new View.OnTouchListener() {


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.e("onTouch", "onTouch: ");
            int action = event.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                action_up_called = false;

                downX = event.getX();
                stayedWithinClickDistance = true;

                return true;
            } else if (action == MotionEvent.ACTION_MOVE) {

                float deltaX = downX - event.getX();
                if (stayedWithinClickDistance && pxToDp(Math.abs(deltaX)) > MAX_CLICK_DISTANCE) {
                    stayedWithinClickDistance = false;
                }
                // horizontal swipe detection
                if (pxToDp(Math.abs(deltaX)) > swipe_min_distance) {

                    // left or right
                    if (deltaX < 0) {
                        if (!action_up_called) {
                            swipeTouchInterface.swipeTouchLeft(view);
                            action_up_called = true;
                        }
                        return true;
                    }
                    if (deltaX > 0) {
                        if (!action_up_called) {
                            swipeTouchInterface.swipeTouchRight(view);
                            action_up_called = true;
                        }
                        return true;
                    }
                } else {
                    return true;

                }


            } else if (action == MotionEvent.ACTION_UP) {
                action_up_called = false;

                if (stayedWithinClickDistance) {
                    swipeTouchInterface.swipeTouchClick(view);
                    // Click event has occurred
                    //Toast.makeText(context, "ciick image slideshow in slideshowview class", Toast.LENGTH_LONG).show();
                }

                return true;
            }
            return false;
        }
    };

    private float pxToDp(float px) {

        return px / context.getResources().getDisplayMetrics().density;
    }


    public interface TOUCH_INTERFACE_EVENTS {

        public void swipeTouchClick(View view);

        public void swipeTouchRight(View view);

        public void swipeTouchLeft(View view);
    }

}
