package dax;

import java.util.List;
import java.util.Map;

import search.SearchField;
import search.SearchMode;


public interface DataAx<T> {
	List<T> fetchAll();
	List<T> search(Map<SearchField,Object> criteria,SearchMode mode);
	List<T> search(String term);
	boolean delete(T t);
	int delete(Map<SearchField,Object> criteria,SearchMode mode);
	boolean add(T t);
	boolean update(T t);
	int update(T t,SearchField[] attributeNames);
	boolean match(T t, Map<SearchField,Object> criteria,SearchMode mode);
	boolean match(T t1,T t2, SearchField[] attributeNames,SearchMode mode);
	boolean change(T target, T model, SearchField[] attributeNames);
}
