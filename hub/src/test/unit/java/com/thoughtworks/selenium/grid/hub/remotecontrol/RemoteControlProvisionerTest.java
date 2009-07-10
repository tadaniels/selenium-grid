package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.ConcurrentAction;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class RemoteControlProvisionerTest {

    @Test
    public void onceAddedARemoteControlIsPartOfTheAvailableList() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("a", 0, "", null);

        provisioner.add(remoteControl);
        assertEquals(1, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

	@Test
	public void whenAddingTwoRemoteControlsThatAreEqualsTheFirstIsReplacedByTheSecond() {
		final RemoteControlProvisioner provisioner;
        final RemoteControlProxy availableRc;

		provisioner = new RemoteControlProvisioner();

		RemoteControlProxy oldRC = new RemoteControlProxy("a", 0, "", null);
		RemoteControlProxy newRC = new RemoteControlProxy("a", 0, "", null);

		provisioner.add(oldRC);
		provisioner.add(newRC);

        availableRc = provisioner.findNextAvailableRemoteControl();
        assertSame(newRC, availableRc);
	}

	@Test
	public void whenReplacingRemoteControlFirstRemoveAllRegisteredSessions() {
        final RemoteControlProvisioner provisioner;

        provisioner = new RemoteControlProvisioner();
		RemoteControlProxy firstRC = new RemoteControlProxy("a", 0, "", null);
        RemoteControlProxy secondRC = new RemoteControlProxy("a", 0, "", null);

	    firstRC.registerNewSession();

		assertTrue(firstRC.sesssionInProgress());

        provisioner.add(firstRC);
        provisioner.add(secondRC);

		assertFalse(firstRC.sesssionInProgress());
    }

    @Test
    public void multipleRemoteControlsCanBeAdded() {
        final RemoteControlProvisioner provisioner;
        final RemoteControlProxy firstRemoteControl;
        final RemoteControlProxy secondRemoteControl;

        provisioner = new RemoteControlProvisioner();
        firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        secondRemoteControl = new RemoteControlProxy("b", 0, "", null);

        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);
        assertEquals(2, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void onceRemovedARemoteControlIsNotPartOfTheAvailableList() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.remove(remoteControl);
        assertTrue(provisioner.availableRemoteControls().isEmpty());
    }

    @Test
    public void removeOnlyRemovesASpecificRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        provisioner.remove(firstRemoteControl);
        assertFalse(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void removeReturnsFalseForARemoteControlThanHasNeverBeenAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        assertFalse(provisioner.remove(new RemoteControlProxy("", 0, "", null)));
    }

    @Test
    public void removeReturnsTrueForAnAvailableRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        assertTrue(provisioner.remove(remoteControl));
    }

    @Test
    public void removeReturnsTrueForAReservedRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);
        provisioner.reserve();

        assertTrue(provisioner.remove(remoteControl));
    }

    @Test
    public void reserveReturnsNullWhenThereIsNoRegisteredRemoteControlToAvoidDeadlocks() {
        assertNull(new RemoteControlProvisioner().reserve());
    }

    @Test
    public void reserveReturnsTheFirstAvailableRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        assertEquals(firstRemoteControl, provisioner.reserve());
    }

    @Test
    public void reserveReturnsAvailableRemoteControlInOrderWhileThenCanHandleNewSessions() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        assertEquals(firstRemoteControl, provisioner.reserve());
        assertEquals(secondRemoteControl, provisioner.reserve());
    }

    @Test
    public void reserveBlocksUntilARemoteControlIsReleasedWhenThereIsAtLeastOneRegisteredRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("a", 0, "", null);

        provisioner.add(remoteControl);
        provisioner.reserve();

        new ConcurrentAction() {
            public void execute() {
                provisioner.release(remoteControl);
            }
        };
        assertEquals(remoteControl, provisioner.reserve());
    }

    @Test
    public void reserveBlocksUntilARemoteControlIsaddedWhenThereIsAtLeastOneRegisteredRemoteControl() {
        final RemoteControlProvisioner provisioner;
        final RemoteControlProxy firstRemoteControl;
        final RemoteControlProxy secondRemoteControl;

        provisioner = new RemoteControlProvisioner();
        firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        secondRemoteControl = new RemoteControlProxy("b", 0, "", null);

        provisioner.add(firstRemoteControl);
        provisioner.reserve();

        new ConcurrentAction() {
            public void execute() {
                provisioner.add(secondRemoteControl);
            }
        };
        assertEquals(secondRemoteControl, provisioner.reserve());
    }

    @Test
    public void findNextAvailableRemoteControlReturnNullWhenThereIsNoRegisteredRemoteControl() {
        assertNull(new RemoteControlProvisioner().findNextAvailableRemoteControl());
    }

    @Test
    public void findNextAvailableRemoteControlReturnTheFirstAvailableRemoteControlWhenThereIsOne() {
        final RemoteControlProvisioner provisioner;
        final RemoteControlProxy firstRemoteControl;
        final RemoteControlProxy secondRemoteControl;

        provisioner = new RemoteControlProvisioner();
        firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        assertEquals(firstRemoteControl, provisioner.findNextAvailableRemoteControl());
    }

    @Test
    public void findNextAvailableRemoteControlCyclesUntilItFindsAnAvailableRemoteControlWhenThereIsOne() {
        final RemoteControlProvisioner provisioner;
        final RemoteControlProxy firstRemoteControl;
        final RemoteControlProxy secondRemoteControl;

        provisioner = new RemoteControlProvisioner();
        firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        provisioner.reserve();
        assertEquals(secondRemoteControl, provisioner.findNextAvailableRemoteControl());
    }

    @Test
    public void findNextAvailableRemoteControlReturnsNullWhenThereIsNoAvailableRemoteControlAtAll() {
        final RemoteControlProvisioner provisioner;
        final RemoteControlProxy firstRemoteControl;
        final RemoteControlProxy secondRemoteControl;

        provisioner = new RemoteControlProvisioner();
        firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        provisioner.reserve();
        provisioner.reserve();
        assertNull(provisioner.findNextAvailableRemoteControl());
    }

    @Test
    public void onceReservedARemoteControlIsNotAvailableAnymore() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.reserve();
        assertNull(provisioner.findNextAvailableRemoteControl());
    }

    @Test
    public void releaseMakesAReservedRemoteControlAvailable() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.reserve();
        provisioner.release(remoteControl);
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

    @Test
    public void reservedRemoteControlsReturnsAnEmptyArrayWhenNoneHaveBeenAdded() {
      assertTrue(new RemoteControlProvisioner().reservedRemoteControls().isEmpty());
    }

    @Test
    public void reservedRemoteControlsReturnsAnEmptyArrayWhenRemoteControlHasBeenAddedButNotReserved() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        assertTrue(provisioner.reservedRemoteControls().isEmpty());
    }

    @Test
    public void reservedRemoteControlsReturnsARemoteControlThatHaveBeenReserved() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        provisioner.reserve();
        assertEquals(1, provisioner.reservedRemoteControls().size());
        assertTrue(provisioner.reservedRemoteControls().contains(remoteControl));
    }
    
    @Test
    public void availableRemoteControlsReturnsAnEmptyArrayWhenNoneHaveBeenAdded() {
      assertTrue(new RemoteControlProvisioner().availableRemoteControls().isEmpty());
    }

    @Test
    public void availableRemoteControlsReturnsARemoteControlThanHasBeenAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        assertEquals(1, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

}
