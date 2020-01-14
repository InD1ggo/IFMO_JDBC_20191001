package com.efimchick.ifmo.web.jdbc.service;

import com.efimchick.ifmo.web.jdbc.dao.DaoFactory;
import com.efimchick.ifmo.web.jdbc.dao.EmployeeDao;
import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.util.Collections;
import java.util.List;

public class ServiceFactory {
    private static final DaoFactory daoFactory = new DaoFactory();
    private static final EmployeeDao daoEmployee = daoFactory.employeeDAO();

    public EmployeeService employeeService() {
        return new EmployeeService() {
            @Override
            public List<Employee> getAllSortByHireDate(Paging paging) {
                List<Employee> employees = daoEmployee.getAll("ORDER BY HIREDATE");
                return getPage(employees, paging);
            }

            @Override
            public List<Employee> getAllSortByLastname(Paging paging) {
                List<Employee> employeeAll = daoEmployee.getAll("ORDER BY LASTNAME");
                return getPage(employeeAll, paging);
            }

            @Override
            public List<Employee> getAllSortBySalary(Paging paging) {
                List<Employee> employeeAll = daoEmployee.getAll("ORDER BY SALARY");
                return getPage(employeeAll, paging);
            }

            @Override
            public List<Employee> getAllSortByDepartmentNameAndLastname(Paging paging) {
                List<Employee> employeeAll = daoEmployee.getAll("ORDER BY DEPARTMENT, LASTNAME");
                return getPage(employeeAll, paging);
            }

            @Override
            public List<Employee> getByDepartmentSortByHireDate(Department department, Paging paging) {
                List<Employee> byDepartment = daoEmployee.getByDepartment(department, "ORDER BY HIREDATE");
                return getPage(byDepartment, paging);
            }

            @Override
            public List<Employee> getByDepartmentSortBySalary(Department department, Paging paging) {
                List<Employee> employeeByDepartment = daoEmployee.getByDepartment(department, "ORDER BY SALARY");
                return getPage(employeeByDepartment, paging);
            }

            @Override
            public List<Employee> getByDepartmentSortByLastname(Department department, Paging paging) {
                List<Employee> orderByLastname = daoEmployee.getByDepartment(department, "ORDER BY LASTNAME");
                return getPage(orderByLastname, paging);
            }

            @Override
            public List<Employee> getByManagerSortByLastname(Employee manager, Paging paging) {
                List<Employee> orderByLastname = daoEmployee.getByManager(manager, "ORDER BY LASTNAME");
                return getPage(orderByLastname, paging);
            }

            @Override
            public List<Employee> getByManagerSortByHireDate(Employee manager, Paging paging) {
                List<Employee> orderByHiredate = daoEmployee.getByManager(manager, "ORDER BY HIREDATE");
                return getPage(orderByHiredate, paging);
            }

            @Override
            public List<Employee> getByManagerSortBySalary(Employee manager, Paging paging) {
                List<Employee> orderBySalary = daoEmployee.getByManager(manager, "ORDER BY SALARY");
                return getPage(orderBySalary, paging);
            }

            @Override
            public Employee getWithDepartmentAndFullManagerChain(Employee employee) {
                System.out.println("FROM withDepAndFullManChain: ");
                return daoEmployee.getById(employee.getId(), true).orElse(null);
            }

            @Override
            public Employee getTopNthBySalaryByDepartment(int salaryRank, Department department) {
                System.out.println("SALARY RANK: " + salaryRank + " | FOR DEPARTMENT: " + department.getId());
                Paging paging = new Paging(1, 100);
                List<Employee> topNth = getByDepartmentSortBySalary(department, paging);
                Collections.reverse(topNth);
                return topNth.get(salaryRank - 1);
            }

            private List<Employee> getPage(List<Employee> employees, Paging paging) {
                System.out.println(String.format("Page %d | ItemPerPage %d", paging.page, paging.itemPerPage));
                int fromIndex = (paging.page - 1) * paging.itemPerPage;
                int toIndex = Math.min(paging.page * paging.itemPerPage, employees.size());
                System.out.println(
                        String.format("FromIndex %d | ToIndex %d | Size %d", fromIndex, toIndex, employees.size())
                );
                System.out.println("===================================");
                return employees.subList(fromIndex, toIndex);
            }

            private void printPage(List<Employee> page) {
                for (Employee e :
                        page) {
                    System.out.println("\t" + e);
                }
            }
        };
    }
}
