package xx.atm.sm;

import xx.atm.res.ATMStateNames;
import xx.atm.res.ATMSignalNames;
import xx.sm.transition.SimpleTransition;
import xx.sm.transition.Transition;

public class PINEnteredTransition extends SimpleTransition implements Transition {

	private Bank bank;
	
	public PINEnteredTransition(Bank bank) {
		super(ATMStateNames.PINEntry, ATMStateNames.Verification, ATMSignalNames.PINEntered);
		
		this.bank = bank;
	}
	
	@Override
	public void executeEffect() {
		bank.verifyPin();
	}

}
