import java.sql.*;
import java.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;
public class jdbc {

	static Connection myConn=null;
	static Statement stmt= null;
	static ResultSet rs=null;
	static String theUser;
	static String thePassword;
	static String theDburl;
	
	public static void main(String[] args) throws SQLException {
		fetch();
	
		insertData();
		
		update();
		
	deleteRecord();
	
		
}
	public static void  fetch() throws SQLException{
		try{
			//1. Load the properties file
			loadUserPass();
			// Get connection
			
			myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
			// create statement
			stmt= myConn.createStatement();
			// Execute query
			rs= stmt.executeQuery("select * from student");
			System.out.println("Here's the Class Roster");
			System.out.printf("First Name           Last Name              GPA SAT" );
			System.out.println();
				while(rs.next()){
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					float GPA = rs.getFloat("GPA");
					int SAT = rs.getInt("SAT");
					
					
					String msg=String.format("%-20s %-20s %5.1f %d ", firstName, lastName, GPA, SAT);
					System.out.println(msg);					
			
				}
			
		}catch(Exception ex){
			if(myConn!= null)
				myConn.close();
			if(stmt != null)
				stmt.close();

	}
		
	}
	public static void insertData() throws SQLException{
		try{
			//1. Load the properties file
			loadUserPass();
			// Get connection
			//1. Making conn
			myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
			//2. Create statement
			stmt=myConn.createStatement();
			//3. Execute sql
		String query="insert into student values(999,'George', 'Washington',4.0,1600,null )";
		int rawAffected = stmt.executeUpdate(query);
			
			System.out.println("New student added to roster");
			rs= stmt.executeQuery("select * from student");
			System.out.printf("First Name           Last Name              GPA SAT" );
			System.out.println();
				while(rs.next()){
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					float GPA = rs.getFloat("GPA");
					int SAT = rs.getInt("SAT");
					
					
					String msg=String.format("%-20s %-20s %5.1f %d ", firstName, lastName, GPA, SAT);
					System.out.println(msg);					
			
				}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			if(myConn!= null)
				myConn.close();
			if(stmt != null)
				stmt.close();
		}
	}

	public static void update() throws SQLException{
		try{
			//1. Load the properties file
			Properties props = new Properties();
			
			props.load(new FileInputStream("jdb.properties"));
			//props.load(new FileInputStream
			
			//2. Read the props
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");
			String theDburl = props.getProperty("dburl");
			// Get connection
			//1. Making conn
			myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
			//2. Create statement
			stmt=myConn.createStatement();
		
		String sql= "update student join major on major.name='General Business' set GPA= 3.5, SAT= 1450, ";
		String sql2 = "student.major_id=major.id where first_name= 'George' and last_name= 'Washington'";
		int rawAffected= stmt.executeUpdate(sql + sql2);
		
		
		System.out.println("New student record changes!");
		rs= stmt.executeQuery("select * from student");
		System.out.printf("First Name           Last Name              GPA SAT" );
		System.out.println();
			while(rs.next()){
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				float GPA = rs.getFloat("GPA");
				int SAT = rs.getInt("SAT");
				
				
				String msg=String.format("%-20s %-20s %5.1f %d ", firstName, lastName, GPA, SAT);
				System.out.println(msg);					
		
			}
		
	}catch(Exception ex){ ex.printStackTrace();
	}finally{
		if(myConn!= null)
			myConn.close();
		if(stmt != null)
			stmt.close();
	}
	}
public static void deleteRecord() throws SQLException{
		
		try{
			loadUserPass();
			// 1. Get connection
			
			myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
			//2. Create statement
			stmt=myConn.createStatement();
		String query="delete from student where id=999";
		int rawAffected= stmt.executeUpdate(query);
		
		System.out.println("New student out of here!");
		rs= stmt.executeQuery("select * from student");
		System.out.printf("First Name           Last Name              GPA SAT" );
		System.out.println();
			while(rs.next()){
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				float GPA = rs.getFloat("GPA");
				int SAT = rs.getInt("SAT");
				
				
				String msg=String.format("%-20s %-20s %5.1f %d ", firstName, lastName, GPA, SAT);
				System.out.println(msg);					
		
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			if(myConn!= null)
				myConn.close();
			if(stmt != null)
				stmt.close();
		}
	}

public static void loadUserPass() throws FileNotFoundException, IOException{
	//1. Load the properties file
	Properties props = new Properties();
	
	props.load(new FileInputStream("jdb.properties"));
	//props.load(new FileInputStream
	
	//2. Read the props
	theUser = props.getProperty("user");
    thePassword = props.getProperty("password");
	theDburl = props.getProperty("dburl");
	
}

}
