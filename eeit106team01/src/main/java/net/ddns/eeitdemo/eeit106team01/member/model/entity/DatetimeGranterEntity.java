package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "datetime_granter")
public class DatetimeGranterEntity extends AbstractGranterEntity<DatetimeGranterEntity> implements Serializable {
	private static final long serialVersionUID = 5541000876707386691L;
}
