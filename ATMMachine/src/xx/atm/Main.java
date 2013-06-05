package xx.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import xx.atm.res.ATMSignalNames;
import xx.atm.sm.ATM;
import xx.atm.sm.ATMSMFactory;
import xx.atm.sm.Bank;
import xx.sm.StateMachine;
import xx.sm.StateMachineRunner;

public class Main {

	private static ATMSignalNames printAndChoseSignal() throws IOException {
		
		int i = 0;
		
		System.out.println("Next signal? (Press C-z/C-d/EOF to quit)");
		
		System.out.println("0: only do a step");
		
		ATMSignalNames[] signals = ATMSignalNames.values();
		
		for(ATMSignalNames sigName : signals) {
			i++;
			System.out.println(i + ": " + sigName.getName());
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String line = in.readLine();
		
		if(line == null) {
			throw new IOException();
		}
		
		line = line.trim();
		
		try {
			int intVal = Integer.valueOf(line);
			
			if( intVal == 0 || intVal >= signals.length ) {
				return null;
			}
			
			return signals[intVal-1];
		} catch(NumberFormatException e) {
			return null;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		ATM atm = new ATM();
		Bank bank = new Bank();
		
		ATMSMFactory atmsmFactory = new ATMSMFactory(atm, bank);
		
		final StateMachine sm = StateMachine.compile(atmsmFactory);
		
		StateMachineRunner smr = new StateMachineRunner(sm);
		smr.setTrace(true);
		
		boolean running = true;
		
		while(running) {
			try {
				ATMSignalNames signal = printAndChoseSignal();
				
				if(signal != null) {
					smr.offerSignal(signal);
				}
				
				smr.step();
				
			} catch (IOException e) {
				System.out.println("Quit due to user request");
				running = false;
			}
		}
		
		

	}
	
}
