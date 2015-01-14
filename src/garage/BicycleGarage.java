package garage;

import gui.GUI;
import hardware.BarcodePrinterTestDriver;
import hardware.BarcodeReaderEntryTestDriver;
import hardware.BarcodeReaderExitTestDriver;
import hardware.ElectronicLockTestDriver;
import hardware.PinCodeTerminalTestDriver;


public class BicycleGarage {
	
	public static void main(String[] args) {
		new BicycleGarage();
	}
	
	public BicycleGarage() {
		BicycleGarageManager manager = new BicycleGarageManager();
		
		//Does not require manager
		ElectronicLockTestDriver eltd = new ElectronicLockTestDriver(new String("EntryDoor"));
		ElectronicLockTestDriver eltd2 = new ElectronicLockTestDriver(new String("ExitDoor"));
		PinCodeTerminalTestDriver pcttd = new PinCodeTerminalTestDriver();
		
		//Requires manager
		BarcodePrinterTestDriver bptd = new BarcodePrinterTestDriver();
		BarcodeReaderEntryTestDriver bretd = new BarcodeReaderEntryTestDriver();
		BarcodeReaderExitTestDriver brextd = new BarcodeReaderExitTestDriver();

		manager.registerHardwareDrivers(bptd, eltd, eltd2, pcttd);
		
		//Registering the classes with the manager
		brextd.register(manager);
		bretd.register(manager);
		pcttd.register(manager);
		
		//Starts the graphical user interface
		new GUI(manager);

		manager.start();
	}

}



