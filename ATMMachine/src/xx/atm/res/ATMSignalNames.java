package xx.atm.res;

import xx.sm.Identifier;

public enum ATMSignalNames implements Identifier {
	cardEntered,
	PINEntered,
	reenterPIN,
	PINVerified,
	abort;

	private String name;
	
	private ATMSignalNames(String name) {
		this.name = name;
	}

	private ATMSignalNames() {
		this.name = toString();
	}

	@Override
	public String getName() {
		return name;
	}

}
