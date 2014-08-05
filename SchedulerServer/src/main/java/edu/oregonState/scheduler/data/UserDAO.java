package edu.oregonState.scheduler.data;

import com.google.common.base.Optional;

import edu.oregonState.scheduler.user.User;
import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {
	private SessionFactory userSessionFactory;
    public UserDAO(SessionFactory factory) {    	
        super(factory);
        userSessionFactory = factory;
    }

    public Optional<User> findById(Long id) {
    	boolean managed = false;
    	if(!this.currentSession().isOpen()){
    		userSessionFactory.openSession();
    		managed = true;
    	}
        Optional<User> user = Optional.fromNullable(get(id));
        if (managed){
        	userSessionFactory.getCurrentSession().close();
        }        
        return user;
    }

    public User create(User person) {
        return persist(person);
    }

    public List<User> findAll() {
        return list(namedQuery("edu.oregonState.scheduler.user.findAll"));
    }
    
    public User findByID(String id) {
        return list(namedQuery("edu.oregonState.scheduler.user.findById").setParameter("userID",id,StringType.INSTANCE)).get(0);
    }    
}