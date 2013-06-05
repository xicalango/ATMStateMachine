package xx.collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface MapCollection<K,T> extends Iterable<Map.Entry<K, List<T>>> {

	T append(K key, T value);
	
	Collection<? extends T> appendAll(K key, Collection<? extends T> values);
	
	List<T> get(K key);
	
	void preserve(K key);
	
	void clearList(K key);
	
	void clear();
	
	Collection<List<T>> getValues();
}
