package garage;

import hardware.BarcodePrinter;
import hardware.ElectronicLock;
import hardware.PinCodeTerminal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class BicycleGarageManager {
	private BarcodePrinter printer;
	private ElectronicLock entryLock,exitLock;
	private PinCodeTerminal terminal;
	private LinkedList<User> regUsers;
	private LinkedList<Bicycle> regBicycles;
	private LinkedList<Bicycle> checkBicycles;
	private LinkedList<Entry<User,Date>> checkUsers;
	private LinkedList<User> waitQueue;
	private int maxCheck;
	private StringBuilder sb;
	private int time = 20;
	private int seconds=0;
	private ArrayList<String> pinCodeList;
	private ArrayList<String> barcodeList;
	
	
	
	public BicycleGarageManager(){
		loadFiles();
		createPinCodeArray();
		createBarcodeArray();
	}
	

	
	//////////////////////////
	//////// STARTUP /////////
	//////////////////////////

	@SuppressWarnings("unchecked")
	private boolean loadFile(){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("lists.dat"));
			regUsers = (LinkedList<User>) in.readObject();
			regBicycles = (LinkedList<Bicycle>) in.readObject();
			checkUsers = (LinkedList<Entry<User, Date>>) in.readObject();
			checkBicycles = (LinkedList<Bicycle>) in.readObject();
			waitQueue = (LinkedList<User>) in.readObject();
			maxCheck = in.readInt();
			in.close();
		}catch(Exception e){
			regUsers = new LinkedList<User>();
			regBicycles = new LinkedList<Bicycle>();
			checkUsers = new LinkedList<Entry<User,Date>>();
			checkBicycles = new LinkedList<Bicycle>();
			waitQueue = new LinkedList<User>();
			maxCheck = 1000;
			return false;
			
		}
		return true;
	}
	
	private Boolean saveFile(){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("lists.dat"));
			out.writeObject(regUsers);
			out.writeObject(regBicycles);
			out.writeObject(checkUsers);
			out.writeObject(checkBicycles);
			out.writeObject(waitQueue);
			out.writeInt(maxCheck);
			out.close();
			return true;
		}catch(Exception e){
		} 
		return false;
	}
	
	public void saveFiles(){
		saveFile();
	}
	
	private void loadFiles(){
		loadFile();
	}
	
	public boolean registerHardwareDrivers(BarcodePrinter printer, ElectronicLock entryLock, 
		ElectronicLock exitLock, PinCodeTerminal terminal){
		this.printer = printer;
		this.entryLock = entryLock;
		this.exitLock = exitLock;
		this.terminal = terminal;
		return false;
	}
	
	public void start(){	
		Timer timer =new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				if(checkUsers.size()>0){
					long minutes=new Date().getTime()-checkUsers.peek().getB().getTime();
					if(minutes>=15*60*1000){
						checkUsers.getFirst().getA().setGarageStatus(false);
						checkUsers.removeFirst();
					}
				}
				
			}
			
		},1000,1000);
		
		Timer timer2 =new Timer();
		timer2.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				if(sb.length()>0){
					seconds++;
					if(seconds>=time){
						sb.setLength(0);
					}
				}
			}
		},1000,1000);
		
		Timer timer3 =new Timer();
		timer3.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				saveFiles();
			}
		},60*1000,60*1000);
		
		
		sb = new StringBuilder();
	}
	
	//////////////////////////////
	//////// REG N UNREG /////////
	//////////////////////////////
	
	public boolean registerUser(String surName, String  lastName, String personNumber,
			String phoneNumber, String mailAdress){
		String pin = generatePinCode();
		if(pin==null){
			return false;
		}
		User temp = new User(surName, lastName, personNumber, phoneNumber, mailAdress); 
		temp.setPinCode(pin);
		
		for(User u:regUsers){
			if(u.getPersonNumber().equals(temp.getPersonNumber())){
				return false;
			}
		}
		
		
		
		regUsers.add(temp);
		return true;
		
	}
	
	public boolean unregisterUser(User u){
		
		Iterator<User> itrU = regUsers.iterator();
		while(itrU.hasNext()){
			User temp = itrU.next();
			if(temp.getPersonNumber().equals(u.getPersonNumber())){
				if(u.getBicycle(0)==null&&u.getBicycle(1)==null){
					
					Iterator<Entry<User,Date>> itrE = checkUsers.iterator();
					while(itrE.hasNext()){
						Entry<User,Date> e = itrE.next();
						if(temp.getPersonNumber().equals(e.getA().getPersonNumber())){
							itrE.remove();
						}
					}
					pinCodeList.add(temp.getPinCode());
					Iterator<User> itr = waitQueue.iterator();
					while(itr.hasNext()){
						User temp2 = itr.next();
						if(temp2.getPersonNumber().equals(u.getPersonNumber())){
							itr.remove();
						}
					}
					itrU.remove();
					return true;
				}else{
					return false;
				}
			}
		}
		
		
		return false;
	}
	
	public boolean registerBicycle(User u){
		if(regBicycles.size()<maxCheck){
			String barcode = generateBarcode();
			if(barcode==null){
				return false;
			}
			printer.printBarcode(barcode);
			Bicycle b = new Bicycle(barcode,u);
			if(u.addBicycle(b)){
				return regBicycles.add(b);
			}
		}
		return false;
	}
	
	public boolean unregisterBicycle(String barcode){
		Iterator<Bicycle> itr = regBicycles.iterator();
		
		while (itr.hasNext()) {
			Bicycle bic = itr.next();
			String str1 = (String) bic.getBarcode();
		    if (str1.equals(barcode)) {
		    	bic.getOwner().removeBicycle(bic);
		    	System.out.println(barcode);
		    	barcodeList.add(barcode);
		    	itr.remove();
		    	return true;
		    }
		}
		return false;
	}

	////////////////////////////////////////////
	/////////SOMEWHAT RELATED TO REG/UNREG//////
	////////////////////////////////////////////
	public boolean changePinCode(User u,String pinCode){
		if(pinCode.length() != 4 || !pinCode.matches("[0-9]+")){
			return false;
		}
		Iterator<String> itr = pinCodeList.iterator();
		String pin="";
		boolean test=false;
		while(itr.hasNext()){
			String temp = itr.next();
			if(temp.equals(pinCode)){
				pin=u.getPinCode();
				u.setPinCode(pinCode);
				itr.remove();
				test=true;
			}
		}
		if(test){
			System.out.println(pin);
			pinCodeList.add(pin);
			return true;
		}
		return false;
	}
	
	public boolean changeBarcode(Bicycle b,String barcode){
		if(barcode.length() != 5 || !barcode.matches("[0-9]+")){
			return false;
		}
		
		Iterator<String> itr = barcodeList.iterator();
		String bar="";
		boolean test=false;
		while(itr.hasNext()){
			String temp = itr.next();
			if(temp.equals(barcode)){
				System.out.println(barcode);
				bar=b.getBarcode();
				b.setBarcode(barcode);
				itr.remove();
				test=true;
			}
		}
		if(test){
			System.out.println(barcode);
			pinCodeList.add(bar);
			return true;
		}
		return false;
	}
	
	private void createPinCodeArray(){
		pinCodeList = new ArrayList<String>();
		for(int i=0;i<10000;i++){
			String temp=""+i;
			int length = temp.length();
			for(int j=0;j<4-length;j++){

				String tmp="0";
				tmp += temp;
				temp = tmp;
			}
			pinCodeList.add(temp);
		}
		for(User u:regUsers){
			Iterator<String> itr = pinCodeList.iterator();
			while(itr.hasNext()){
				if(itr.next().equals(u.getPinCode())){
					itr.remove();
				}
			}
		}
	}
	
	private void createBarcodeArray(){
		barcodeList = new ArrayList<String>();
		for(int i=0;i<100000;i++){
			String temp=""+i;

			int length = temp.length();
			for(int j=0;j<5-length;j++){
				String tmp="0";
				tmp += temp;
				temp = tmp;
			}
			barcodeList.add(temp);
		}
		for(Bicycle b:regBicycles){
			Iterator<String> itr = barcodeList.iterator();
			while(itr.hasNext()){
				if(itr.next().equals(b.getBarcode())){
					itr.remove();
				}
			}
		}
	}
	
	private String generatePinCode(){
		int random = (int)(Math.random()*pinCodeList.size());
		String temp = pinCodeList.get(random);
		pinCodeList.remove(random);
		return temp;
	}
	
	private String generateBarcode(){
		int random = (int)(Math.random()*barcodeList.size());
		String temp = barcodeList.get(random);
		barcodeList.remove(random);
		return temp;
	}
	
	public boolean moveToQueue(User u){
		boolean same=false;
		for(User temp:waitQueue){
			if(u.getPersonNumber()==temp.getPersonNumber()){
				same=true;
			}
		}
		if(!same){
			return waitQueue.add(u);
		}
		return false;
	}
	
	//////////////////////////
	//////// TERMINALS ///////
	//////////////////////////

	public void entryCharacter(char c){
		seconds=0;
		if(c=='*'){
			sb.setLength(0);
		}
		sb.append(c);
		if(sb.toString().charAt(0)=='*'){
			if(sb.length()>=6 && sb.toString().charAt(5)=='#'){
				//unlock door
				boolean bUserInRegList=false;
				for(User u:regUsers){
					if(u.getPinCode().equals(sb.substring(1, 5).toString())){
						if(!(u.getBicycle(0)==null&&u.getBicycle(0)==null)){
							boolean bicycleChecked=false;
							for(int i=0;i<u.getBicycleAmount();i++){
								if(u.getBicycle(i).getGarageStatus()){
									bicycleChecked=true;
								}
							}
							if(bicycleChecked){
								bUserInRegList=true;
								
								Iterator<Entry<User,Date>> itr = checkUsers.iterator();
								while(itr.hasNext()){
									Entry<User,Date> e = itr.next();
									if(e.getA().getPersonNumber().equals(u.getPersonNumber())){
										itr.remove();
									}
								}
								
								u.setGarageStatus(true);
								checkUsers.add(new Entry<User, Date>(u, new Date())); //spara
								terminal.lightLED(1,3);
								sb.setLength(0);
								entryLock.open(time);
							}else{
								terminal.lightLED(0,3);
								sb.setLength(0);
							}
						}else{
							terminal.lightLED(0,3);
							sb.setLength(0);
						}
					}
				}
				if(!bUserInRegList){
					new Thread(new Runnable() {
						public void run() {
							try {
								terminal.lightLED(0,1);
								Thread.sleep(2000);
								terminal.lightLED(0,1);
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
					
				}
				sb.setLength(0);
			}else if(sb.length()>=6){
				new Thread(new Runnable() {
					public void run() {
						try {
							terminal.lightLED(0,1);
							Thread.sleep(2000);
							terminal.lightLED(0,1);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
				sb.setLength(0);
			}else{
				terminal.lightLED(1,1);
			}
		}else{
			terminal.lightLED(0,1);
			sb.setLength(0);
		}
		
	}
		
	public void entryBarcode(String bicycleID){
		boolean notRegistered=true;
		for(Bicycle b:regBicycles){
			if(b.getBarcode().equals(bicycleID)){
				notRegistered=false;
				if(b.getPaymentStatus()){
					Boolean bSame = false;
					for(Bicycle b2:checkBicycles){
						if(b2.getBarcode().equals(bicycleID)){
							bSame =true;
						}
					}
					if(!bSame){
						
						b.setGarageStatus(true);
						checkBicycles.add(b);
						
					}
					User u = b.getOwner();
					Iterator<Entry<User,Date>> itr = checkUsers.iterator();
					while(itr.hasNext()){
						Entry<User,Date> e = itr.next();
						if(e.getA().getPersonNumber().equals(u.getPersonNumber())){
							itr.remove();
						}
					}
					
					u.setGarageStatus(true);
					checkUsers.add(new Entry<User,Date>(u,new Date()));
					
					entryLock.open(time);
				}else{
					terminal.lightLED(0, 3);
				}
			}
		}
		if(notRegistered){
			terminal.lightLED(0, 3);
		}
	}
	
	public void exitBarcode(String bicycleID){		
		Iterator<Bicycle> itrB = checkBicycles.iterator();
		boolean rightUser =false;
		while(itrB.hasNext()){
			Bicycle b = itrB.next();
			if(b.getBarcode().equals(bicycleID)){
				Iterator<Entry<User,Date>> itrE = checkUsers.iterator();
				while(itrE.hasNext()){
					Entry<User,Date> e = itrE.next();
					if(e.getA().getPersonNumber().equals(b.getOwner().getPersonNumber())){
						rightUser=true;
						e.getA().setGarageStatus(false);
						b.setGarageStatus(false);
						itrE.remove();
						itrB.remove();
						exitLock.open(time);
					}
				}
			}
		}
		if(!rightUser){
			for(Bicycle b:regBicycles){
				if(b.getBarcode().equals(bicycleID)){
					Iterator<Entry<User,Date>> itrE = checkUsers.iterator();
					while(itrE.hasNext()){
						Entry<User,Date> e = itrE.next();
						if(e.getA().getPersonNumber().equals(b.getOwner().getPersonNumber())){
							rightUser=true;
							e.getA().setGarageStatus(false);
							itrE.remove();
							exitLock.open(time);
						}
					}
				}
			}
		}
		
		
		if(!rightUser){
			new Thread(new Runnable() {
				public void run() {
					try {
						terminal.lightLED(0,1);
						Thread.sleep(2000);
						terminal.lightLED(0,1);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
		
	//////////////////////////////
	///////// GET N SET //////////
	//////////////////////////////
	
	public void setRegisterLimit(int newMax){
		maxCheck = newMax;
	}
	
	public int getRegisterLimit(){
		return maxCheck;
	}
	
	public User getUser(String personNumber){
		Iterator<User> itr = regUsers.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			String number = (String) user.getPersonNumber();
		    if (number.equals(personNumber)) {
		      return user;
		    }
		  
		}
		return null;
	}

	public Bicycle getBicycle(String bicycleID){
		Iterator<Bicycle> itr = regBicycles.iterator();
		while (itr.hasNext()) {
			Bicycle bicycle = itr.next();
			String str1 = (String) bicycle.getBarcode();
		    if (str1.equals(bicycleID)) {
		      return bicycle;
		    }
		  
		}
		return null;
	}

	//////////////////////////////
	////////// GET LISTS /////////
	//////////////////////////////
	
	public LinkedList<User> getUsers(){
		return regUsers;
	}
	
	public LinkedList<Bicycle> getBicycles(){
		return regBicycles;
	}
	
	public LinkedList<Entry<User,Date>> getCheckedInUsers() {
		return checkUsers;
	}
	
	public LinkedList<Bicycle> getCheckedInBicycles() {
		return checkBicycles;
	}
	
	public LinkedList<User> getQueue(){
		return waitQueue;
	}
	
}




