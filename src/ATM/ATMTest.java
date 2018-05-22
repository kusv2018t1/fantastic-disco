package ATM;

import java.util.Scanner;

import BankSystem.*;
import Item.*;
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

			switch (service) {
			// 계좌조회
			case 1:
				System.out.println("----------------------");
				System.out.print("PassWord (****) : ");
				pwd = Integer.valueOf(sc.nextLine());
				
				if(atm.confirm(pwd)) {
					System.out.println("----------------------");
					System.out.print("Do you want to print receit? (yes(1)/no(0)) : ");
					wants = Integer.valueOf(sc.nextLine()) != null;
					atm.printReceipt(wants);
					
					atm.checkResource();
					break;
				} else {
					System.out.println("----------------------");
					System.out.println("Wrong Pass Word");
				}
			// 입금
			case 2:
				break;
			// 출금
			case 3:
				break;
			// 송금
			case 4:
				break;
			// 교통카드 발급
			case 5:
				break;
			}
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
