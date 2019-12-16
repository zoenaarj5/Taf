package dax;

import java.util.List;
import java.util.Map;


public interface DataAx<T> {
	List<T> fetchAll();
	List<T> search(Map<String,Object> criteria,SearchMode mode);
	List<T> search(String term);
	boolean delete(T t);
	int delete(Map<String,Object> criteria,SearchMode mode);
	boolean add(T t);
	boolean update(T t);
	int update(T t,String[] attributeNames);
	boolean match(T t, Map<String,Object> criteria,SearchMode mode);
	boolean match(T t1,T t2, String[] attributeNames,SearchMode mode);
	boolean change(T target, T model, String[] attributeNames);
}
