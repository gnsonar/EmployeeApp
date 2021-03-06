package com.employee.repository.impl;

import java.util.List;

import org.hibernate.Query;
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


	@Override
	public List<Employee> getAllObject() {
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		List<Employee> list = session.createCriteria(Employee.class).list();
		
		tx.commit();
		session.close();
		return list;
	}


	@Override
	public List<Employee> getAllObject(String user) {
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Employee where userCreated = :user");
		query.setString("user", user);
		
		
		List<Employee> list = query.list();
		
		tx.commit();
		session.close();
		return list;
	}


	@Override
	public void deleteObject(Employee emp) {
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		session.delete(emp);
		
		tx.commit();
		session.close();
	}
}
