package entities;

import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOProject {

	private int id;
	private String name;
	private boolean isCurrencyDollar;

	public VOProject(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public VOProject(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		this.id = Integer.parseInt(object.getProperty(4).toString());
		this.name = object.getProperty(9).toString();
		this.isCurrencyDollar = Boolean.parseBoolean(object.getProperty(5)
				.toString());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}
}
