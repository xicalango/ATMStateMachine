package xx.sm.constraint;

import xx.sm.Environment;

public class ConstraintAnd implements Constraint {

	private Constraint c1;
	private Constraint c2;
	
	public ConstraintAnd(Constraint c1, Constraint c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public boolean guard(Environment env) {
		return c1.guard(env) && c2.guard(env);
	}

}
