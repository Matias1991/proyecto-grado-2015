package servicelayer.entity.businessEntity;

public enum EmployedType {
	// ESTOS VALORES SE DEBE CORRESPONDER CON LOS VALORES DE LOS ID's DE LA
	// TABLE USERTYPE EN LA BASE DE DATOS
	COMMON(1), PARTNER(2);

	private final int id;

	EmployedType(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static EmployedType getEnum(int value) {
		for (EmployedType v : values())
			if (v.getValue() == value)
				return v;
		throw new IllegalArgumentException();
	}
}
