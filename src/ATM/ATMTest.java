package ATM;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    public ATM atm = new ATM();

    public static void main(String[] args)
    {
        ATMTest at = new ATMTest();

        at.readItem();
        at.selectNation();
        at.confirm();
        at.insertCash();
        at.enterAmount();
        at.printReceipt();
        at.destAccount();
        at.setDataRange();
        at.readManagementItem();
        at.selectService();
        at.end();

    }

    @org.junit.jupiter.api.Test
    void readItem() {
        assertEquals(1,atm.readItem(0,0,"kb",0));
    }



    @org.junit.jupiter.api.Test
    void selectNation() {
        atm.selectService(0);
        atm.selectService(1);
        atm.selectService(2);
        atm.selectService(3);
        atm.selectService(4);
    }

    @org.junit.jupiter.api.Test
    void confirm() {
        assertEquals(0,atm.confirm(0));
    }

    @org.junit.jupiter.api.Test
    void insertCash() {
        String[] bill = new String[7];
        bill[0] = "thousandwon";
        bill[1] = "fivdthousandwon";
        bill[2] = "tenthousandwon";
        bill[3] = "fiftythousandwon";
        bill[4] = "tendollor";
        bill[5] = "twentydollor";
        bill[6] = "ondhundreddollor";
        assertEquals(1,atm.insertCash(bill));
    }

    @org.junit.jupiter.api.Test
    void enterAmount() {
        assertEquals(0,atm.enterAmount(10000));
    }

    @org.junit.jupiter.api.Test
    void printReceipt() {
        assertEquals(0,atm.printReceipt(true));
    }

    @org.junit.jupiter.api.Test
    void destAccount() {
        assertEquals(1,atm.destAccount("kb",1010));
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
    void end() {
        atm.end();
    }
}
