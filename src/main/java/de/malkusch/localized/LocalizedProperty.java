package de.malkusch.localized;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity for storing {@literal @Localized} fields of an arbitrary entity.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"type", "instance", "locale", "field"}))
public class LocalizedProperty implements Serializable {
	
	private static final long serialVersionUID = 1080823819223199393L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Class<?> type;
	
	private String instance;
	
	private Locale locale;
	
	private String field;
	
	@Lob
	private Serializable value;

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	@Override
	public String toString() {
		return String.format("locale=%s, id=%s, %s.%s='%s'", locale, instance, type.getSimpleName(), field, value);
	}

}
