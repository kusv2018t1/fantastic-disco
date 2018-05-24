package BankSystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    //make test Bank
    Bank bTest = new Bank("shinhan");

    @Test
    void validCheck() {
        //itemtype : 0 (book) , itemID : 7000 accountID : 1000
        assertEquals(true,bTest.validCheck(0,6000,1000));
    }

    @Test
    void confirm() {
        //bTest.confirm is true
        //before the item should be checked
        this.validCheck();
        assertTrue(bTest.confirm(1230));
    }

    @Test
    void linkAccount() {
        //link Account
        //before the item should be checked
        this.validCheck();
        bTest.linkAccount(9900);
    }

    @Test
    void getBalance() {
        //before the item should be checked
        this.validCheck();

        // ATMTEST를 한번이라도 돌렸다면 txt 파일 값이 바뀜(ATMTEST 1번 돌리면 -> 49000 2번 돌리면 -> 48000 안돌리면 -> 50000)
        // 따라서 ATMTEST를 한번이라도 돌렸다면(enterAmount에서 10000만큼 뺌) 당연히 오류가 나게됨 (초기 세팅값은 50000이 맞음)
        assertEquals(50000, bTest.getBalance());
    }

    @Test
    void chargeTrafficCard() {
        //it can chargeTrafficCard balance>3000
        //before the item should be checked
        this.validCheck();
        assertTrue(bTest.chargeTrafficCard(3000));
    }

    @Test
    void withdraw() {
        //before the item should be checked
        this.validCheck();
        //it can withdraw 1000won -> balance = 28000
        assertTrue(bTest.withdraw(10000));
    }

    @Test
    void deposit() {
        //before the item should be checked
        this.validCheck();
        //insert money 10000 -> balance =38000
        assertTrue(bTest.deposit(10000));
    }

    @Test
    void checkAccount() {
        //check kb account 1001 name : choi
        assertEquals("choi",bTest.checkAccount("shinhan",1001));
    }

    @Test
    void transfer() {
        //before the item should be checked and destAccount also checked
        this.validCheck();
        this.checkAccount();
        //same because destaccount ==using account
        assertTrue(bTest.transfer(10000));
    }
}