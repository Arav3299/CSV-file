package com.prodian.task.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prodian.task.inaddition.CSVHelper;
import com.prodian.task.modal.EmployeeDetails;
import com.prodian.task.repository.EmployeeDetailsRepository;

@Service
public class EmployeeDetailsService {
	
	 @Autowired
	 private EmployeeDetailsRepository employeeDetailsRepository;

	  public void save(MultipartFile file) {
	    try {
	      List<EmployeeDetails> employeeList = CSVHelper.csvToTutorials(file.getInputStream());
	      employeeDetailsRepository.saveAll(employeeList);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }

	  public ByteArrayInputStream load() {
	    
		  List<EmployeeDetails> employeeList = employeeDetailsRepository.findAll();

		  ByteArrayInputStream in = CSVHelper.tutorialsToCSV(employeeList);
	    return in;
	  }

	  public List<EmployeeDetails> getAllTutorials() {
	    return employeeDetailsRepository.findAll();
	  }

}
