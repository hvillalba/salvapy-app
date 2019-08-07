package py.ccenturion.salvapy_app.util.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Gustavo on 11/8/17.
 */

public class GlobalBus {
    private static EventBus sBus;
    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }
}

