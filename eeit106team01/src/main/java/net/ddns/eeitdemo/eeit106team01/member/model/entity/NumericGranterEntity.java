package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "numeric_granter")
public class NumericGranterEntity extends AbstractGranterEntity<NumericGranterEntity> implements Serializable{
	private static final long serialVersionUID = 3663691379848066986L;
	
}
