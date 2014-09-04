package edu.oregonState.scheduler.data;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.oregonState.scheduler.core.Catalog;
import io.dropwizard.hibernate.AbstractDAO;

public class CatalogDAO extends AbstractDAO<Catalog>{
	
	public CatalogDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public List<Catalog> findByName(String name) {
		return list(namedQuery("edu.oregonState.scheduler.core.catalog.findbyName"));
	}
}
