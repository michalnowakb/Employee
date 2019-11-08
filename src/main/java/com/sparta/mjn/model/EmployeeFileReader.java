package com.sparta.mjn.model;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EmployeeFileReader {

    private static Logger log = Logger.getLogger(EmployeeFileReader.class.getName());
    private Map<String, Employee> listOfEmployeesFromFile;
    private Map<String,Employee> listOfDuplicates;


    private static Employee createEmployee(String file) {
        String empID = null;
        String namePrefix = null;
        String firstName = null;
        String middleInitial = null;
        String lastName = null;
        String gender = null;
        String eMail = null;
        LocalDate dateOfBirth;
        LocalDate dateOfJoining;
        int salary = 0;


        String[] array = file.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");


        if (array[0].matches("^[1-9][0-9]+$")) {
            empID = (array[0]);
        } else {
            log.error("Invalid Entry " + array[0] + " for Employee ID");
        }

        if (array[1].matches("^[A-Z][a-z]+[.]$")) {
            namePrefix = array[1];
        } else {
            log.error("Invalid Entry " + array[1] + " for Name Prefix");
        }

        if (array[2].matches("^[A-Z][a-z]+$")) {
            firstName = array[2];
        } else {
            log.error("Invalid Entry " + array[2] + " for First Name");
        }

        if (array[3].matches("^[A-Z]$")) {
            middleInitial = array[3];
        } else {
            log.error("Invalid Entry " + array[3] + " for Middle Initial");
        }

        if (array[4].matches("^[A-Z][a-z]+$")) {
            lastName = array[4];
        } else {
            log.error("Invalid Entry " + array[4] + " for Last Name");
        }

        if (array[5].matches("^[FM]$")) {
            gender = array[5];
        } else {
            log.error("Invalid Entry " + array[5] + " for Gender");
        }

        if (array[6].matches("^[a-z+.]+@(.+)$")) {
            eMail = array[6];
        } else {
            log.error("Invalid Entry " + array[6] + " for e-mail");
        }

        dateOfBirth = LocalDate.parse(array[7], formatter);

        dateOfJoining = LocalDate.parse(array[8], formatter);

        if (array[9].matches("^[1-9][0-9]{1,20}$")) {
            salary = Integer.parseInt(array[9]);
        } else {
            log.error("Invalid Entry " + array[9] + " for Salary");
        }


        Employee employee = new Employee(empID, namePrefix, firstName, middleInitial, lastName, gender, eMail, dateOfBirth, dateOfJoining, salary);
        return employee;
    }

    private Employee createEmployeeInStream(String fields) {
        Employee employee = createEmployee(fields);
        String key = employee.getEmpID();
        if (listOfEmployeesFromFile.containsKey(key)) {
            log.warn("This employee ID " + key + " has been used" );
            listOfDuplicates.put(key,employee);
        } else {
            listOfEmployeesFromFile.put(key, employee);
        }

        return employee;
    }

    public Map<String, Employee> readEmployeeRecord(String file) {
        listOfEmployeesFromFile = new HashMap<>();
        listOfDuplicates = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.readLine();
            Stream<String> streamReader = bufferedReader.lines();
            streamReader
                    .filter(fileName -> !fileName.equals(null))
                    .forEach(fields -> createEmployeeInStream(fields));
        } catch (IOException e) {
            e.printStackTrace();

        }


//            String line;
//            line = bufferedReader.readLine(); // Moving on from first line, to skip headings
//            while ((line = bufferedReader.readLine()) != null) {
//                Employee employee = createEmployee(line);
//                if(listOfEmployeesFromFile.containsKey(employee.getEmpID())){
//                    log.warn("This employee ID has been used");
//                } else {
//                    listOfEmployeesFromFile.put(employee.getEmpID(),employee);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return listOfEmployeesFromFile;
    }


}
