package xx.sm.state;

import xx.sm.NameDescriptor;

public enum SpecialStates implements NameDescriptor{
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
