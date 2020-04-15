package com.faceset.database;


import com.faceset.dbconnection.DBConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.management.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;



public class AddService {
	DBConnection db=new DBConnection();
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
        private ResultSet rs1 = null;
        int i=0;
        String Records="";
	byte[ ] imgData = null ;
        Blob image = null;

    public String getGeneSequence(String geneseq) {
        String prdList = null;
        ArrayList <String>_al=new ArrayList<String>();
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
         
			Statement stmt = con.createStatement();
				//rs = stmt.executeQuery("select * from product where pname like '%"+Keywords+"%' or review like '%"+Keywords+"%' order by review DESC");
                        rs = stmt.executeQuery("SELECT gene_seq, amino_acid, short_term FROM amino_acid_seq where gene_seq='"+geneseq+"'");
			 if(rs.next()){
                            prdList=rs.getString(2);
                            
                         }
                       
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return prdList;
    }
        public ArrayList getAllGeneSequence(ArrayList geneseq) {
        String prdList = null;
        ArrayList <String>_al=new ArrayList<String>();
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
         
			Statement stmt = con.createStatement();
                        Iterator i=geneseq.iterator();
                        while(i.hasNext()){
				//rs = stmt.executeQuery("select * from product where pname like '%"+Keywords+"%' or review like '%"+Keywords+"%' order by review DESC");
                        rs = stmt.executeQuery("SELECT short_term FROM amino_acid_seq where gene_seq='"+i.next()+"'");
			 if(rs.next()){
                             _al.add(rs.getString(1));
                                //System.out.println(rs.getString(1));                             
                         }
                        }
                       
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return _al;
    }
    
       public int Add_User(String Name,String UserName,String Password,String email,String ContactNo,String Location) {
        String prdList = null;
        
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into user_details (name, username, password, email, contactno,location) values('"+Name+"','"+UserName+"','"+Password+"','"+email+"','"+ContactNo+"','"+Location+"')");                      
                                if(i>0){
                                    System.out.println("Successfully Inserted");
                                }
                                else{
                                    System.out.println("Not Inserted");
                                }
			
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return i;
    }
       public int Add_results(String User_id,String transform,String Disease,String Drugs) {
        String prdList = null;
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
			Statement stmt = con.createStatement();
				int i = stmt.executeUpdate("insert into gene_results (user_id, gene_transform,disease,drugs) values('"+User_id+"','"+transform+"','"+Disease+"','"+Drugs+"')");                      
                                if(i>0){
                                    System.out.println("Successfully Inserted");
                                }
                                else{
                                    System.out.println("Not Inserted");
                                }
			
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return i;
    }
       public boolean Login_Valid(String UserName,String Password) {
        boolean prdList = false;
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select * from user_details where username='"+UserName+"' and password='"+Password+"'");                      
                                if(rs.next()){
                                    prdList=true;
                                    System.out.println("Valid");
                                }
                                else{
                                    System.out.println("Not Inserted");
                                }
			
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return prdList;
    }
   public String Get_drugs(String GeneSeq,String AminoAcid) {
        String prdList = "";
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select  disease, drugs from amino_acid_seq where gene_seq='"+GeneSeq+"' and amino_acid='"+AminoAcid+"'");                      
                                if(rs.next()){
                                    String Disease=rs.getString(1);
                                    String Drugs=rs.getString(2);
                                    if(!Disease.equals("")&&!Drugs.equals("")){
                                    prdList=Disease+"$"+Drugs;
                                    System.out.println("Valid");
                                    }else{
                                        prdList="INVALID";
                                    }
                                }
                                else{
                                    prdList="INVALID";
                                    System.out.println("Not Inserted");
                                }
			
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return prdList;
    }
   public String Get_Food(String Disease) {
        String prdList = "";
        try {
           
           Connection con = db.getConnection();
			if(con == null){
				System.out.println("DB Not Configured");
			}
                        
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select  food from food_details where disease='"+Disease+"'");                      
                                if(rs.next()){
                                    String Food=rs.getString(1);
                                    prdList=Food;
                                    
                                }
                                else{
                                    prdList="INVALID";
                                    System.out.println("Not Inserted");
                                }
			
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return prdList;
    }
	
}
