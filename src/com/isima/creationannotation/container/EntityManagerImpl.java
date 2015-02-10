package com.isima.creationannotation.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManagerImpl implements EntityManager {
	// singleton
	private static EntityManagerImpl INSTANCE = null;
	
	// données
	private HashMap<Integer,List<Object>> mRows = new HashMap<Integer,List<Object>>();
	
	/**
	 * Constructeur privée
	 */
	private EntityManagerImpl(){
		
	}
	
	public static EntityManagerImpl getInstance(){
		if(INSTANCE == null){
			INSTANCE = new EntityManagerImpl();
		}
		
		return INSTANCE;
	}
	
	@Override
	public void persist(Object entity) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void persist(int filiere, Object entity) {
		if(!mRows.containsKey(filiere)){
			mRows.put(filiere, new ArrayList<Object>());
		}
		
		mRows.get(filiere).add(entity);
	}
	
	@Override
	public List<Object> get(int filiere) {
		return mRows.get(filiere);
	}
}
