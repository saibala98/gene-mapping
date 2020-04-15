package com.nura.dbsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBConnection {
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int i = 0;
        String data="";
	private boolean validatedataFlag = false;
	
	public Connection getConnection(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
	         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "admin");
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return con;
	}
	
	public int createData(String Category,String productName,String Profit){
		try{
			
			Connection con = getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into high_utility(category, productname, netprofit) values('"+Category+"','"+productName+"','"+Profit+"')");
				if(i>0){
					System.out.println("Values are successfully inserted...");
				}
			
					
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return i;
	}
        	public int DeleteData(){
		try{
			
			Connection con = getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("delete from high_utility");
				if(i>0){
					System.out.println("Deleted...");
				}
			
					
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return i;
	}
        
       public String getData(){
		try{
			
                        boolean status=false;
                        StringBuilder sb=new StringBuilder();
			Connection con = getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
			Statement stmt = con.createStatement();                      
                      
				//rs = stmt.executeQuery("select prd_ctg , count(*) from pur_dtls group by prd_id order by 2 desc limit 5");
                                 rs=stmt.executeQuery("SELECT distinct(category) FROM ecommerce.high_utility order by netprofit DESC");
				while(rs.next()){
                                    data += rs.getString(1)+"#";
                                   sb.append(data);
					System.out.println("Values are**"+data);
                                        status=true;
				}
                                if(status){
                                    data="";
                                    data=sb.toString();
                                    sb=new StringBuilder();
                                    System.out.println("Values are IF"+data);
                                }
                                else{
                                    System.out.println("Status false");
                                }
			
					
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return data;
	}
	
	public boolean validateData(String userName,String pass){
		try{
			Connection con = getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
			Statement stmt = con.createStatement();
			int x=  stmt.executeUpdate("delete from productlist");
			rs = stmt.executeQuery("select 1 from userlogin where uname='"+userName+"' and pass='"+pass+"'");
			if(rs.next()){
				validatedataFlag = true;
			}else{
				validatedataFlag = false;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return validatedataFlag;
	}

}
