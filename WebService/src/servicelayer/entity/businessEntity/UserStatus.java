package servicelayer.entity.businessEntity;

public enum UserStatus {
	// ESTOS VALORES SE DEBE CORRESPONDER CON LOS VALORES DE LOS ID's DE LA
	// TABLE USERTYPE EN LA BASE DE DATOS
	ACTIVE(1), BLOCKED(2);

	private final int id;

	UserStatus(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static UserStatus getEnum(int value) {
		for (UserStatus v : values())
			if (v.getValue() == value)
				return v;
		throw new IllegalArgumentException();
	}
}
