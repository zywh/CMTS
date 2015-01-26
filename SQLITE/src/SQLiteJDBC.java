import java.sql.*;

public class SQLiteJDBC
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
     //Connect to company.db
      
      c = DriverManager.getConnection("jdbc:sqlite:company1.db");
      System.out.println("Opened database successfully");
      c.setAutoCommit(false);
      stmt = c.createStatement();
      String checktable = " SELECT * FROM sqlite_master WHERE name ='COMPANY' and type='table'"; 
      ResultSet rs = stmt.executeQuery(checktable);
    		  


      if (!rs.next()) {
		String sql = "CREATE TABLE COMPANY "
				+ "(ID INT PRIMARY KEY     NOT NULL,"
				+ " NAME           TEXT    NOT NULL, "
				+ " AGE            INT     NOT NULL, "
				+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
		stmt.executeUpdate(sql);
		System.out.println("Table created successfully");
	}
	
      
      //Insert Record
     
      String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
      stmt.executeUpdate(sql);

      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  
  }
}