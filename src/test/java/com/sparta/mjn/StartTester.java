package com.sparta.mjn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.sparta.mjn.controller.Manager;
import com.sparta.mjn.model.Employee;
import com.sparta.mjn.model.EmployeeFileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class StartTester
{
    File file = new File("resources/EmployeeRecords.csv");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    Employee testEmployee;
    EmployeeFileReader testEmployeeFileReader;
    Map<String,Employee> employees;
    Employee employeeKey;

    @Before
    public void creatingTestEmployee() {
        testEmployee = new Employee(
                "198429",
                "Mrs.",
                "Serafina",
                "I",
                "Bumgarner",
                "F", "serafina.bumgarner@exxonmobil.com",
                LocalDate.parse("9/21/1982", formatter),
                LocalDate.parse("2/1/2008", formatter),
                69294);

        testEmployeeFileReader = new EmployeeFileReader();

    }

    @Before
    public void creatingEmployeeFileReaderObject(){
        testEmployeeFileReader = new EmployeeFileReader();
        employees = testEmployeeFileReader.readEmployeeRecord(file.toString());
        employeeKey= employees.get("198429");
    }

    @Test
    public void checkIfEmployeeIdIsEqual()
    {
        assertEquals(testEmployee.getEmpID(),employeeKey.getEmpID());
    }

    @Test
    public void checkIfEmployeeNameIsEqual(){
        assertEquals(testEmployee.getFirstName(),employeeKey.getFirstName());
    }

    @Test
    public void checkIfEmployeeInitialIsEqual(){
        assertEquals(testEmployee.getMiddleInitial(),employeeKey.getMiddleInitial());
    }

    @Test
    public void checkIfLastNameIsEqual(){
        assertEquals(testEmployee.getLastName(),employeeKey.getLastName());
    }

    @Test
    public void checkIfGenderIsEqual(){
        assertEquals(testEmployee.getGender(),employeeKey.getGender());
    }

    @Test
    public void checkIfDOBIsEqual(){
        assertEquals(testEmployee.getDateOfBirth(),employeeKey.getDateOfBirth());
    }

    @Test
    public void checkIfJoiningDateIsEqual(){
        assertEquals(testEmployee.getDateOfJoining(),employeeKey.getDateOfJoining());
    }

    @Test
    public void checkIfSalaryIsEqual(){
        assertEquals(testEmployee.getSalary(),employeeKey.getSalary());
    }

}
