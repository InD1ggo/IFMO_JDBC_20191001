package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.sql.ResultSet;

public interface EmployeeRowMapper extends RowMapper<Employee> {
    Employee mapRow(ResultSet resultSet, boolean fullManagerChain);
}
