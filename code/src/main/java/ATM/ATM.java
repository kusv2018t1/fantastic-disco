package ATM;

import BankSystem.Bank;
import Item.TrafficCard;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

//전체적으로 바뀐 것은 ok를 모두 boolean type으로 해서 method의 타입이 바뀌었다.

public class ATM {

	private File bootATM;
	private FileReader fr;
	private BufferedReader br;
	private Bank[] bank = new Bank[4];
	private TrafficCard tCard = new TrafficCard();;
	// ATM 소지 Item Amount
	// cashAmount[0] : 10,000
	// cashAmount[1] : 50,000
	// cashAmount[2] : 10$
	// cashAmount[3] : 100$
	private int[] cashAmount = new int[4];

	private int trafficCardAmount;
	private int receiptAmount;
	//거래 중인 Aid / Bank Id
	private int usingAccountID;
	private int usingBankID;

	//거래 중인 서비스 (1 : check / 2 : deposit / 3 : withdraw / 4 : transfer / 5 : issueTrafficCard )
	private int transactionAmount = 0;
	//언어 0 : 한국 / 1 : 미국   -> 계좌 정보와 동일
	private int languageMode;

	//Admin ID
	private int ATMadminID;
	//환율
	private int rate;
	// 출금 원/달러인지 확인
	private int ATMnation;

	public ATM() {
		try {
			bootATM = new File("code/src/main/java/ATM/management.txt");//path.getAbsolutePath() +
			fr = new FileReader(bootATM);
			br = new BufferedReader(fr);

			//mention delete
			br.readLine();

			//declare getStr
			String getStr;

			//10,000 / 50,000 / 10$ / 100$ Amount
			for(int i = 0; i < 4; i++){
				getStr = br.readLine();
				cashAmount[i] = Integer.parseInt(getStr);
			}

			getStr = br.readLine();
			receiptAmount = Integer.parseInt(getStr);

			getStr = br.readLine();
			trafficCardAmount = Integer.parseInt(getStr);

			getStr = br.readLine();
			ATMadminID = Integer.parseInt(getStr);

			getStr = br.readLine();
			rate = Integer.parseInt(getStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int readItem(int itemType, int itemID, String bankID, int accountID) {
		usingAccountID=accountID;
		// kb(usingBankID 0 ) 한국 은행(한국 0)
		if (bankID.equals("kb")) {
			bank[0] = new Bank(bankID);
			usingBankID = 0;
			languageMode = 0;
		}
		// shinhan (usingBankID 1 ) 한국 은행(한국 0)
		else if (bankID.equals("shinhan")) {
			bank[1] = new Bank(bankID);
			usingBankID = 1;
			languageMode = 0;
		}
		// city (usingBankID 2 ) 외국 은행(미국 1)
		else if (bankID.equals("citi")) {
			bank[2] = new Bank(bankID);
			usingBankID = 2;
			languageMode = 1;
		}
		// bankofAmerican (usingBankID 3) 외국 은행(미국 1)
		else if (bankID.equals("bankofamerica")) {
			bank[3] = new Bank(bankID);
			usingBankID = 3;
			languageMode = 1;
		} else	// 유효하지 않은 은행
			return -1;

		if( bank[usingBankID].validCheck(itemType, itemID, accountID) ){
			usingAccountID = accountID;
			return languageMode;
		} else // 유효하지 않은 계좌
			return -1;
	}

	public void selectService(int service) {
		transactionAmount = service;
		switch (service) {
		// 계좌조회
		case 1:
			break;
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
		return;
	}

	public int selectNation(int nation) {
		ATMnation = nation;
		// gui
		//System.out.println("Nation :" + nation);
		// nation 1 = 달러 nation 0 = 한화
		return nation;
	}

	public boolean confirm(int pwd) {
		return bank[usingBankID].confirm(pwd);
	}

	public int insertCash(String[] bill) {

		for(int i = 0; i < bill.length; i++){
			System.out.println(bill[i]);
		}

		// insertCash를 통해 읽는 총 돈, deposit 하는 값
		int money = 0;

		// 들어온 bill의 갯수 만큼 돈다.
		for (int i = 0; i < bill.length; i++) {
			if(bill[i] == null){
				break;
			}

			// bill의 1번쨰 자리가 0이면 한국 돈
			if (bill[i].substring(0, 1).equals("0")) {

				switch (Integer.parseInt(bill[i].substring(1, 3))) {
				// 1000원 bill
				case 00:
					money += 1000;
					break;
				// 5000원 bill
				case 01:
					money += 5000;
					break;
				// 10000원 bill cashAmount[0]- 10000원 양이 250보다 적으면 증가
				case 10:
					money += 10000;
					if (cashAmount[0] < 2500)
						cashAmount[0]++;
					else
						return -1;// ATM안 돈이 가득 차서 더 못들어간다.
					break;
				// 50000원 bill cashAmount[1]- 50000원 양이 250보다 적으면 증가
				case 11:
					money += 50000;
					if (cashAmount[1] < 2500)
						cashAmount[1]++;
					else
						return -1;// ATM안 돈이 가득 차서 더 못들어간다.
					break;
				}
			}
			// bill의 1번쨰 자리가 1이면 외국돈
			else if (bill[i].substring(0, 1).equals("1")) {

				switch (Integer.parseInt(bill[i].substring(1, 3))) {
				// 10달러 bill cashAmount[2]- 10달러 양이 250보다 적으면 증가
				case 00:
					money += 10 * rate;
					if (cashAmount[2] < 2500)
						cashAmount[2]++;
					else
						return -1;// ATM안 돈이 가득 차서 더 못들어간다.
					break;
				// 20달러 bill
				case 01:
					money += 50 * rate;
					break;
				// 100달러 bill cashAmount[3]- 100달러 양이 250보다 적으면 증가
				case 10:
					money += 100 * rate;
					if (cashAmount[3] < 2500)
						cashAmount[3]++;
					else
						return -1; // ATM안 돈이 가득 차서 더 못들어간다.
					break;
				}
			}
		}
		// 만약 입금하려는 계좌가 외국계좌일 경우,(languagemode ==1) 환율로 나눠준다.
		if (languageMode == 1)
			money = money / rate;


		// deposit try
		if(bank[usingBankID].deposit(money)){
			return money;
		}
		else{
			return -1;
		}

	}

	public int enterAmount(int money) {

		//withdraw
		if (transactionAmount == 3) {
			//달러출금
			if (ATMnation == 1) {
				System.out.println("doller");
				if (cashAmount[3] < money / 100 || cashAmount[2] < (money % 100) / 10) {
					//ATM 잔고 부족
					return -1;
				}
				else {
					//한국 계좌
					if(languageMode == 0){
						if(bank[usingBankID].withdraw(money * rate)){
							return money * rate;
						}
						//계좌 잔고 부족
						return -2;
					}
					//해외 계좌
					else{
						if(bank[usingBankID].withdraw(money)){
							return money;
						}
						//계좌 잔고 부족
						return -2;
					}

				}
			}//한화출금
			else if (ATMnation == 0) {
				System.out.println("won");
				if (cashAmount[1] < money / 50000 || cashAmount[0] < (money % 50000) / 10000) {
					System.out.println("withdraw / 원 / ATM잔고부족?");
					return -1;
				}
				else {
					//한국 계좌
					if(languageMode == 0){
						if(bank[usingBankID].withdraw(money)){
							return money;
						}
						return -2;
					}
					//해외 계좌
					else{
						if(bank[usingBankID].withdraw(money / rate)){
							return money / rate;
						}
						return -2;
					}
				}
			}//ㅅㅂ?
			else {
				return -9999;
			}
		} //transfer
		else if (transactionAmount == 4) {
			System.out.println("transfer");
			// money는 송금의 경우 무조건 외국계좌면 달러를 입력하고 한국 계좌이면 한화를 입력한다
			if(bank[usingBankID].transfer(money)){
				return money;
			}
			return -2;
		} //error
		else {
			return -999;
		}
	}

	public int getBalance() {

		PrintWriter pw;

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.bootATM)));

			//10,000 / 50,000 / 10$ / 100$ / receiptAmount / trafficCardAmount / adminID / rate
			pw.println("10,000 / 50,000 / 10$ / 100$ / receiptAmount / trafficCardAmount / adminID / rate");
			for(int i = 0; i < 4; i++){
				pw.println(cashAmount[i]);
			}

			pw.println(receiptAmount);
			pw.println(trafficCardAmount);
			pw.println(ATMadminID);
			pw.println(rate);

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bank[usingBankID].getBalance();
	}
	//
	public boolean printReceipt(){
		receiptAmount--;

		if(receiptAmount > 0){
			return true;
		}
		else{
			return false;
		}
	}


	public boolean setDataRange(int date_range) {
		//카드 부족
		if(this.trafficCardAmount < 1){
			return false;
		}
		else{
			tCard.setDateRange(date_range);
			return true;
		}

	}

	public boolean agreement() {
		//linking
		boolean ok = bank[usingBankID].linkAccount(tCard.getTcid());

		//링킹 성공 발급비
		if (ok) {
			//발급비 계산
			if (languageMode == 0) {
				ok = bank[usingBankID].chargeTrafficCard(3000);
			} else {
				ok = bank[usingBankID].chargeTrafficCard(3000 / rate);
			}

			//계산 성공
			if(ok){
				trafficCardAmount--;
				return true;
			}//계산 실패
			else{
				return false;
			}
		}//링킹 실패
		else{
			return false;
		}

	}

	public String destAccount(String bankID, int accountID) {
		int id;
		if(usingAccountID!=accountID ) {
			if (bankID.equals("kb")) {
				id = 0;
			}
			// shinhan (usingBankID 1 ) 한국 은행
			else if (bankID.equals("shinhan")) {
				id = 1;
			}
			// city (usingBankID 2 ) 외국 은행
			else if (bankID.equals("citi")) {
				id = 2;
			}
			// bankofAmerican (usingBankID 3) 외국 은행
			else if (bankID.equals("bankofAmerican")) {
				id = 3;
			} else {
				return null;
			}
			return bank[usingBankID].checkAccount(bankID, accountID);
		}
		return null;
		// gui 이름이 떠야 한다.
	}



	public void checkResource() {
		String text = "";
		int max=80,condition=0;

		for(int i = 0 ; i<4 ; i++){
			if ( cashAmount[i] < 20) {
				condition=1;
				if (i == 0)
					text = text+"만원권 : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == 1)
					text = text+"오원권 : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == 2)
					text = text+"10$ : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else
					text = text+"100$ : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
			} else if(cashAmount[i] > max){
				condition=1;
				if (i == 0)
					text = text+"만원권 : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == 1)
					text = text+"오원권 : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == 2)
					text = text+"10$ : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else
					text = text+"100$ : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
			}
		}
		if(trafficCardAmount < 20) {
			condition=1;
			text=text+"교통카드 : 부족함 (현재개수 : "+trafficCardAmount+")\n";
		}
		if(receiptAmount < 20){
			condition=1;
			text=text+"명세표 : 부족함 (현재개수 : "+receiptAmount+")\n";
		}

		if (condition==1) {
			// 관리자에게 알람
			String mail = "jhun9409@gmail.com";
			String pw = "davichi123";

			Properties pr = new Properties();
			pr.put("mail.smtp.host", "smtp.gmail.com");
			pr.put("mail.smtp.port", "587");
			pr.put("mail.smtp.auth", "true");
			pr.put("mail.smtp.starttls.enable", "true");

			class Authentication_SMTP extends Authenticator {

				PasswordAuthentication pa;

				public Authentication_SMTP(String mail, String pw) {

					pa = new PasswordAuthentication(mail, pw);
				}

				public PasswordAuthentication getPasswordAuthentication() {
					return pa;
				}
			}
			Authenticator au = new Authentication_SMTP(mail, pw);
			Session session = Session.getInstance(pr, au);

			// receiver
			String receiver = "jhun9409@naver.com";
			String title = "Global ATM 알람";

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(mail));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
				message.setSubject(title);
				message.setText(text);
				Transport.send(message);
				System.out.println("success");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void end() {
		try {
			bootATM = new File("code/src/main/java/ATM/management.txt");//path.getAbsolutePath() +
			fr = new FileReader(bootATM);
			br = new BufferedReader(fr);

			//mention delete
			br.readLine();

			//declare getStr
			String getStr;

			//10,000 / 50,000 / 10$ / 100$ Amount
			for(int i = 0; i < 4; i++){
				getStr = br.readLine();
				cashAmount[i] = Integer.parseInt(getStr);
			}

			getStr = br.readLine();
			receiptAmount = Integer.parseInt(getStr);

			getStr = br.readLine();
			trafficCardAmount = Integer.parseInt(getStr);

			getStr = br.readLine();
			ATMadminID = Integer.parseInt(getStr);

			getStr = br.readLine();
			rate = Integer.parseInt(getStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getATMadminID(){
		return this.ATMadminID;
	}


}

//	private int bankDataDownload() {
//		// bank생성
//		// 한국 은행 생성
//		bank[0] = new Bank("kb");
//		bank[1] = new Bank("shinhan");
//		// 외국 은행 생성
//		bank[2] = new Bank("city");
//		bank[3] = new Bank("bankofAmerican");
//		return 0;
//	}

//	private void bootATM() {
//		// txt파일로 부터 ATM안 현금, 교통카드 . 명세표 용지 량을 받아온다.
//		try {
//			path = new File("src/ATM");
//			bootATM = new File(path.getAbsolutePath() + "/management.txt");
//			fr = new FileReader(bootATM);
//			br = new BufferedReader(fr);
//
//			String getStr = br.readLine();
//			int cash = Integer.parseInt(getStr);
//			cashAmount[0] = cash;
//			cashAmount[1] = cash;
//			cashAmount[2] = cash;
//			cashAmount[3] = cash;
//			getStr = br.readLine();
//			receiptAmount = Integer.parseInt(getStr);
//			getStr = br.readLine();
//			trafficCardAmount = Integer.parseInt(getStr);
//			getStr = br.readLine();
//			ATMadminID = Integer.parseInt(getStr);
//			getStr = br.readLine();
//			rate = Integer.parseInt(getStr);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	public int getBalance(){
//		return bank[usingBankID].getBalance();
//	}

