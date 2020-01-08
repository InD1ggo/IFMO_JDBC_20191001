package com.efimchick.ifmo.web.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return resultSet -> {
            Set<Employee> employees = new HashSet<>();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    employees.add(
                            new Employee(
                                    resultSet.getBigDecimal("ID").toBigInteger(),
                                    new FullName(
                                            resultSet.getString("FIRSTNAME"),
                                            resultSet.getString("LASTNAME"),
                                            resultSet.getString("MIDDLENAME")
                                    ),
                                    Position.valueOf(resultSet.getString("POSITION")),
                                    LocalDate.parse(resultSet.getString("HIREDATE")),
                                    resultSet.getBigDecimal("SALARY"),
                                    getWithManager(resultSet, resultSet.getBigDecimal("MANAGER"))
                            )
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return employees;
        };
    }

    public Employee getWithManager(ResultSet rs, BigDecimal managerId) throws SQLException {
        Employee thisManager = null;
        if (managerId != null) {
            int rowId = rs.getRow();
            BigInteger manager = managerId.toBigInteger();
            rs.beforeFirst();

            while (rs.next()) {
                if (rs.getBigDecimal("ID").toBigInteger().equals(manager)) {
                    thisManager = new Employee(
                            rs.getBigDecimal("ID").toBigInteger(),
                            new FullName(
                                    rs.getString("FIRSTNAME"),
                                    rs.getString("LASTNAME"),
                                    rs.getString("MIDDLENAME")
                            ),
                            Position.valueOf(rs.getString("POSITION")),
                            LocalDate.parse(rs.getString("HIREDATE")),
                            rs.getBigDecimal("SALARY"),
                            getWithManager(rs, rs.getBigDecimal("MANAGER"))
                    );
                }
            }

            rs.absolute(rowId);
        }
        return thisManager;
    }
}
