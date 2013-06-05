package xx.atm.sm;

import xx.atm.res.ATMStateNames;
import xx.sm.Environment;
import xx.sm.state.SimpleState;

public class CoutingState extends SimpleState {

	private ATM atm;
	
	public CoutingState(ATM atm) {
		super(ATMStateNames.Couting);
		
		this.atm = atm;
	}
	
	@Override
	public void doAction(Environment env) {
		atm.counting();
		
		/* alt:
		ATM atm = env.get("ATM");
		atm.counting();
		*/
	}

}
