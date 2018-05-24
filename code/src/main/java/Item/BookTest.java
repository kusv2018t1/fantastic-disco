package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    //book class test를 위한 class
    //shinhan book , accountID 1010 Test book id : 7000 book pwd :  1230
    Book bTest = new Book("shinhan",1000);

    @Test
    void getBid() {
        //run Book class getBid() -> return book ID -> expect 7000
        assertEquals(6000,bTest.getBid());
    }

    @Test
    void getBpwd() {
        //run Book class getBid() -> return book pwd -> expect 1230
        assertEquals(1230 , bTest.getBpwd());
    }
}