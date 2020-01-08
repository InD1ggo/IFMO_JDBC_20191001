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
        return new SetMapper<Set<Employee>>() {
            @Override
            public Set<Employee> mapSet(ResultSet resultSet) {
                Set<Employee> employees = new HashSet<>();
                while (true) {
                    try {
                        if (!resultSet.next()) break;
                        employees.add(
                                getManager(resultSet, resultSet.getBigDecimal("MANAGER"))
                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                return employees;
            }
        };
    }

    public Employee getManager(ResultSet rs, BigDecimal managerId) throws SQLException {
        if (!managerId.equals(BigDecimal.ZERO)) {
            int rowId = rs.getRow();
            BigInteger manager = managerId.toBigInteger();
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getBigDecimal("ID").toBigInteger().equals(manager)) {
                    return new Employee(
                            rs.getBigDecimal("ID").toBigInteger(),
                            new FullName(
                                    rs.getString("FIRSTNAME"),
                                    rs.getString("LASTNAME"),
                                    rs.getString("MIDDLENAME")
                            ),
                            Position.valueOf(rs.getString("POSITION")),
                            LocalDate.parse(rs.getString("HIREDATE")),
                            rs.getBigDecimal("SALARY"),
                            getManager(rs, rs.getBigDecimal("MANAGER"))
                    );
                }
            }
            rs.absolute(rowId);
        }
        return null;
    }
}
