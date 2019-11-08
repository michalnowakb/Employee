package com.sparta.mjn.controller;

import com.sparta.mjn.model.DAO;
import com.sparta.mjn.model.EmployeeFileReader;

import java.io.File;

/**
 * Hello world!
 *
 */
public class Start
{
    public static void main( String[] args )
    {
        Manager manager = new Manager();
        manager.transferData();

    }
}
