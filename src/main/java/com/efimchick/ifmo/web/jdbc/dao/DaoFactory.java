package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoFactory {
    static final ConnectionSource cns = ConnectionSource.instance();

    public EmployeeDao employeeDAO() {
        return new EmployeeDao() {
            @Override
            public List<Employee> getByDepartment(Department department) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(
                            "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = " + department.getId());
                    SetMapperFactory smf = new SetMapperFactory();
                    return new ArrayList<>(smf.employeesSetMapper().mapSet(resultSet));
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public List<Employee> getByManager(Employee employee) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement(
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    ResultSet resultSet = statement.executeQuery(
                            "SELECT * FROM EMPLOYEE WHERE MANAGER = " + employee.getId());
                    SetMapperFactory smf = new SetMapperFactory();
                    return new ArrayList<>(smf.employeesSetMapper().mapSet(resultSet));
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public Optional<Employee> getById(BigInteger Id) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement(
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE WHERE ID = " + Id);
                    RowMapperFactory rmf = new RowMapperFactory();
                    resultSet.next();
                    return Optional.ofNullable(rmf.employeeRowMapper().mapRow(resultSet));
                } catch (SQLException e) {
                    return Optional.empty();
                }
            }

            @Override
            public List<Employee> getAll() {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement(
                             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE");
                    SetMapperFactory smf = new SetMapperFactory();
                    return new ArrayList<>(smf.employeesSetMapper().mapSet(resultSet));
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public Employee save(Employee employee) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    int update = statement.executeUpdate(
                            "" + "INSERT INTO EMPLOYEE VALUES ("
                                    + employee.getId() + ", " +
                                    "'" + employee.getFullName().getFirstName() + "', " +
                                    "'" + employee.getFullName().getLastName() + "', " +
                                    "'" + employee.getFullName().getMiddleName() + "', " +
                                    "'" + employee.getPosition() + "'," +
                                    " " + employee.getManagerId() +
                                    ", TO_DATE('" +
                                    employee
                                            .getHired()
                                            .format(DateTimeFormatter
                                                    .ofPattern("DD-MM-YYYY")) +
                                    "', 'DD-MM-YYYY'), " +
                                    employee.getSalary() + ", " +
                                    employee.getDepartmentId() + ")");
                    return employee;
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public void delete(Employee employee) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    int update = statement.executeUpdate("DELETE FROM EMPLOYEE WHERE ID = " + employee.getId());
                } catch (SQLException ignored) {
                }
            }
        };
    }

    public DepartmentDao departmentDAO() {
        return new DepartmentDao() {
            @Override
            public Optional<Department> getById(BigInteger Id) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM DEPARTMENT WHERE ID = " + Id);
                    resultSet.next();
                    RowMapperFactory rmf = new RowMapperFactory();
                    Department department = rmf.departmentRowMapper().mapRow(resultSet);
                    return Optional.ofNullable(department);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }

            @Override
            public List<Department> getAll() {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM DEPARTMENT");
                    SetMapperFactory smf = new SetMapperFactory();
                    return new ArrayList<>(smf.departmentSetMapper().mapSet(resultSet));
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public Department save(Department department) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    if (getById(department.getId()).isPresent()) {
                        delete(department);
                        save(department);
                    } else {
                        int update = statement.executeUpdate("INSERT INTO DEPARTMENT VALUES " +
                                department.getId().toString() + ", '" +
                                department.getName() + "', '" +
                                department.getLocation() + "'");
                    }
                    return department;
                } catch (SQLException ignored) {
                    return null;
                }
            }

            @Override
            public void delete(Department department) {
                try (Connection connection = cns.createConnection();
                     Statement statement = connection.createStatement()) {
                    int update = statement.executeUpdate("DELETE FROM DEPARTMENT WHERE ID = " + department.getId());
                } catch (SQLException ignored) {
                }
            }
        };
    }
}
