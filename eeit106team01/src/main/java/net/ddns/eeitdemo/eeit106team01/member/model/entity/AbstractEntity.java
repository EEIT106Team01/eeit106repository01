package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity<T extends Number> {
	private T id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public T getId() {
		return id;
	}
	public void setId(T id) {
		this.id = id;
	}
	
}
