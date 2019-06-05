package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Entity
@Table(name = "text_granter")
public class TextGranterEntity extends AbstractGranterEntity<TextGranterEntity> implements Serializable {
	private static final long serialVersionUID = -8863242131855557569L;
}
