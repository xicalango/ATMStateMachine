package xx.sm.state;

import xx.sm.Identifier;

public enum SpecialStates implements Identifier{
	FINAL;

	private String name;
	
	private SpecialStates() {
		this.name = "__" + toString();
	}

	@Override
	public String getName() {
		return name;
	}
}
