package com.employee.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepositoryDB;

@Repository
@Qualifier("hbTemplate")
public class EmployeeRepositryHbImpl implements EmployeeRepositoryDB<Employee>{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public long saveObject(Employee t) {
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		long id = (Long)session.save(t);
		
		tx.commit();
		session.close();
		
		return id;
	}


	@Override
	public Employee getObject(long id) {
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		Employee emp = (Employee)session.get(Employee.class, id);
		
		tx.commit();
		session.close();
		
		return emp;
	}


	@Override
	public void updateObject(Employee emp) {
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(emp);
		
		tx.commit();
		session.close();
		
	}

}
