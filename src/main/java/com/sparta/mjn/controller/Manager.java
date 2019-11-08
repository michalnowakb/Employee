package com.sparta.mjn.controller;

import com.sparta.mjn.model.DAO;
import com.sparta.mjn.model.EmployeeFileReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Manager {

    private static final String LOG_PROPERTIES_FILE = "resources/log4j.properties";
    private static Logger log = Logger.getLogger(Manager.class.getName());

    public void transferData(){
        initialiseLogging();
        long start = System.nanoTime();
        File file = new File("resources/EmployeeRecords.csv");
        EmployeeFileReader csvFileReader = new EmployeeFileReader();
        csvFileReader.readEmployeeRecord(file.toString());
        DAO dao = new DAO();
        dao.runThreadQuery(csvFileReader.readEmployeeRecord(file.toString()));
        long end = System.nanoTime();
        System.out.println("Upload time: " + ((end-start)/1_000_000_000) + "seconds");

    }

    private void initialiseLogging() {
        PropertyConfigurator.configure(LOG_PROPERTIES_FILE);
        log.trace("Logging initialised");
    }
}
