package com.app.crudapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.crudapp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	

}
