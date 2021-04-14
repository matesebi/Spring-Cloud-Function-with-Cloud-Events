package com.example.scf;

import java.util.concurrent.atomic.AtomicLong;

public class Employee {

    private static final AtomicLong employeeNumberGenerator = new AtomicLong();

    private final long employeeNumber;
    private String firstName;
    private String lastName;

    public Employee(Person person) {
        this.employeeNumber = employeeNumberGenerator.getAndIncrement();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }

    public long getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
