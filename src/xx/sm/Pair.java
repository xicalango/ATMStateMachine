package xx.sm;

public class Pair<T1, T2> {

	public T1 first;
	public T2 second;
	
	public Pair(T1 first, T2 second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public Pair() {
		this(null,null);
	}
	
	public static <T1,T2> Pair<T1,T2> pair(T1 first, T2 second) {
		return new Pair<>(first, second);
	}
	
	@Override
	public String toString() {
		return "{ " + first + ", " + second + " }";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
	
}
