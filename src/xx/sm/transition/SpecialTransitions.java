package xx.sm.transition;

import xx.sm.NameDescriptor;

public enum SpecialTransitions implements NameDescriptor{
	COMPLETION;

	private String name;
	
	private SpecialTransitions() {
		this.name = "__" + toString();
	}

	@Override
	public String getName() {
		return name;
	}

}
