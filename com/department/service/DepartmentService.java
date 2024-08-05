package com.department.service;

import java.util.Map;
import java.util.List;

import com.customexception.EmployeeException;
import com.model.Department;
import com.model.Employee;

/**
* <p>
* This interface has Abstract methods to implements the 
* methods like Department Operations 
* </p>
* @author Aravind
*/
public interface DepartmentService {
 
    /** 
     * <p>
     * This method Return the Employee Departments to the Controller
     * </p>
     * @return Departments as the Map objects
     * 
     * @throws EmployeeException while get Departments
     */
    public Map<Integer, Department> getDepartments() throws EmployeeException;

    /** 
     * <p>
     * This method return the Employee Records to the Employee Controller
     * </p>
     * @return Employees - List of Employees
     */
    public List<Employee> getEmployeeRecords();

    /** 
     * <p>
     * This method passes department object as argument to  
     * department Warehouse or Dao 
     * </p>
     * @param departmentName - Name of the Department
     *
     * @throws EmployeeException - Error while Adding Department to the Database
     */
    public void addDepartment(String departmentName) throws EmployeeException;
   
    /**
     * <p>
     * This method to delete a department by the department
     * id given by the user
     * </p>
     * @param id department - Department Id given by the user
     *
     * @throws EmployeeException - Error while Delete Department by Department Id
     */
    public void deleteDepartment(int id) throws EmployeeException;

    /**  
     * <p>
     * This method return the Department Object 
     * with the Department Id
     * </p>
     * @param departmentId - Id of the Department to get Department Object
     *
     * @return Department - Department as the Object
     * 
     * @throws EmployeeException- Error while get Department as Object by Department Id
     */
    public Department getDepartmentObject(int departmentId) throws EmployeeException;

    /**
     * <p>
     * This method update Department name with id
     * </p>
     * @param Department - Department as the Object
     * 
     * @throws EmployeeException - Error while Update Department
     */
    public void updateDepartmentRecord(Department department) throws EmployeeException;
}