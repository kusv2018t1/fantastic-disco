package ATM;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    public ATM atm = new ATM();

    public static void main(String[] args)
    {
        ATMTest at = new ATMTest();
        at.atm.initATM();
        at.readItem();
        at.selectNation();
        at.confirm();
        at.insertCash();
        at.enterAmount();
        at.printReceipt();
        at.destAccount();

    }

    @org.junit.jupiter.api.Test
    void readItem() {
        assertEquals(1,atm.readItem(0,0,0,0));
    }



    @org.junit.jupiter.api.Test
    void selectNation() {
        assertEquals(1,atm.selectNation(1));
    }

    @org.junit.jupiter.api.Test
    void confirm() {
        assertEquals(1,atm.confirm(0,0));
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
        assertEquals(1,atm.enterAmount(10000));
    }

    @org.junit.jupiter.api.Test
    void printReceipt() {
        assertEquals(0,atm.printReceipt(true));
    }

    @org.junit.jupiter.api.Test
    void destAccount() {
        assertEquals(1,atm.destAccount(1,1010));
    }
}
