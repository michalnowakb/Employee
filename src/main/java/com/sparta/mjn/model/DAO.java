package com.sparta.mjn.model;

import org.apache.log4j.Logger;


import java.sql.*;
import java.util.Map;

public class DAO {

    private final String QUERY = "INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String URL = "jdbc:mysql://localhost/sakila?user=root&password=eXoSparta2019";
    private int index = 0;
    private int threadCounter = 0;

    private static final String LOG_PROPERTIES_FILE = "resources/log4j.properties";
    private static Logger log = Logger.getLogger(DAO.class.getName());

    public void runThreadQuery(Map<String,Employee> employeeMap){
        Employee[] listOfEmployees = employeeMap.values().toArray(new Employee[employeeMap.size()]);
        Runnable runnable = () -> runSQLQuery(listOfEmployees);
        Thread[] threads = new Thread[150];
        while (threadCounter < threads.length){
            threads[threadCounter] = new Thread(runnable, "Thread " + threadCounter);
            threads[threadCounter].start();

            threadCounter++;
        }
        for(Thread t : threads)
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void runSQLQuery(Employee[] employeeArray){
        int i = 0;


        try(Connection connection = DriverManager.getConnection(URL)){
            synchronized (this){
                i = index;
                index++;
            }
            PreparedStatement statement = connection.prepareStatement(QUERY);
            int size = employeeArray.length;
                while (i < size){
                    statement.setString(1,employeeArray[i].getEmpID());
                    statement.setString(2,employeeArray[i].getNamePrefix());
                    statement.setString(3,employeeArray[i].getFirstName());
                    statement.setString(4,employeeArray[i].getMiddleInitial());
                    statement.setString(5,employeeArray[i].getLastName());
                    statement.setString(6,employeeArray[i].getGender());
                    statement.setString(7,employeeArray[i].geteMail());
                    statement.setString(8, String.valueOf(employeeArray[i].getDateOfBirth()));
                    statement.setString(9, String.valueOf(employeeArray[i].getDateOfJoining()));
                    statement.setInt(10,employeeArray[i].getSalary());

                    statement.executeUpdate();
                    synchronized (this) {
                       i = index;
                       index++;
                    }
                }
        } catch (SQLException e){
            log.warn(e);
        }
    }

}
