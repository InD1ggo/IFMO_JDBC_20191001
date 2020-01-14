package com.efimchick.ifmo.web.jdbc.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

public interface EmployeeDao extends Dao<Employee, BigInteger> {
    List<Employee> getByDepartment(Department department);
    List<Employee> getByDepartment(Department department, String sorter);
    List<Employee> getByManager(Employee employee);
    List<Employee> getByManager(Employee employee, String sorter);
    List<Employee> getAll(String sorter);
    Optional<Employee> getById(BigInteger Id, boolean fullManagerChain);
}

