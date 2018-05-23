package ATM;

import java.util.Scanner;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

	public static void main(String[] args) {
		ATM atm = new ATM();

		String bankID;
		int itemType, itemID, accountID;
		int service;
		int pwd;
		boolean wants;
        int money_amount=0, print=0;
        int mode=0;
		Scanner sc = new Scanner(System.in);

		ATMTest at = new ATMTest();

		while (true) {
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
			atm.readItem(itemType, itemID, bankID, accountID);

			System.out.println("----------------------");
			System.out.print("1.check  2.deposit  3.withdraw  4.transfer 5.issue traffic card : ");
			service = Integer.valueOf(sc.nextLine());
			atm.selectService(service);

			switch (service) {
                // 계좌조회
                case 1:
                    System.out.println("----------------------");
                    System.out.print("PassWord (****) : ");
                    pwd = Integer.valueOf(sc.nextLine());

                    if (atm.confirm(pwd)) {
                        print=1;
                    } else {
                        System.out.println("----------------------");
                        System.out.println("Wrong Pass Word");
                    }
                    money_amount=0;
                    mode=0;
                    break;
                // 입금 not ready
                case 2:
                    System.out.println("----------------------");
                    System.out.print("insert money (10$ ~ 100$) : ");
                    money_amount = Integer.valueOf(sc.nextLine());
                    //atm.insertCash(money_amount);

                    break;
                // 출금
                case 3:
                    System.out.println("----------------------");
                    System.out.print("PassWord (****) : ");
                    pwd = Integer.valueOf(sc.nextLine());

                    if (atm.confirm(pwd)) {
                        int money_type;
                        System.out.println("----------------------");
                        System.out.print("select money type (won(0) / $(1)) : ");
                        money_type = Integer.valueOf(sc.nextLine());
                        atm.selectNation(money_type);

                        System.out.println("----------------------");
                        System.out.print("How much will you withdraw? (10$ ~ 1000$) : ");
                        money_amount = Integer.valueOf(sc.nextLine());
                        atm.enterAmount(money_amount);

                        print=1;
                    } else {
                        System.out.println("----------------------");
                        System.out.println("Wrong Pass Word");
                    }
                    break;
                // 송금
                case 4:
                    System.out.println("----------------------");
                    System.out.print("PassWord (****) : ");
                    pwd = Integer.valueOf(sc.nextLine());

                    if (atm.confirm(pwd)) {
                        String dest_bankID;
                        System.out.println("----------------------");
                        System.out.print("(Destination) Bank ID (shinhan/kb/) : ");
                        dest_bankID = sc.nextLine();

                        int dest_account;
                        System.out.println("----------------------");
                        System.out.print("(Destination) Account ID (1000/1001/1010/) : ");
                        dest_account = Integer.valueOf(sc.nextLine());

                        String name;
                        System.out.println("----------------------");
                        if( (name=atm.destAccount(dest_bankID,dest_account)) != null)
                            System.out.println("Receiver Name : "+name);
                        else{
                            System.out.println("Wrong Receiver !!");
                            break;
                        }

                        System.out.println("----------------------");
                        System.out.print("How much will you send? (10$ ~ 1000$) : ");
                        money_amount = Integer.valueOf(sc.nextLine());
                        atm.enterAmount(money_amount);

                        print=1;
                    } else {
                        System.out.println("----------------------");
                        System.out.println("Wrong Pass Word");
                    }
                    break;
                // 교통카드 발급
                case 5:
                    System.out.println("----------------------");
                    System.out.print("PassWord (****) : ");
                    pwd = Integer.valueOf(sc.nextLine());

                    if (atm.confirm(pwd)) {
                        int setdaterange;
                        System.out.println("----------------------");
                        System.out.print("set date range (1~7) : ");
                        setdaterange = Integer.valueOf(sc.nextLine());
                        atm.setDataRange(setdaterange);

                        boolean approval;
                        System.out.println("----------------------");
                        System.out.print("Do you want to charge traffic Card(30$) and agree with linking account? (yes(1)/no(0)) : ");
                        approval = Integer.valueOf(sc.nextLine()) != 0;
                        atm.agreement(approval);

                        System.out.println("----------------------");
                        System.out.println("Traffic Card Issued!");

                        money_amount=3000;
                        print = 1;
                    } else {
                        System.out.println("----------------------");
                        System.out.println("Wrong Pass Word");
                    }
                    break;
            }

            if(print==1) {

                System.out.println("----------------------");
                if(mode!=0)
                    System.out.print("Transaction Amount : -" + money_amount + " $\n");
                System.out.print("Balance : " + atm.getBalance() + " $\n");
                System.out.println("----------------------");

                System.out.println("----------------------");
                System.out.print("Do you want to print receit? (yes(1)/no(0)) : ");
                wants = Integer.valueOf(sc.nextLine()) != 0;
                if (wants == true) {
                    System.out.println("----------------------");
                    System.out.println("print receit!");
                    System.out.println("----------------------");
                    atm.printReceipt(wants);
                }
                atm.checkResource();
            }
            print=0;

		}
	}
}
// at.selectNation();
// at.readItem();
// at.confirm();
// at.insertCash();
// at.enterAmount();
// at.printReceipt();
// at.destAccount();
// at.setDataRange();
// at.readManagementItem();
// at.selectService();
// at.end();
// @org.junit.jupiter.api.Test
// void readItem() {
// assertEquals(1,atm.readItem(0,0,"kb",0));
// }
//
//
//
// @org.junit.jupiter.api.Test
// void selectNation() {
// assertEquals(1,atm.selectNation(1));
// }
//
// @org.junit.jupiter.api.Test
// void confirm() {
// assertEquals(1,atm.confirm(0));
// }
//
// @org.junit.jupiter.api.Test
// void insertCash() {
// String[] bill = new String[7];
// bill[0] = "0000";
// bill[1] = "0001";
// bill[2] = "0010";
// bill[3] = "0011";
// bill[4] = "0100";
// bill[5] = "0101";
// bill[6] = "0110";
// assertEquals(1,atm.insertCash(bill));
// }
//
// @org.junit.jupiter.api.Test
// void enterAmount() {
// assertEquals(0,atm.enterAmount(10000));
// }
//
// @org.junit.jupiter.api.Test
// void printReceipt() {
// assertEquals(0,atm.printReceipt(true));
// }
//
// @org.junit.jupiter.api.Test
// void destAccount() {
// assertEquals(1,atm.destAccount("kb",1010));
// }
//
// @Test
// void selectService() {
// atm.selectService(1);
//
// }
//
// @Test
// void setDataRange() {
// atm.setDataRange(10);
// }
//
// @Test
// void readManagementItem() {
// atm.readManagementItem(123);
// }
//
// @Test
// void end() {
// atm.end();
// }
