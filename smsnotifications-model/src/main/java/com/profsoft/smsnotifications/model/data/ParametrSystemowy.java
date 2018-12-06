package com.profsoft.smsnotifications.model.data;

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
@Table(name = "PARAMETRY")
public class ParametrSystemowy extends AbstractEntity<Long> {

	@Id
	@SequenceGenerator(name = "SEQ_PAR", sequenceName = "SEQ_PAR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAR")
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Basic(optional = false)
	@Column(name = "PAR_KOD", nullable = false, length = 30)
	private String kodParametru;

	@Basic(optional = false)
	@Column(name = "PAR_NAZWA", nullable = false, length = 100)
	private String nazwa;

	@Basic(optional = false)
	@Column(name = "PAR_WARTOSC", nullable = false, length = 50)
	private String wartosc;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKodParametru() {
		return kodParametru;
	}

	public void setKodParametru(String kodParametru) {
		this.kodParametru = kodParametru;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getWartosc() {
		return wartosc;
	}

	public void setWartosc(String wartosc) {
		this.wartosc = wartosc;
	}

}
