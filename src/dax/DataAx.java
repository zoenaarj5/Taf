package dax;

import java.util.List;
import java.util.Map;


public interface DataAx<T> {
	List<T> fetchAll();
	List<T> search(Map<String,Object> criteria);
	boolean delete(T t);
	int delete(Map<String,Object> criteria);
	boolean add(T t);
	boolean update(T t);
	int update(T t,String[] attributeNames);
	boolean match(T t, Map<String,Object> criteria);
	boolean match(T t1,T t2, String[] attributeNames);
	boolean change(T target, T model, String[] attributeNames);
}
