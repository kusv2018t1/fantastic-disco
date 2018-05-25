package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficCardTest {

    TrafficCard tcTest = new TrafficCard();

    @Test
    void getTcid() {
        //first Tcid = 9000 read correctly check
        assertEquals(9900,tcTest.getTcid());
        //second TrafficCrad Tcid =>9001
        TrafficCard tt = new TrafficCard();
        assertEquals(9901,tt.getTcid());
    }

    @Test
    void setDateRange() {
        tcTest.setDateRange(0523);
    }

    @Test
    void setAccountID() {
        tcTest.setAccountID(1000);
    }
}