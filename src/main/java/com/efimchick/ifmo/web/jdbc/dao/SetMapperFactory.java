package com.efimchick.ifmo.web.jdbc.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return resultSet -> {
            RowMapperFactory rowMapperFactory = new RowMapperFactory();
            Set<Employee> employees = new HashSet<>();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    employees.add(
                            rowMapperFactory.employeeRowMapper().mapRow(resultSet)
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return employees;
        };
    }

    public SetMapper<Set<Department>> departmentSetMapper() {
        return resultSet -> {
            RowMapperFactory rowMapperFactory = new RowMapperFactory();
            Set<Department> departments = new HashSet<>();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    departments.add(
                            rowMapperFactory.departmentRowMapper().mapRow(resultSet)
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return departments;
        };
    }
}