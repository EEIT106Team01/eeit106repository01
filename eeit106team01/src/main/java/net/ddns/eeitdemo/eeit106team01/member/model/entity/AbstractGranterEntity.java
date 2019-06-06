package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractGranterEntity<T> extends AbstractEntity<Long> {
	private String tagName;
	@Column(name="tag_name",nullable = false,unique = true)
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
