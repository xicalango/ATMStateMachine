package xx.sm.transition;

import xx.sm.Identifier;

public enum SpecialSignals implements Identifier{
	COMPLETION;

	private String name;
	
	private SpecialSignals() {
		this.name = "__" + toString();
	}

	@Override
	public String getName() {
		return name;
	}

}
