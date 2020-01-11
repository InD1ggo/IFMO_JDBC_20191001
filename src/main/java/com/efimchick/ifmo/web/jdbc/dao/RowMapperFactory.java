package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {
        return resultSet -> {
            try {
                return new Employee(
                        resultSet.getBigDecimal("ID").toBigInteger(),
                        new FullName(
                                resultSet.getString("FIRSTNAME"),
                                resultSet.getString("LASTNAME"),
                                resultSet.getString("MIDDLENAME")
                        ),
                        Position.valueOf(resultSet.getString("POSITION")),
                        LocalDate.parse(resultSet.getString("HIREDATE")),
                        resultSet.getBigDecimal("SALARY"),
                        (resultSet.getBigDecimal("MANAGER") != null) ?
                                resultSet.getBigDecimal("MANAGER").toBigInteger() :
                                BigInteger.ZERO,
                        (resultSet.getBigDecimal("DEPARTMENT") != null) ?
                                resultSet.getBigDecimal("DEPARTMENT").toBigInteger() :
                                BigInteger.ZERO
                );
            } catch (SQLException ignored) {
                return null;
            }
        };
    }

    public RowMapper<Department> departmentRowMapper() {
        return resultSet -> {
            try {
                return new Department(resultSet.getBigDecimal("ID").toBigInteger(),
                        resultSet.getString("NAME"),
                        resultSet.getString("LOCATION"));
            } catch (SQLException ignored) {
                return null;
            }
        };
    }
}
