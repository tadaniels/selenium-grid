package com.thoughtworks.selenium.grid.remotecontrol;

import java.io.IOException;

/**
 * Periodicaly Poll Hub to check it is still up and re-register automatically
 * when the Hub disappears and come back up.
 */
public class HubPoller {
    private final SelfRegisteringRemoteControl rc;
    private boolean lostConnectionToHub;

    public HubPoller(SelfRegisteringRemoteControl rc) {
        this(rc, false);
    }

    protected HubPoller(SelfRegisteringRemoteControl rc, boolean lostConnectionToHub) {
        this.rc = rc;
        this.lostConnectionToHub = lostConnectionToHub;
    }

    public SelfRegisteringRemoteControl remoteControl() {
        return this.rc;
    }
    
    public boolean lostConnectionToHub() {
        return lostConnectionToHub;
    }

    public void checkConnectionToHub() throws IOException {
        if (rc.canReachHub()) {
            if (lostConnectionToHub) { rc.register(); }
            lostConnectionToHub = false;
        } else {
            lostConnectionToHub = true;
        }
    }

}
