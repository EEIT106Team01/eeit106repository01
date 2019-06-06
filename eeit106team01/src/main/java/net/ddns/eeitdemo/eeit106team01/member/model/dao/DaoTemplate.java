package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.Collection;

public interface DaoTemplate<T,K extends Number> {
	public T getById(K id);
	public T findAnUnique(String bean);
	public Collection<T> findByBean(T bean);
	public boolean deleteById(K id);
	public boolean deleteByIdList(Collection<K> ids);
	public T updateNotNull(T bean);
	public T saveIgnoreId(T bean);
	public boolean insertMany(Collection<T> beans);	
}
