package servicelayer.entity.businessEntity;


import datalayer.daos.DAOEmployees;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployees;


public class Project {

	private int id;
	private String name;
	private String notes;
	private IDAOEmployees IDAOEmployees;
	
	public Project() throws ServerException
	{
		IDAOEmployees = new DAOEmployees();
	}
	
	public Project(int id, String name, String notes)
	{
		this.id = id;
		this.name = name;
		this.notes = notes;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/*
	public void NewEmployed(Employed employed)
	{
		IDAOEmployees.Insert(employed);
	}
	
	public ArrayList<Employed> GetEmployees()
	{
		return IDAOEmployees.GetAll();
	}
	*/
}
