package xx.atm.sm;

import java.util.Arrays;
import java.util.List;

import xx.atm.res.ATMStateNames;
import xx.sm.StateMachineFactory;
import xx.sm.state.FinalState;
import xx.sm.state.SimpleState;
import xx.sm.state.State;
import xx.sm.transition.CompletionTransition;
import xx.sm.transition.Transition;

public class GivingMoneyFactory implements StateMachineFactory {

	private ATM atm;
	
	public GivingMoneyFactory(ATM atm) {
		this.atm = atm;
	}
	
	@Override
	public List<? extends State> getStates() {
		return Arrays.asList(
				new SimpleState(ATMStateNames.Dispensing),
				new CoutingState(atm),
				new FinalState(ATMStateNames.GivingMoney)
				);	
	}

	@Override
	public List<? extends Transition> getTransitions() {
		return Arrays.asList(
				new CompletionTransition(ATMStateNames.Couting, ATMStateNames.Dispensing),
				new CompletionTransition(ATMStateNames.Dispensing.getName(), FinalState.getFinalStateName(ATMStateNames.GivingMoney))
				);
	}

	@Override
	public String getStartState() {
		return ATMStateNames.Couting.getName();
	}

}
