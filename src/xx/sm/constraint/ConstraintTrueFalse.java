package xx.sm.constraint;

import xx.sm.Environment;

public enum ConstraintTrueFalse implements Constraint {
	True(true),
	False(false);

	private boolean value;
	
	private ConstraintTrueFalse(boolean value) {
		this.value = value;
	}

	@Override
	public boolean guard(Environment env) {
		return value;
	}

}
