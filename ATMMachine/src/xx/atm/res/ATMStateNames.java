package xx.atm.res;

import xx.sm.Identifier;

public enum ATMStateNames implements Identifier {
	CardEntry,
	PINEntry,
	Verification,
	ReturningCard,
	AmountEntry,
	Couting,
	Dispensing,
	GivingMoney("Giving Money");

	private String name;
	
	private ATMStateNames(String name) {
		this.name = name;
	}

	private ATMStateNames() {
		this.name = toString();
	}

	@Override
	public String getName() {
		return name;
	}
	
	
}
