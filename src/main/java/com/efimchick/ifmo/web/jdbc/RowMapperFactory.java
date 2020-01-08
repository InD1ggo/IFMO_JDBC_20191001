package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

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
                        resultSet.getBigDecimal("SALARY")
                );
            } catch (SQLException ignored) {
                return null;
            }
        };
    }
}
