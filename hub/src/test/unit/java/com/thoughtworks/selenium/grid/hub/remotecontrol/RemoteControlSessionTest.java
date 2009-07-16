package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.jbehave.classmock.UsingClassMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.util.Date;


public class RemoteControlSessionTest extends UsingClassMock {

    @Test
    public void constructorReturnsTheSessionIdProvidedInTheConstructor() {
        assertEquals("123", new RemoteControlSession("123", null).sessionId());
    }

    @Test
    public void constructorReturnsTheRemoteControlProvidedInTheConstructor() {
        final RemoteControlProxy rc;

        rc = new RemoteControlProxy("host", 24, "env", null);
        assertEquals(rc, new RemoteControlSession("whatever", rc).remoteControl());
    }

    @Test
    public void byDefaultLastActiveAtReturnsMoreOrLessTheCreationTime() {
        final RemoteControlSession session;
        final long now;

        now = new Date().getTime();
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null));
        assertTrue(session.lastActiveAt() >= now);
        assertTrue(session.lastActiveAt() <= now + 10 * 1000);
    }

    @Test
    public void lastActiveAtDoesNotChangeUntilupdateLastActiveAtIsCalled() {
        final RemoteControlSession session;
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null));
        assertEquals(session.lastActiveAt(), session.lastActiveAt());
    }

    @Test
    public void updateLastActiveAtSetsLastActiveAtToMoreOrLessTheMethodCallTime() {
        final RemoteControlSession session;
        final long now;

        now = new Date().getTime();
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null)) {
            @Override
            protected long now() {
                return now + 10;
            }
        };
        session.updateLastActiveAt();
        assertEquals(now + 10, session.lastActiveAt());
    }

    @Test
    public void innactiveSinceReturnsTrueWhenGivenTimeIsTheLastActiveAt() {
        final RemoteControlSession session;
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null));
        assertTrue(session.innactiveSince(session.lastActiveAt()));
    }

    @Test
    public void innactiveSinceReturnsFalseWhenGivenTimeIsBeforeTheLastActiveAt() {
        final RemoteControlSession session;
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null));
        assertFalse(session.innactiveSince(session.lastActiveAt() - 1));
    }

    @Test
    public void innactiveSinceReturnsTrueWhenGivenTimeIsAfterTheLastActiveAt() {
        final RemoteControlSession session;
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null));
        assertTrue(session.innactiveSince(session.lastActiveAt() + 1));
    }

    @Test
    public void innactiveForMoreThanCallsinnactiveSinceWithNowPlusArgument() {
        final RemoteControlSession session;
        final long now;

        now = new Date().getTime();
        session = new RemoteControlSession("whatever",
                                           new RemoteControlProxy("host", 24, "env", null)) {
            @Override
            protected long now() {
                return now;
            }

            @Override
            public boolean innactiveSince(long millisecondsSinceEpoch) {
                if (millisecondsSinceEpoch != (now - 5)) {
                    throw new IllegalStateException("got " + millisecondsSinceEpoch);
                }
                return true;
            }
        };
        assertTrue(session.innactiveForMoreThan(5));
    }

}
