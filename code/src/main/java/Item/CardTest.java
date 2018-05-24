package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    //Card class test를 위한 class
    //shinhan Card , accountID 1000 Test book id : 4000 book pwd :  2230 cardindex : 0
    Card cTest = new Card("shinhan",1000,0);


    @Test
    void getCid() {
        //run Card class getCid() -> return card id -> expect 4000
        assertEquals(60000,cTest.getCid());
    }

    @Test
    void getCpwd() {
        //run Card class getCid() -> return card pwd -> expect 2230
        assertEquals(2230,cTest.getCpwd());
    }
}