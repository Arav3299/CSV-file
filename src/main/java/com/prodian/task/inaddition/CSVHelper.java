package com.prodian.task.inaddition;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.prodian.task.modal.EmployeeDetails;

public class CSVHelper {
	 
	public static String TYPE = "text/csv";
	
	static String[] HEADERs = { "id", "name", "experience", "salary" , "status" };

	  public static boolean hasCSVFormat(MultipartFile file) {
		  
	    if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
	      return true;
	    }

	    return false;
	  }

	  public static List<EmployeeDetails> csvToTutorials(InputStream is) {
		  
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    		
	    CSVParser csvParser = new CSVParser(fileReader,
	            							CSVFormat.DEFAULT.withFirstRecordAsHeader()
	            											 .withIgnoreHeaderCase()
	            											 .withTrim());) {

	      List<EmployeeDetails> employeeList = new ArrayList<>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  
	    	 EmployeeDetails employeeDetail = new EmployeeDetails(
	              Integer.parseInt(csvRecord.get("id")),
	              csvRecord.get("name"),
	              csvRecord.get("experience"),
	              csvRecord.get("salary"),
	              csvRecord.get("status")
	            );

	    	  employeeList.add(employeeDetail);
	      }

	      return employeeList;
	      
	    } catch (IOException e) {
	    	
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	      
	    }
	  }

	  public static ByteArrayInputStream tutorialsToCSV(List<EmployeeDetails> employeeList) {
	    
		  final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	    		
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	     
	    	for (EmployeeDetails employee : employeeList) {
	       
	    		List<String> data = Arrays.asList(
	              String.valueOf(employee.getId()),
	              employee.getName(),
	              employee.getExperience(),
	              employee.getSalary(),
	              employee.getStatus()
	               
	            );

	        csvPrinter.printRecord(data);
	      }

	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	}
