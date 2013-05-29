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
	
}
