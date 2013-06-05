package xx.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashArrayMapList<K,T>implements MapCollection<K, T> {

	private Map<K, List<T>> internalMap = new HashMap<>();
	
	@Override
	public T append(K key, T value) {
		preserve(key);
		
		internalMap.get(key).add(value);

		return value;
	}

	@Override
	public List<T> get(K key) {
		if(internalMap.containsKey(key)) {
			return internalMap.get(key);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void clearList(K key) {
		internalMap.put(key, new ArrayList<T>());
	}

	@Override
	public void clear() {
		internalMap.clear();
	}

	@Override
	public Collection<? extends T> appendAll(K key, Collection<? extends T> values) {
		preserve(key);
		
		internalMap.get(key).addAll(values);
		
		return values;
	}

	@Override
	public void preserve(K key) {
		if(!internalMap.containsKey(key)) {
			clearList(key);
		}
	}

	@Override
	public Iterator<Map.Entry<K, List<T>>> iterator() {
		return internalMap.entrySet().iterator();
	}

	@Override
	public Collection<List<T>> getValues() {
		List<List<T>> values = new ArrayList<>();
	
		for(Map.Entry<K, List<T>> e : this) {
			values.add(e.getValue());
		}
		
		return values;
	}


	

	

}
