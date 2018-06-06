package ATM;

import BankSystem.Bank;
import Item.TrafficCard;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;


/* * ATM class
 *   Main System
 */
public class ATM {

	//File "management.txt"
	//path mode
	private boolean path = false;
	//true : path_1 : IDE
	private String path_1 = "code/src/main/java/ATM/management.txt";
	//false : path_2 : .jar
	private String path_2 = "management.txt";

	private File bootATM;
	private FileReader fr;
	private BufferedReader br;

	//Bank Index
	private final int BANK_TYPE_NUM = 4;
	private final int BANK_KB = 0;
	private final int BANK_SH = 1;
	private final int BANK_CT = 2;
	private final int BANK_BA = 3;
	private Bank[] bank = new Bank[BANK_TYPE_NUM];
	private int usingBankID;

	//ATM Item "Traffic Card"
	private TrafficCard tCard;
	private int trafficCardAmount;

	//ATM Item "Cash"
	private final int MAX_CASH = 1000;
	private final int CASH_TYPE_NUM = 4;
	private final int CASH_TYPE_MAN_WON = 0;
	private final int CASH_TYPE_O_MAN_WON = 1;
	private final int CASH_TYPE_TEN_DOLLAR = 2;
	private final int CASH_TYPE_HUNDRED_DOLLAR = 3;
	private int[] cashAmount = new int[CASH_TYPE_NUM];

	//ATM Item "Receipt"
	private int receiptAmount;

	//Transaction Account ID
	private int usingAccountID;

	//Transaction Mode
	private final int READY_MODE = 0;
	private final int CHECK_MODE = 1;
	private final int DEPOSIT_MODE = 2;
	private final int WITHDRAW_MODE = 3;
	private final int TRANSFER_MODE = 4;
	private final int ISSUE_MODE = 5;
	private int transactionMode = READY_MODE;

	//Account Nation (KOR / ENG)
	private final int ACC_KOR = 0;
	private final int ACC_ENG = 1;
	private int accountNation;

	//Admin ID
	private int adminID;
	//Admin Email ID/PW
	private String adminMailID;
	private String adminMailPW;
	private String getMailID;

	//Won < - > $ rating
	private int rate;

	//withdraw Cash type
	private final int CASH_KOR = 0;
	private final int CASH_ENG = 1;
	private int cashNation;

	//Fee
	private final int FEE_KOR = 1000;
	private final int FEE_ENG = 1;

	//$ Type
	private final int TEN_DOLLAR = 10;
	private final int HUNDRED_DOLLAR = 100;
	//won Type
	private final int MAN_WON = 10000;
	private final int OMAN_WON = 50000;

	//alarm condition
	private boolean alarmCondition = false;

	/* Initialization
	 * Loading Text file "management.txt"
	 * Setting attributes
	 */
	public ATM() {

		//declare getStr
		String getStr;


		try {
			if(path){
				bootATM = new File(path_1);
			}
			else{
				bootATM = new File(path_2);
			}
			fr = new FileReader(bootATM);
			br = new BufferedReader(fr);

			//mention delete
			br.readLine();

			//10,000 / 50,000 / 10$ / 100$ Amount
			for(int i = 0; i < CASH_TYPE_NUM; i++){
				getStr = br.readLine();
				cashAmount[i] = Integer.parseInt(getStr);
			}

			getStr = br.readLine();
			receiptAmount = Integer.parseInt(getStr);

			tCard = new TrafficCard();
			trafficCardAmount = tCard.getCardAmount();
			System.out.println("trafficCard Amount in ATM " + trafficCardAmount);

			getStr = br.readLine();
			adminID = Integer.parseInt(getStr);

			getStr = br.readLine();
			adminMailID = getStr;

			getStr = br.readLine();
			adminMailPW = getStr;

			getStr = br.readLine();
			getMailID = getStr;

			getStr = br.readLine();
			rate = Integer.parseInt(getStr);
		} catch (FileNotFoundException e) {
			//mistake! path setting
			if(!path){
				System.out.println("mistake! path setting");

				path = !path;

				try{
					if(path){
						bootATM = new File(path_1);
					}
					else{
						bootATM = new File(path_2);
					}
					fr = new FileReader(bootATM);
					br = new BufferedReader(fr);

					//mention delete
					br.readLine();

					//10,000 / 50,000 / 10$ / 100$ Amount
					for(int i = 0; i < CASH_TYPE_NUM; i++){
						getStr = br.readLine();
						cashAmount[i] = Integer.parseInt(getStr);
					}

					getStr = br.readLine();
					receiptAmount = Integer.parseInt(getStr);

					tCard = new TrafficCard();
					trafficCardAmount = tCard.getCardAmount();
					System.out.println("trafficCard Amount in ATM " + trafficCardAmount);

					getStr = br.readLine();
					adminID = Integer.parseInt(getStr);

					getStr = br.readLine();
					adminMailID = getStr;

					getStr = br.readLine();
					adminMailPW = getStr;

					getStr = br.readLine();
					getMailID = getStr;

					getStr = br.readLine();
					rate = Integer.parseInt(getStr);
				} catch (IOException e_e){
					System.out.println("can't read textFile (management.txt) please check path.");
				}
			}
		} catch (IOException e) {
			System.out.println("can't read textFile (management.txt) please check path.");
		}
	}

	/* Method
	 * Description : 읽힌 아이템의 계좌 정보를 통해 유효한 값인지 확인한다.
	 */
	public int readItem(int itemType, int itemID, String bankID, int accountID) {
		usingAccountID=accountID;
		// kb(usingBankID 0 ) 한국 은행(한국 0)
		if (bankID.equals("kb")) {
			bank[BANK_KB] = new Bank(bankID);
			usingBankID = BANK_KB;
			accountNation = ACC_KOR;
		}
		// shinhan (usingBankID 1 ) 한국 은행(한국 0)
		else if (bankID.equals("shinhan")) {
			bank[BANK_SH] = new Bank(bankID);
			usingBankID = BANK_SH;
			accountNation = ACC_KOR;
		}
		// city (usingBankID 2 ) 외국 은행(미국 1)
		else if (bankID.equals("citi")) {
			bank[BANK_CT] = new Bank(bankID);
			usingBankID = BANK_CT;
			accountNation = ACC_ENG;
		}
		// bankofAmerican (usingBankID 3) 외국 은행(미국 1)
		else if (bankID.equals("bankofamerica")) {
			bank[BANK_BA] = new Bank(bankID);
			usingBankID = BANK_BA;
			accountNation = ACC_ENG;
		}
		else {   // 유효하지 않은 은행
          return -1;
      }


      System.out.println("???");
      //valid check item
		if( bank[usingBankID].validCheck(itemType, itemID, accountID) ){
			usingAccountID = accountID;
			return accountNation;
		} else // 유효하지 않은 계좌
			return -1;
	}

	/* Method
	 * Description : 고객이 선택한 서비스 정보를 저장한다.
	 */
	public void selectService(int service) {
		transactionMode = service;
		//Exception
		if(service != CHECK_MODE && service != DEPOSIT_MODE && service != WITHDRAW_MODE && service != TRANSFER_MODE && service != ISSUE_MODE){
			System.out.println("No Service...");
		}
	}

	/* Method
	 * Description : 출금시 어떤 지폐(원/달러)를 원하는지 선택한 정보를 저장한다.
	 */
	public int selectNation(int nation) {
		this.cashNation = nation;

		return nation;
	}

	/* Method
	 * Description : 고객이 입력한 비밀번호를 확인한다.
	 */
	public boolean confirm(int pwd) {
		return bank[usingBankID].confirm(pwd);
	}

	/* Method
	 * Description : 고객이 입금한 지폐를 계산한다.
	 */
	public int insertCash(String[] bill) {

		for(int i = 0; i < bill.length; i++){
			System.out.println("billCode : " + bill[i]);
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
					if (cashAmount[0] < MAX_CASH)
						cashAmount[0]++;
					else
						return -1;// ATM안 돈이 가득 차서 더 못들어간다.
					break;
				// 50000원 bill cashAmount[1]- 50000원 양이 250보다 적으면 증가
				case 11:
					money += 50000;
					if (cashAmount[1] < MAX_CASH)
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
					if (cashAmount[2] < MAX_CASH)
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
					if (cashAmount[3] < MAX_CASH)
						cashAmount[3]++;
					else
						return -1; // ATM안 돈이 가득 차서 더 못들어간다.
					break;
				}
			}
		}
		// 만약 입금하려는 계좌가 외국계좌일 경우, 환율로 나눠준다.
		if (accountNation == ACC_ENG)
			money = money / rate;


		// deposit try
		if(bank[usingBankID].deposit(money)){
			return money;
		}
		else{
			return -1;
		}

	}

	/* Method
	 * Description : 출금 / 송금시 거래할 금액을 계산한다.
	 */
	public int enterAmount(int money) {

		//withdraw
		if (transactionMode == WITHDRAW_MODE) {
			//달러출금
			if (cashNation == CASH_ENG) {
				System.out.println("doller withDraw");

				int amountHundredDollar = money / HUNDRED_DOLLAR;
				money = money - (amountHundredDollar * HUNDRED_DOLLAR);
				int amountTenDollar = money / TEN_DOLLAR;


				//큰 단위 + 작은 단위 부족한지
				if (cashAmount[CASH_TYPE_HUNDRED_DOLLAR] < amountHundredDollar || cashAmount[CASH_TYPE_TEN_DOLLAR] < amountTenDollar) {

					//작은 단위로 아에 부족한지
					if(cashAmount[CASH_TYPE_TEN_DOLLAR] < amountTenDollar + (amountHundredDollar*10) ) {
						//ATM 잔고 부족
						return -1;

					}
					else{
						//정상 거래 (작은 단위로만)
						//한국 계좌
						if(accountNation == ACC_KOR){
							if(bank[usingBankID].withdraw(((amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) * rate) + FEE_KOR)){
								//ATM 10$ 감소
								this.cashAmount[CASH_TYPE_TEN_DOLLAR] -= amountTenDollar + (amountHundredDollar*TEN_DOLLAR);

								return (amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) * rate;
							}
							//계좌 잔고 부족
							return -2;
						}
						//해외 계좌
						else{
							if(bank[usingBankID].withdraw((amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) + FEE_ENG)){
								//ATM 10$ 감소
								this.cashAmount[CASH_TYPE_TEN_DOLLAR] -= amountTenDollar + (amountHundredDollar*TEN_DOLLAR);

								return (amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR);
							}
							//계좌 잔고 부족
							return -2;
						}
					}

				}
				//정상 거래 (큰 단위 + 작은 단위)
				else {
					//한국 계좌
					if(accountNation == ACC_KOR){
						if(bank[usingBankID].withdraw((amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) * rate + FEE_KOR)){

							//ATM 100$ 감소
							this.cashAmount[CASH_TYPE_HUNDRED_DOLLAR] -= amountHundredDollar;
							//ATM 10$ 감소
							this.cashAmount[CASH_TYPE_TEN_DOLLAR] -= amountTenDollar;

							return (amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) * rate;
						}
						//계좌 잔고 부족
						return -2;
					}
					//해외 계좌
					else{
						if(bank[usingBankID].withdraw((amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR) + FEE_ENG)){

							//ATM 100$ 감소
							this.cashAmount[CASH_TYPE_HUNDRED_DOLLAR] -= amountHundredDollar;
							//ATM 10$ 감소
							this.cashAmount[CASH_TYPE_TEN_DOLLAR] -= amountTenDollar;

							return (amountTenDollar*TEN_DOLLAR + amountHundredDollar*HUNDRED_DOLLAR);
						}
						//계좌 잔고 부족
						return -2;
					}

				}
			}//한화출금
			else if (cashNation == CASH_KOR) {
				System.out.println("won withDraw");

				int amountOManWon = money / OMAN_WON;
				money = money - (amountOManWon * OMAN_WON);
				int amountManWon = money / MAN_WON;


				////큰 단위 + 작은 단위 부족한지
				if (cashAmount[CASH_TYPE_O_MAN_WON] < amountOManWon || cashAmount[CASH_TYPE_MAN_WON] < amountManWon) {

					//작은 단위로 아에 부족한지
					if(cashAmount[CASH_TYPE_MAN_WON] < amountManWon + (amountOManWon * 5)){
						//ATM 잔고 부족
						return -1;
					}
					//작은 단위로만 출금
					else{
						//한국 계좌
						if(accountNation == ACC_KOR){
							if(bank[usingBankID].withdraw( (amountManWon*MAN_WON + amountOManWon*OMAN_WON)  + FEE_KOR)){

								//ATM 지폐 감소
								cashAmount[CASH_TYPE_MAN_WON] -= (amountManWon + amountOManWon*5);
								//거래 금액
								return (amountManWon*MAN_WON + amountOManWon*OMAN_WON);
							}
							return -2;
						}
						//해외 계좌
						else{
							if(bank[usingBankID].withdraw(((amountManWon*MAN_WON + amountOManWon*OMAN_WON) / rate)   + FEE_ENG)){

								//ATM 지폐 감소
								cashAmount[CASH_TYPE_MAN_WON] -= (amountManWon + amountOManWon*5);
								//거래 금액
								return ( (amountManWon*MAN_WON + amountOManWon*OMAN_WON) / rate );
							}
							return -2;
						}
					}

				}
				//큰 단위 + 작은 단위 출금
				else {
					//한국 계좌
					if(accountNation == ACC_KOR){
						if(bank[usingBankID].withdraw((amountManWon*MAN_WON + amountOManWon*OMAN_WON) + FEE_KOR)){
							//ATM 지폐 감소
							cashAmount[CASH_TYPE_MAN_WON] -= (amountManWon);
							cashAmount[CASH_TYPE_O_MAN_WON] -= amountOManWon;
							//거래 금액
							return (amountManWon*MAN_WON + amountOManWon*OMAN_WON);
						}
						return -2;
					}
					//해외 계좌
					else{
						if(bank[usingBankID].withdraw(((amountManWon*MAN_WON + amountOManWon*OMAN_WON) / rate) + FEE_ENG)){

							//ATM 지폐 감소
							cashAmount[CASH_TYPE_MAN_WON] -= (amountManWon);
							cashAmount[CASH_TYPE_O_MAN_WON] -= amountOManWon;
							//거래 금액

							return ( (amountManWon*MAN_WON + amountOManWon*OMAN_WON) / rate);
						}
						return -2;
					}
				}
			}//Cant'be
			else {
				return -9999;
			}
		} //transfer
		else if (transactionMode == TRANSFER_MODE) {
			System.out.println("transfer");
			// 한국 계좌 원으로 보냄   //money : ~만원
			if(accountNation == ACC_KOR){
				if(bank[usingBankID].transfer(money + FEE_KOR)){
					return money;
				}
				return -2;
			}
			//해외 계좌 달러로 보냄   // money : ~0$
			else{
				if(bank[usingBankID].transfer(money + FEE_ENG)){
					return money;
				}
				return -2;
			}
		} //error : Withdraw 도 아니고 transfereh 아니고?
		else {
			return -999;
		}
	}

	/* Method
	 * Description : 거래 후, 잔액을 가져오고, ATM의 수정된 소지물 정보를 저장한다.
	 */
	public int getBalance() {

		PrintWriter pw;

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.bootATM)));

			//10,000 / 50,000 / 10$ / 100$ / receiptAmount / adminID / rate
			pw.println("10,000/50,000/10$/100$/receiptAmount/adminID/mailID/mailPW/getMailID/");
			for(int i = 0; i < CASH_TYPE_NUM; i++){
				pw.println(cashAmount[i]);
				System.out.println(" billAmount in ATM["+i+"] : "+cashAmount[i]);
			}

			pw.println(receiptAmount);
			System.out.println("receiptAmount in ATM : "+receiptAmount);
			pw.println(adminID);
			pw.println(adminMailID);
			pw.println(adminMailPW);
			pw.println(getMailID);
			pw.println(rate);

			pw.close();
		} catch (IOException e) {
			System.out.println("can't write textFile (management.txt) please check path.");
		}

		return bank[usingBankID].getBalance();
	}

	/* Method
	 * Description : 명세표 출력시 명세표 용지를 계산한다.
	 */
	public boolean printReceipt(){
		receiptAmount--;

		if(receiptAmount > 0){
			return true;
		}
		else{
			return false;
		}
	}

	/* Method
	 * Description : 고객이 원하는 교통카드 사용 날짜를 계산한다.
	 */
	public boolean setDataRange(int date_range) {

		//카드 부족
		if(tCard.getTcid() <= 0){
			System.out.println("카드 부족");
			return false;
		}
		else{
			//can't set 0 day
			if(date_range == 0){
				return false;
			}
			else{
				tCard.setDateRange(date_range);
				return true;
			}
		}
	}

	/* Method
	 * Description : 교통카드 발급시 고객의 동의 여부를 판단한다.
	 */
	public boolean agreement() {
		//first link after charge???WTf
		//plz first charge after link........ done
		boolean ok;

		//charge!
		if (accountNation == ACC_KOR) {
			ok = bank[usingBankID].chargeTrafficCard(3000);
			System.out.println("3000won charge");
		} else {
			ok = bank[usingBankID].chargeTrafficCard(3000 / rate);
			System.out.println("3$ charge");
		}

		//linking
		if (ok) {
			 ok = bank[usingBankID].linkAccount(tCard.getTcid());
			//link 성공
			if(ok){

				tCard.setAccountID(usingAccountID);

				System.out.println("발급 카드 tcid : "+tCard.getTcid());
				//새로운 카드 뽑기
				tCard = new TrafficCard();
				trafficCardAmount = tCard.getCardAmount();
				System.out.println("다음 대기중인 카드 tcid : "+tCard.getTcid());

				return true;
			}//link 실패
			else{
				return false;
			}
		}//비용 부족
		else{
			return false;
		}

	}

	/* Method
	 * Description : 송금시 수신 계좌가 유효한지 검사한다.
	 */
	public String destAccount(String bankID, int accountID) {
		return bank[usingBankID].checkAccount(bankID, accountID);
	}

	/* Method
	 * Description : ATM의 소지 자원이 충분한지 검사한다.
	 */
	public void checkResource() {
		String alarmMsg = "";
		//alarmRange : ATM 소지 가능 제한
		int alarmRange = (MAX_CASH - 100);

		for(int i = 0 ; i<CASH_TYPE_NUM ; i++){
			if ( cashAmount[i] < 100) {
				alarmCondition = true;
				if (i == CASH_TYPE_MAN_WON)
					alarmMsg = alarmMsg+"만원권 : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == CASH_TYPE_O_MAN_WON)
					alarmMsg = alarmMsg+"오원권 : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == CASH_TYPE_TEN_DOLLAR)
					alarmMsg = alarmMsg+"10$ : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
				else
					alarmMsg = alarmMsg+"100$ : 부족함 (현재개수 : " + cashAmount[i] + ")\n";
			} else if(cashAmount[i] > alarmRange){
				alarmCondition = true;
				if (i == CASH_TYPE_MAN_WON)
					alarmMsg = alarmMsg+"만원권 : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == CASH_TYPE_O_MAN_WON)
					alarmMsg = alarmMsg+"오원권 : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else if (i == CASH_TYPE_TEN_DOLLAR)
					alarmMsg = alarmMsg+"10$ : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
				else
					alarmMsg = alarmMsg+"100$ : 너무 많음 (현재개수 : " + cashAmount[i] + ")\n";
			}
		}
		if(trafficCardAmount < 100) {
			alarmCondition = true;
			alarmMsg=alarmMsg+"교통카드 : 부족함 (현재개수 : "+trafficCardAmount+")\n";
		}
		if(receiptAmount < 100){
			alarmCondition = true;
			alarmMsg=alarmMsg+"명세표 : 부족함 (현재개수 : "+receiptAmount+")\n";
		}

		if (alarmCondition) {
			// 관리자에게 알람
			String mail = adminMailID;
			String pw = adminMailPW;

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
			String receiver = getMailID;
			String title = "Global ATM 알람";

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(mail));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
				message.setSubject(title);
				message.setText(alarmMsg);
				Transport.send(message);
				System.out.println("alarm success");

			} catch (AuthenticationFailedException ae){
				System.out.println("Wrong Mail Account...! plz correcting id/pw in management.txt");
			} catch (AddressException e) {
				System.out.println("Wrong Mail Account...! plz correcting id/pw in management.txt");
			} catch (MessagingException e) {
				System.out.println("Wrong Mail Message...! plz check Alarm");
			}
		}
	}

	/* Method
	 * Description : 관리자가 ATM 소지 자원을 수정한 후, 수정된 정보를 저장한다.
	 */
	public void end() {
		try {
			if(path){
				bootATM = new File(path_1);
			}
			else {
				bootATM = new File(path_1);
			}
			fr = new FileReader(bootATM);
			br = new BufferedReader(fr);

			//mention delete
			br.readLine();

			//declare getStr
			String getStr;

			//10,000 / 50,000 / 10$ / 100$ Amount
			for(int i = 0; i < CASH_TYPE_NUM; i++){
				getStr = br.readLine();
				cashAmount[i] = Integer.parseInt(getStr);
			}

			getStr = br.readLine();
			receiptAmount = Integer.parseInt(getStr);

			trafficCardAmount = tCard.getCardAmount();
			System.out.println("trafficCard Amount in ATM " + trafficCardAmount);

			getStr = br.readLine();
			adminID = Integer.parseInt(getStr);

			getStr = br.readLine();
			adminMailID = getStr;

			getStr = br.readLine();
			adminMailPW = getStr;

			getStr = br.readLine();
			getMailID = getStr;

			getStr = br.readLine();
			rate = Integer.parseInt(getStr);
		} catch (FileNotFoundException e) {
			System.out.println("can't read textFile (management.txt) please check path.");
		} catch (IOException e) {
			System.out.println("can't read textFile (management.txt) please check path.");
		}
	}

	/* Method
	 * Description : ATM 관리자 ID를 반환한다.
	 */
	public int getAdminID(){
		return this.adminID;
	}
}

