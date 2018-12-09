package com.location_map.listeners;

import android.location.Location;

/**
 * Created by Net22 on 11/13/2017.
 */

public interface MapListeners {

    public void prepareMapClicks();
    public void gotLocationChanged(Location newLocation);
    public void doWork();

}
