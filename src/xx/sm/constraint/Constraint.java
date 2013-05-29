package xx.sm.constraint;

import xx.sm.Environment;

public interface Constraint {
	boolean guard(Environment env);
}
