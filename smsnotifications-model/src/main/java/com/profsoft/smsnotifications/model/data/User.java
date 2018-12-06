package com.profsoft.smsnotifications.model.data;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author filips
 */
@Entity
@Table(name = "ADM_USER")
public class User extends AbstractEntity<Long> {

	public static final String SYSTEM_LOGIN = "SYSTEM";

	@Id
	@SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Basic(optional = false)
	@Column(name = "US_LOGIN", nullable = false, length = 30)
	private String login;

	@Basic(optional = false)
	@Column(name = "US_PASSWORD", nullable = false, length = 100)
	private String haslo;

	@Basic(optional = false)
	@Column(name = "US_NAME", nullable = false, length = 50)
	private String imie;

	@Basic(optional = false)
	@Column(name = "US_LASTNAME", nullable = false, length = 50)
	private String nazwisko;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.id);
		hash = 47 * hash + Objects.hashCode(this.login);
		hash = 47 * hash + Objects.hashCode(this.haslo);
		hash = 47 * hash + Objects.hashCode(this.imie);
		hash = 47 * hash + Objects.hashCode(this.nazwisko);

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (!Objects.equals(this.login, other.login)) {
			return false;
		}
		if (!Objects.equals(this.haslo, other.haslo)) {
			return false;
		}
		if (!Objects.equals(this.imie, other.imie)) {
			return false;
		}
		if (!Objects.equals(this.nazwisko, other.nazwisko)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}

		return true;
	}

}
