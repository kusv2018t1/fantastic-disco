package ATM;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    public static ATM atm = new ATM();

    @org.junit.jupiter.api.Test
    void readItem() {
        assertEquals(0, atm.readItem(1, 60000,"shinhan" , 1000));
    }

    @org.junit.jupiter.api.Test
    void selectNation() {
        assertEquals(1,atm.selectNation(1));
    }

    @org.junit.jupiter.api.Test
    void confirm() {
        assertEquals(true,atm.confirm(2230));
    }

    @org.junit.jupiter.api.Test
    void insertCash() {
        String[] bill = new String[100];
        //
        bill[0] = "000"; // 1000
        bill[1] = "001";
        bill[2] = "010";
        bill[3] = "011";
        //
        bill[4] = "100";
        bill[5] = "101";
        bill[6] = "110";
        //
        bill[7] = null;
        assertEquals(true,atm.insertCash(bill));
    }

    @org.junit.jupiter.api.Test
    void enterAmount() {
        atm.selectService(3);
        assertEquals(true,atm.enterAmount(10000));
    }

    @org.junit.jupiter.api.Test
    void printReceipt() {
        assertEquals(0,atm.printReceipt(true));
    }

    @org.junit.jupiter.api.Test
    void agreement() {
        assertEquals(true,atm.agreement(true));
    }

    @org.junit.jupiter.api.Test
    void destAccount() {
        assertEquals("choi",atm.destAccount("shinhan",1001));
    }

    @Test
    void selectService() {
        atm.selectService(1);
    }

    @Test
    void setDataRange() {
        atm.setDataRange(10);
    }

    @Test
    void readManagementItem() {
        atm.readManagementItem(123);
    }

    @Test
    void checkResource() {
        atm.checkResource();
    }
}