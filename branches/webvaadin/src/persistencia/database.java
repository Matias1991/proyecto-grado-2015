package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

public class database {
	
	private final String userName = "root";
	private final String password = "root";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "testdb";
	
		
	public JDBCConnectionPool getConnection() throws SQLException{
		/*Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);*/
		JDBCConnectionPool pool = null;
		
		//conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);
		try{
			 pool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/testdb", "root", "root", 2, 5);
		
		//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","root");
		//System.out.println("ACA ==> " + pool);
		
		}catch (Exception e){
			e.printStackTrace();
		}
		return pool;		
	}
	
	
	/*public boolean executeUpdate(String command) throws SQLException {
	    Statement stmt = null;
	    Connection conn = null;
	    try {
	    	conn = getConnection();
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); 
	        return true;
	    } finally {	    	
	        if (stmt != null) {
	        	stmt.close();	        	
				conn.close();
	        }
	    }
	}*/
	
	
	public Iterator executeQuery(String query) throws SQLException {
		//Statement stmt = null;
		//ResultSet rs = null;		
		JDBCConnectionPool conn = null;
		Iterator<?> result = null;
				
		try {
			conn = getConnection();
			
			//FreeformQuery FreeQuery = new FreeformQuery(query, conn, "ID");
			FreeformQuery FreeQuery = new FreeformQuery(query, conn);
			SQLContainer container = new SQLContainer(FreeQuery);
			
			result = container.getItemIds().iterator();
			
			
			
			//stmt = conn.createStatement();
			//rs = stmt.executeQuery(query);
			
			
			
			
			/*while(rs.next()){
				result.add(rs.getString("id").trim());
				result.add(rs.getString("usuario").trim());
				result.add(rs.getString("clave").trim());
			}*/
											
		
		}catch (SQLException e){
			e.printStackTrace();
		}
		return result;
	}
}
