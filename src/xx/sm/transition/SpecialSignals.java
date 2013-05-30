package xx.sm.transition;

import xx.sm.NameDescriptor;

public enum SpecialSignals implements NameDescriptor{
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
