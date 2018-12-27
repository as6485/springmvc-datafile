package org.springmvc.file.dao;

import java.io.Serializable;
import java.util.List;

public interface BookDaoInterface<T, Id extends Serializable> {

	public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Id id);
	
	public List<T> findByTitle(String title);
	
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll(String filename);
	
}
