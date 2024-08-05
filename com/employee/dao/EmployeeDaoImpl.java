package com.employee.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query; 
import org.hibernate.Session; 
import org.hibernate.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.model.Employee;
import com.model.Mentor;
import com.model.Department;
import com.customexception.EmployeeException;
import com.connectionmanager.HibernateManager;

/** 
 * <p>
 * This class handles all the Database Operations like
 * Insertion, Display the Records, Update and Delete the Records
 * </p>
 * @author Aravind
 */
public class EmployeeDaoImpl implements EmployeeDao {
    private static Logger logger = LogManager.getLogger();

    @Override
    public List<Employee> getRecords() throws EmployeeException {
        Session session = HibernateManager.getFactory().openSession();
        Transaction transaction = null;   
        List<Employee> employeeRecords = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Query<Employee> query =  session.createQuery("from Employee where isDeleted = :isDeleted", Employee.class)
                                            .setParameter("isDeleted", false);
            employeeRecords = query.list();                                   
            transaction.commit();      
        } catch (HibernateException e) {
            if(null != transaction) {
                transaction.rollback();
            }
            logger.error("Error while get Employee Records..");
            throw new EmployeeException("Error while get All employee records..", e);
        } finally {
	    if (null != session) {
                session.close();
	    }
        }  
        return employeeRecords;
    }

    @Override
    public Employee insertData(Employee employee) throws EmployeeException {
        Session session = HibernateManager.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if(null != transaction) {
                transaction.rollback();
            }
            logger.error("Error occured while Adding this Employee :" + employee.getEmployeeName());
            throw new EmployeeException("Error occured while adding employee of name : " + employee.getEmployeeName(), e);
        } finally {
            if (null != session) {
                session.close();
	    }
        }
	return employee;
    }

    @Override
    public void updateRecord(Employee employee) throws EmployeeException {
	Session session = HibernateManager.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if(null != transaction) {
                transaction.rollback();
            }
            logger.error("Error while updating with Employee :" + employee.getEmployeeName());
            throw new EmployeeException("Error while updating employee of ID : " + employee.getEmployeeId(), e);
        } finally {
            if (null != session) {
                session.close();
	    }
        }
    }
    
    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeException {
        Session session = HibernateManager.getFactory().openSession();
        Transaction transaction = null;
        Employee employee = null;
        try {
            transaction = session.beginTransaction();
            employee = session.createQuery("from Employee where isDeleted = false and employeeId = :employeeId", Employee.class)
			      .setParameter("employeeId", employeeId).uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if(null != transaction) {
                transaction.rollback();
            }
	    logger.error("Error while get Employee by this Id: " + employeeId);
            throw new EmployeeException("Error while getting employee of Id : " + employeeId, e);
        } finally {
            if (null != session) {
                session.close();
	    }
        }
        return employee;
    }
 
    @Override
    public void deleteRecord(int deleteId) throws EmployeeException {
        Session session = HibernateManager.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String deleteQuery = "update Employee set isDeleted = :isDeleted where id = :deleteId";
            Query<?> query = session.createQuery(deleteQuery);
            query.setParameter("isDeleted", true);
            query.setParameter("deleteId", deleteId);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if(null != transaction) {
                transaction.rollback();
            }
	    logger.error("Error while deleting employee with this Id :" + deleteId);
            throw new EmployeeException("Error while deleting employee of Id : " + deleteId, e);
        } finally {
            if (null != session) {
                session.close();
	    }
        }
    }
}