package BankSystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    //test -make Account
    Account accountTest = new Account("shinhan", "park", 1000, 36000, 0);

    @Test
    void get_name() {
        //test name : park
        assertEquals("park",accountTest.get_name());
    }

    @Test
    void get_aid() {
        //test get aid
        assertEquals(1000,accountTest.get_aid());
    }

    @Test
    void get_balance() {
        //test get_balance
        assertEquals(36000,accountTest.get_balance());
    }

    @Test
    void get_tcid() {
        //test get_tcid
        assertEquals(0,accountTest.get_tcid());
    }

    @Test
    void set_balance() {
        accountTest.set_balance(10000);
    }

    @Test
    void getItemID() {
        //park -aid = 1000 -> card id = 40000 , 41000
        int cardID[] = new int[2];
        cardID[0] = 60000;
        cardID[1] = 61000;
        assertArrayEquals(cardID,accountTest.getItemID(1));
        //park -aid = 1000 -> book id = 7000
        int bookID[] = new int[1];
        bookID[0] = 6000;
        assertArrayEquals(bookID,accountTest.getItemID(0));
    }

    @Test
    void getPwd() {
        //get book pwd = 1230
        int bookPwd[] = new int[1];
        bookPwd[0] = 1230;
        assertArrayEquals(bookPwd,accountTest.getPwd(0));

        //get card pwd : 2230,3230
        int cardPwd[] = new int[2];
        cardPwd[0] = 2230;
        cardPwd[1] = 3230;
        assertArrayEquals(cardPwd,accountTest.getPwd(1));
    }

    @Test
    void addLink() {
        //tcid 9000 -addLink
        assertEquals(true,accountTest.addLink(9000));
    }
}