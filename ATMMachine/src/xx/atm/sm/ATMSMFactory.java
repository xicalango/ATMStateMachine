package xx.atm.sm;

import java.util.Arrays;
import java.util.List;

import xx.atm.res.ATMSignalNames;
import xx.atm.res.ATMStateNames;
import xx.sm.StateMachineFactory;
import xx.sm.state.FinalState;
import xx.sm.state.SimpleRegionState;
import xx.sm.state.SimpleState;
import xx.sm.state.State;
import xx.sm.transition.CompletionTransition;
import xx.sm.transition.SimpleTransition;
import xx.sm.transition.Transition;

public class ATMSMFactory implements StateMachineFactory {

	private ATM atm;
	private Bank bank;
	
	public ATMSMFactory(ATM atm, Bank bank) {
		this.atm = atm;
		this.bank = bank;
	}

	@Override
	public List<? extends State> getStates() {
		
		return Arrays.asList(
				new SimpleState(ATMStateNames.CardEntry) ,
				new SimpleState(ATMStateNames.PINEntry) ,
				new SimpleState(ATMStateNames.Verification) ,
				new SimpleState(ATMStateNames.ReturningCard) ,
				new SimpleState(ATMStateNames.AmountEntry),
				new SimpleRegionState(ATMStateNames.GivingMoney, new GivingMoneyFactory(atm)),
				new FinalState("")
				);
	}

	@Override
	public List<? extends Transition> getTransitions() {
		return  Arrays.asList(
				new SimpleTransition(ATMStateNames.CardEntry, ATMStateNames.PINEntry, ATMSignalNames.cardEntered),
				new SimpleTransition(ATMStateNames.Verification, ATMStateNames.PINEntry, ATMSignalNames.reenterPIN),
				new SimpleTransition(ATMStateNames.Verification, ATMStateNames.AmountEntry, ATMSignalNames.PINVerified),
				new SimpleTransition(ATMStateNames.Verification, ATMStateNames.ReturningCard, ATMSignalNames.abort),
				new CompletionTransition(ATMStateNames.ReturningCard.getName(), FinalState.getFinalStateName("")),
				new CompletionTransition(ATMStateNames.ReturningCard, ATMStateNames.CardEntry),
				new CompletionTransition(ATMStateNames.AmountEntry, ATMStateNames.GivingMoney),
				new CompletionTransition(ATMStateNames.GivingMoney, ATMStateNames.ReturningCard),
				new PINEnteredTransition(bank)
				);
	}

	@Override
	public String getStartState() {
		return ATMStateNames.CardEntry.getName();
	}

}
