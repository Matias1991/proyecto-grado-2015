package servicelayer.entity.valueObject;

import java.io.Serializable;

public class VODistributionType implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String value;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
