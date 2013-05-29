package xx.sm;

public interface Environment {
	<T> T get(String key);
	<T> void put(String key, T value);
}
