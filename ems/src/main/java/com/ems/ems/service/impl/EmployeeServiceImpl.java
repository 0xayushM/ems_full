package com.ems.ems.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.ems.dto.EmployeeDto;
import com.ems.ems.entity.Employee;
import com.ems.ems.exception.DuplicateEntryException;
import com.ems.ems.exception.ResourceNotFoundException;
import com.ems.ems.mapper.EmployeeMapper;
import com.ems.ems.repository.EmployeeRepository;
import com.ems.ems.service.EmployeeService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		
		Employee existingEmployeeByEmail = employeeRepository.findByEmail(employeeDto.getEmail());
	    if (existingEmployeeByEmail != null) {
	    	throw new DuplicateEntryException(existingEmployeeByEmail.getEmail() + " already exists");
		 }
	    Employee existingEmployeeByFirstName = employeeRepository.findByFirstName(employeeDto.getFirstName());
		    if (existingEmployeeByFirstName != null) {
		        throw new DuplicateEntryException(existingEmployeeByFirstName.getFirstName() + " already exists");
		    }
		Employee existingEmployeeByLastName = employeeRepository.findByLastName(employeeDto.getLastName());
		if (existingEmployeeByLastName != null) {
	        throw new DuplicateEntryException(existingEmployeeByLastName.getLastName() + " already exists");
	    }
		
		
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("No such employee exist of the given ID."));
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("No such employee exist of the given ID."));
		Employee existingEmployeeByEmail = employeeRepository.findByEmail(updatedEmployee.getEmail());
	    if (existingEmployeeByEmail != null && !existingEmployeeByEmail.getId().equals(employeeId)) {
	    	throw new DuplicateEntryException(existingEmployeeByEmail.getEmail() + " already exists");
		 }
	    Employee existingEmployeeByFirstName = employeeRepository.findByFirstName(updatedEmployee.getFirstName());
		    if (existingEmployeeByFirstName != null && !existingEmployeeByFirstName.getId().equals(employeeId)) {
		        throw new DuplicateEntryException(existingEmployeeByFirstName.getFirstName() + " already exists");
		    }
		Employee existingEmployeeByLastName = employeeRepository.findByLastName(updatedEmployee.getLastName());
		if (existingEmployeeByLastName != null && !existingEmployeeByLastName.getId().equals(employeeId)) {
	        throw new DuplicateEntryException(existingEmployeeByLastName.getLastName() + " already exists");
	    }
		
		
		employee.setFirstName(updatedEmployee.getFirstName());
		employee.setLastName(updatedEmployee.getLastName());
		employee.setEmail(updatedEmployee.getEmail());
		
		Employee updatedEmployeeObj = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("No such employee exist of the given ID."));
		employeeRepository.deleteById(employeeId);
	}

}
