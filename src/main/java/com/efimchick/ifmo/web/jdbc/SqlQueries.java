package com.efimchick.ifmo.web.jdbc;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    //language=HSQLDB
    String select01 = "SELECT * FROM EMPLOYEE ORDER BY LASTNAME";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    String select02 = "SELECT * FROM EMPLOYEE WHERE LENGTH(LASTNAME) <= 5 ORDER BY LASTNAME";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    String select03 = "SELECT * FROM EMPLOYEE WHERE SALARY BETWEEN 2000 AND 3000 ORDER BY LASTNAME";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    String select04 = "SELECT * FROM EMPLOYEE WHERE SALARY <= 2000 OR SALARY >= 3000";

    //Select employees assigned to a department and corresponding department name
    //language=HSQLDB
    String select05 = "SELECT EMPLOYEE.*, D.NAME FROM EMPLOYEE INNER JOIN DEPARTMENT D ON EMPLOYEE.DEPARTMENT = D.ID";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select06 = "SELECT EMPLOYEE.*, D.NAME AS depname FROM EMPLOYEE LEFT JOIN DEPARTMENT D on EMPLOYEE.DEPARTMENT = D.ID";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    String select07 = "SELECT SUM(SALARY) AS TOTAL FROM EMPLOYEE";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    String select08 =
            "SELECT NAME AS depname, COUNT(E.ID) AS staff_size " +
                    "FROM DEPARTMENT " +
                    "INNER JOIN EMPLOYEE E on DEPARTMENT.ID = E.DEPARTMENT " +
                    "GROUP BY DEPARTMENT.NAME";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select09 = "SELECT DEPARTMENT.NAME AS depname, SUM(E.SALARY) AS total, AVG(E.SALARY) AS average FROM DEPARTMENT INNER JOIN EMPLOYEE E on DEPARTMENT.ID = E.DEPARTMENT GROUP BY DEPARTMENT.ID";

    //Select all employees and their managers if there is one.
    //Name column containing employee lastname "employee".
    //Name column containing manager lastname "manager".
    //language=HSQLDB
    String select10 = "SELECT emps.LASTNAME AS employee, mans.LASTNAME AS manager  FROM EMPLOYEE emps LEFT JOIN EMPLOYEE mans ON emps.MANAGER = mans.ID";


}
