package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractPropertyEntity<T,K extends AbstractGranterEntity<?>> extends AbstractEntity<Long>{
	
	private T tableInfo;
	private K granterInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="table_id",nullable = false)
	public T getTableInfo() {
		return tableInfo;
	}
	public void setTableInfo(T tableInfo) {
		this.tableInfo = tableInfo;
	}
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name="granter_id",nullable = false)
	public K getGranterInfo() {
		return granterInfo;
	}
	public void setGranterInfo(K granterInfo) {
		this.granterInfo = granterInfo;
	}
}
