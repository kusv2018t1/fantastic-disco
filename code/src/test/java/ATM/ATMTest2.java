package ATM;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest2 {
    public static ATM atm = new ATM();

    String bankID;
    int itemType, itemID, accountID;
    int service;
    int pwd;
    boolean wants;
    int money_amount=0, print=0;
    int mode=0;
    Scanner sc = new Scanner(System.in);

    @Test
    void readItem() {
        System.out.println("----------------------");
        System.out.println("----- Global ATM -----");
        System.out.println("----------------------");
        System.out.print("Item Type (card:1 / book:2) : ");
        itemType = Integer.valueOf(sc.nextLine());
        System.out.print("Item ID (40000 / 41000 / 40001) : ");
        itemID = Integer.valueOf(sc.nextLine());
        System.out.print("Bank ID (shinhan) : ");
        bankID = sc.nextLine();
        System.out.print("Account ID (1000 / 1001 / 1010) : ");
        accountID = Integer.valueOf(sc.nextLine());
        System.out.println("----------------------");


        assertEquals(true,atm.readItem(itemType, itemID, bankID, accountID));
    }

    @Test
    void selectService() {
    }

    @Test
    void selectNation() {
    }

    @Test
    void confirm() {
    }

    @Test
    void insertCash() {
    }

    @Test
    void enterAmount() {
    }

    @Test
    void printReceipt() {
    }

    @Test
    void setDataRange() {
    }

    @Test
    void agreement() {
    }

    @Test
    void destAccount() {
    }

    @Test
    void readManagementItem() {
    }

    @Test
    void checkResource() {
    }

    @Test
    void getBalance() {
    }

    @Test
    void end() {
    }
}