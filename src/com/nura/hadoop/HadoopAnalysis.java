/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.hadoop;

import com.faceset.database.AddService;
import com.nura.dao.impl.JSONEntityDAOImpl;

import com.nura.entity.JSONEntity;
import constants.ServerIP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import javax.swing.JOptionPane;

/**
 *
 * @author ArunRamya
 */
public class HadoopAnalysis {
private static String result="";
private static boolean status=false;
private static float NetBal;
private static String ProductName="";
private static  String ProductOne="";
 private static String ProductTwo="";
private static  float NetAmount=0;
private static  String FinalProduct="";
public static String Drugs="";
public static String Disease="";
static String s1="",s2="";
static  String content="";
static String originalcontent="";
static ArrayList <String>origin_sq=new ArrayList<String>();
static ArrayList <String>user_sq=new ArrayList<String>();
static int i=0;
 static String User_id="";
  private static float FinalNetAmount=0;
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter)
                throws IOException {
            try {
                     File f=new File(constants.Constants.FILE_HADOOP_IN_LOCATION);
            File f1=new File(constants.Constants.FILE_HADOOP_USER_IN_LOCATION);
            BufferedReader bf=new BufferedReader(new FileReader(f1));
            s1="";
            content="";
            while((s1=bf.readLine())!=null){
                int i=s1.length();              
                content+=s1;
                
            }
             BufferedReader bf1=new BufferedReader(new FileReader(f));
             s2="";
             originalcontent="";
            while((s2=bf1.readLine())!=null){
                int i=s2.length();
                originalcontent+=s2;  
            }          
            originalcontent=originalcontent.replace(" ", "");
            originalcontent=originalcontent.replace("t", "u");
            originalcontent=originalcontent.substring(438,2172);
            //content=content.trim();
            content=content.replace(" ", "");
            content=content.replace("t", "u");
            content=content.substring(438, 2172);
            System.out.println("Original Content Received--->"+originalcontent);
            System.out.println("User Content Received--->"+content);
            origin_sq=new ArrayList();
            user_sq=new ArrayList();
             for(i=0;i<content.length()/3;i++){           
              
                 origin_sq.add(originalcontent.substring(i,i+3));
                 user_sq.add(content.substring(i,i+3));
               
                output.collect(new LongWritable(Long.parseLong("1")), new Text(content.substring(i,i+3)));
                i+=2;
            }
               

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text> {

        public void reduce(LongWritable key, Iterator<Text> values, OutputCollector<LongWritable, Text> output,
                Reporter reporter) throws IOException {          
            int k=0;
           String likes="";
           AddService _ad=new AddService();
           ArrayList <String>change_sequence=new ArrayList<String>();
           ArrayList <String>user_reference=new ArrayList<String>();
            String gene_changed_index="";
            String data_set_gene="";
           boolean sequenceChanged=false;
           String user_shortterm="";
            java.util.Set<String> prdSel = new java.util.HashSet<>();
            int count = 0;
            int userSize=0;
            while(values.hasNext()){
               System.out.println(values.next());
                 userSize=user_sq.size();
                int contentSize=origin_sq.size();
          
            }
            for(int jk=0;jk<userSize;jk++){
               if(user_sq.get(jk).equals(origin_sq.get(jk))){                 
                   
                 System.out.println("Matched----> "+user_sq.get(jk)+" ----"+origin_sq.get(jk));
             } else{
                   gene_changed_index=user_sq.get(jk);
                   data_set_gene=origin_sq.get(jk);
                   //JOptionPane.showMessageDialog(null, "Sequence Changed Detected");                   
                    System.out.println("Sequence Changed Detected");
                   sequenceChanged=true;
               }
            }
            user_reference= _ad.getAllGeneSequence(user_sq);
                  System.out.println(user_reference.size());
                  for(String shortterm:user_reference){
                     user_shortterm+=shortterm; 
                  }
            if(sequenceChanged){
               String user_result= _ad.getGeneSequence(gene_changed_index);
               String data_set_result= _ad.getGeneSequence(data_set_gene);
               System.out.println("User Gene Result--> "+user_result+"    "+"Data set Result--> "+data_set_result);
               if(user_result.equals(data_set_result)){
                   System.out.println("Gene Sequence Changed Here-->"+gene_changed_index);
                   System.out.println("Polymorphism Founded");
                   System.out.println("Gene Result-->"+user_shortterm);
                   JOptionPane.showMessageDialog(null, "Polymorphism Founded");
                   JOptionPane.showMessageDialog(null, "Gene Result-->"+user_shortterm);
                    }else{
                   String drugresults=_ad.Get_drugs(gene_changed_index, user_result);
                  
                   System.out.println("GluCoMo Desease Found On your Gene");
                   System.out.println("Gene Result-->"+user_shortterm);
                    //JOptionPane.showMessageDialog(null, "LocoMo Desease Found On your Gene");
                   JOptionPane.showMessageDialog(null, "Gene Result-->"+user_shortterm);
                    if(!drugresults.equals("INVALID")){
                            StringTokenizer st=new StringTokenizer(drugresults,"$");
                             Disease=st.nextToken();
                             Drugs=st.nextToken();
                              String Food=_ad.Get_Food(Disease);
                            JOptionPane.showMessageDialog(null, "Disease Found-->"+Disease);
                            JOptionPane.showMessageDialog(null, "Drug is-->"+Drugs);
                            JOptionPane.showMessageDialog(null, "Take Food is-->"+Food);
                        }else{
                        JOptionPane.showMessageDialog(null, "Please Add Disease and Drugs in Database");
                    }
               }
               _ad.Add_results(User_id, user_shortterm ,Disease,Drugs);
                System.out.println("Sequence Changed Detected");
            }
            output.collect(key, new Text(likes));
        }
    }

    public void processFiles(File inputFile) throws Exception {

        HadoopAnalysis tweetAnalyze = new HadoopAnalysis();

        JobConf conf = new JobConf(HadoopAnalysis.class);

        conf.set("fs.defaultFS", "hdfs://127.0.0.1:9000");
        conf.set("mapred.job.tracker", "127.0.0.1:9001");
        conf.setJobName("hadooptrans");

        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(Map.class);
        //conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        //Code for accessing HDFS file system
        FileSystem hdfs = FileSystem.get(conf);
        Path homeDir = hdfs.getHomeDirectory();
        //Print the home directory
        System.out.println("Home folder -" + homeDir);

        //Add below code For creating and deleting directory
        Path workingDir = hdfs.getWorkingDirectory();
        Path newFolderPath = new Path("/hinput");
        newFolderPath = Path.mergePaths(workingDir, newFolderPath);
        if (hdfs.exists(newFolderPath)) {
            hdfs.delete(newFolderPath, true); //Delete existing Directory
        }
        hdfs.mkdirs(newFolderPath);     //Create new Directory

        //Code for copying File from local file system to HDFS
        String filePath = inputFile.getAbsolutePath();
        System.out.println("FilePATH"+filePath);
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        Path localFilePath = new Path(inputFile.getAbsolutePath());
        Path hdfsFilePath = new Path(newFolderPath + "/" + inputFile.getName());
        hdfs.copyFromLocalFile(localFilePath, hdfsFilePath);

        hdfs.copyFromLocalFile(localFilePath, newFolderPath);

        FileInputFormat.addInputPath(conf, hdfsFilePath);
        FileSystem fs = FileSystem.get(conf);
        Path out = new Path("hdfs://127.0.0.1:9000/hout");
        fs.delete(out, true);
        FileOutputFormat.setOutputPath(conf, new Path("hdfs://127.0.0.1:9000/hout"));
        JobClient.runJob(conf);
        //Finally copying the out file to local after job has run
        fs.copyToLocalFile(new Path("hdfs://127.0.0.1:9000/hout/part-00000"),
                new Path(constants.Constants.FILE_HADOOP_OUT_LOCATION));

        System.out.println("End of the program");
    }

    public static void main(String[] args) throws Exception {
       
      User_id=JOptionPane.showInputDialog("Enter User-ID");
    new HadoopAnalysis().processFiles(new File(constants.Constants.FILE_HADOOP_IN_LOCATION));
   
       
      
               //System.out.println(cost+warranty+offer+model+keywords+startingcost+endingcost);
        //String userName = new UserDtlsDAOImpl().getUsername(Long.parseLong(id));
        //java.util.List<Tweets> getTweets = new TweetsDAOImpl().getSpecificTweetsBasedOnUser(userName);
       /* java.io.File hInput = new java.io.File(constants.Constants.FILE_HADOOP_IN_LOCATION);
        java.io.FileWriter fileWriter = new java.io.FileWriter(hInput);
        StringTokenizer stt=new StringTokenizer(result,"#");
        while(stt.hasMoreTokens()){
            String stdata=stt.nextToken();
            StringTokenizer sttt=new StringTokenizer(stdata,"$");
            String pid=sttt.nextToken();
            String pname=sttt.nextToken();
            System.out.println("PID"+pid);
            System.out.println("PNAME"+pname);
            fileWriter.write(pid + "|" + pname + "|" +  "\n");
            fileWriter.flush();
        }
        //for (Tweets tweet : getTweets) {
           // fileWriter.write(id + "|" + userName + "|" + tweet.getTweetMsg() + "\n");
           // fileWriter.flush();
       // }
        fileWriter.close();
        new HadoopAnalysis().processFiles(hInput);
        java.io.FileInputStream fis = new java.io.FileInputStream(constants.Constants.FILE_HADOOP_OUT_LOCATION);
        java.util.Scanner scan = new java.util.Scanner(fis);
        while (scan.hasNextLine()) {
            JSONEntity _jsonEntity = new JSONEntity();
           //_jsonEntity.setUserName(userName);
            String data = scan.nextLine().split("\t")[1];
            _jsonEntity.setUserLikes(data);
            new JSONEntityDAOImpl().saveDetailss(_jsonEntity);
        }*/
    }
}
