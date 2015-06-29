package servicelayer.entity.businessEntity;

public class Employed {

	private int id;
	private String name;

	public Employed()
	{
		
	}
	
	public Employed(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int _id) {
		this.id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
