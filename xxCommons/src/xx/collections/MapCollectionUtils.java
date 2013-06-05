package xx.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MapCollectionUtils {

	private MapCollectionUtils() {}
	
	public static <K,T> Map<K,Integer> countOccurrences( MapCollection<K, T> collection) {
		Map<K,Integer> result = new HashMap<>();
		
		for( Map.Entry<K, List<T>> entry : collection ) {
			result.put(entry.getKey(), entry.getValue().size());
		}
		
		return result;
	}
	
}
