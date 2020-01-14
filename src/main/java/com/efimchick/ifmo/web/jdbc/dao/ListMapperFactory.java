package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListMapperFactory {
    private static final RowMapperFactory rmf = new RowMapperFactory();

    public ListMapper<List<Employee>> employeeListMapper() {
        return resultSet -> {
            List<Employee> employees = new ArrayList<>();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    employees.add(rmf.employeeRowMapper().mapRow(resultSet));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return employees;
        };
    }

    public ListMapper<List<Department>> departmentListMapper() {
        return resultSet -> {
            List<Department> departments = new ArrayList<>();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    departments.add(rmf.departmentRowMapper().mapRow(resultSet));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return departments;
        };
    }
}