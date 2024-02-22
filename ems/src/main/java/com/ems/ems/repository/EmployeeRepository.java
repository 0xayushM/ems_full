package com.ems.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.ems.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByEmail(String email);
	Employee findByFirstName(String firstName);
	Employee findByLastName(String lastName);
	Employee findByFirstNameAndLastName(String firstName, String lastName);
}
