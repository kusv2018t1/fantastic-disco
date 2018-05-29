package BankSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Bank {
	// bank name
	String bankID;
	// Account list
	private ArrayList<Account> accountInfo = new ArrayList<>();
	// Cache
	private Account A;
	private Bank Dest;
	private int type;
	// File IO
	File file;
	// card number
	private int cardNum;

	// Bank init
	public Bank(String _bankName) {
		String bankName;
		bankID = _bankName;
		//code/src/main/java/BankSystem
		bankName = "code/src/main/java/BankSystem/"+bankID + ".txt";
		//bankName = bankID + ".txt";
		this.file = new File(bankName);
		this.loadData();
	}

	// initialization setAccountData -> loadData
	public void loadData() {
		String[] splitedStr = null;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "euc-kr"));
			String line = null;
			splitedStr = null;

			// read file line by line
			while ((line = reader.readLine()) != null) {
				splitedStr = null;
				splitedStr = line.split("\t");

				for (int i = 0; i < splitedStr.length; i++)
					splitedStr[i] = splitedStr[i].trim();

				// put chunked Data to Account list (Account composition : Name AccountID
				// Balance)
				this.accountInfo.add(new Account(bankID, splitedStr[0], Integer.valueOf(splitedStr[1]),
						Integer.valueOf(splitedStr[2]), Integer.valueOf(splitedStr[3])));

				// // test code
				// System.out.println("Name : " + splitedStr[0]);
				// System.out.println("AccountID : " + splitedStr[1]);
				// System.out.println("Balance : " + splitedStr[2] + "\n");
				// // test code
			}
			reader.close();
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write data
	public void WriteData() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.file)));

			for (Account value : accountInfo) {
				pw.println(value.get_name() + "\t" + value.get_aid() + "\t" + value.get_balance() + "\t"
						+ value.get_tcid());
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// valid check
	public boolean validCheck(int _itemType, int _itemID, int _accountID) {
		// loop until find the right Account
		type = _itemType;
		for (Account value : this.accountInfo) {
			if (value.get_aid() == _accountID) {// Account Id check
				if (_itemType == 1) { // card
					for (int i = 0; i < 2; i++) {
						if (value.getItemID(_itemType)[i] == _itemID) { // Item Id check
							A = value;
							cardNum = i;
							return true;
						}
					}
				} else { // book
					if (value.getItemID(_itemType)[0] == _itemID) {
						A = value;
						return true;
					}
				}
			}
		}
		return false;
	}

	// confirm [modified] only pwd
	public boolean confirm(int _pwd) {
		if (type == 1) {    // card
			if (A.getPwd(type)[cardNum] == _pwd) {// Account Id check
				return true;
			}
		} else {
			if (A.getPwd(type)[0] == _pwd)
				return true;
		}
		return false;
	}

	// Link Account
	public boolean linkAccount(int _tcid) {
		if (A.addLink(_tcid))
			return true;
		else
			return false;
	}

	// get balance
	public int getBalance() {
		this.WriteData();
		return A.get_balance();
	}

	// charge Traffic Card [modified]
	public boolean chargeTrafficCard(int _money) {
		return this.withdraw(_money);
	}

	// withdraw [modified] too much same as charge Traffic Card
	public boolean withdraw(int _money) {
		int bal;
		// verify sufficient fund

		System.out.println(A.get_balance());
		bal = A.get_balance() - _money;
		if (bal >= 0) {
			A.set_balance(bal);
			return true;
		}
		return false;
	}

	// deposit
	public boolean deposit(int _money) {
		A.set_balance(A.get_balance() + _money);
		return true;
	}

	// check Account [modified] bank ID string
	public String checkAccount(String _bankID, int _accountID) {
		Dest = new Bank(_bankID);
		// this account != dest account
		//**change && -> ||  &&-> it can't transfer even if it isn't same account becase of bank
        for (Account value : Dest.accountInfo) {
            if (value.get_aid() == _accountID) {
                Dest.A = value;
                return value.get_name();
            }
        }
		return null;
	}

	// transfer [modified] boolean
	public boolean transfer(int _money) {
		// renew my Balance & Dest Balance
		if ((this.withdraw(_money)) && (Dest.deposit(_money))) {
			if (this.bankID.equals(Dest.bankID)) {
				for (Account value : accountInfo) {
					if (value.get_name().equals(Dest.A.get_name()))
						value.set_balance(Dest.A.get_balance());
				}
				return true;
			} else{
				Dest.WriteData();
				return true;
			}
		} else {
			return false;
		}
	}
}
