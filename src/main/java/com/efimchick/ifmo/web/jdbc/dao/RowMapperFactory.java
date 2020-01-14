package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class RowMapperFactory {

    public EmployeeRowMapper employeeRowMapper() {
        return new EmployeeRowMapper() {
            @Override
            public Employee mapRow(ResultSet resultSet) {
                return mapRow(resultSet, false);
            }

            @Override
            public Employee mapRow(ResultSet resultSet, boolean fullManagerChain) {
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
                            getManager(resultSet.getBigDecimal("MANAGER"), fullManagerChain),
                            getDepartment(resultSet.getBigDecimal("DEPARTMENT"))
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    private Employee getManager(BigDecimal manager, boolean chain) throws SQLException {
        if (manager != null) {
            try (Connection connection = ConnectionSource.instance().createConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM EMPLOYEE WHERE ID = " + manager.toBigInteger());
                while (resultSet.next()) {
                    if (resultSet.getBigDecimal("ID").equals(manager)) {
                        Employee employee = new Employee(
                                resultSet.getBigDecimal("ID").toBigInteger(),
                                new FullName(
                                        resultSet.getString("FIRSTNAME"),
                                        resultSet.getString("LASTNAME"),
                                        resultSet.getString("MIDDLENAME")
                                ),
                                Position.valueOf(resultSet.getString("POSITION")),
                                LocalDate.parse(resultSet.getString("HIREDATE")),
                                resultSet.getBigDecimal("SALARY"),
                                chain ? getManager(resultSet.getBigDecimal("MANAGER"), chain) : null,
                                getDepartment(resultSet.getBigDecimal("DEPARTMENT"))
                        );
                        return employee;
                    }
                }
            }
        }
        return null;
    }

    private Department getDepartment(BigDecimal department) {
        if (department != null) {
            return (new DaoFactory()).departmentDAO().getById(department.toBigInteger()).get();
        }
        return null;
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
