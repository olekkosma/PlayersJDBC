package com.players.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.players.domain.Contract;
import com.players.domain.NationalTeam;
import com.players.domain.Player;

@Component
@Transactional
public class PlayerManagerJDBC implements PlayerManager{

	@Autowired
	private SessionFactory sessionFactory;
	  
	public SessionFactory getSessionFactory() {
	   return sessionFactory;
	}
	  
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	  public void addPlayer(Player player) {
	    sessionFactory.getCurrentSession().persist(player);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers() {
		 return sessionFactory.getCurrentSession().getNamedQuery("player.all").list();
	}

	@Override
	public void updatePlayer(Player player) {
		sessionFactory.getCurrentSession().saveOrUpdate(player);
	}

	@Override
	public void deletePlayer(Player player) {
		sessionFactory.getCurrentSession().delete(player);
	}

	@Override
	public Player findById(long id) {
		return (Player) sessionFactory.getCurrentSession().get(Player.class, id);
	}

	@Override
	public Player findByName(String name) {
	return (Player) sessionFactory.getCurrentSession().getNamedQuery("player.byName")
		 .setString("name", name).uniqueResult();
	}

	@Override
	public void addContract(Contract contract) {
		  sessionFactory.getCurrentSession().persist(contract);
	}

	@Override
	public List<Contract> getAllContracts() {
		 return sessionFactory.getCurrentSession().getNamedQuery("contract.all").list();
		 }

	@Override
	public void updateContract(Contract contract) {
		sessionFactory.getCurrentSession().saveOrUpdate(contract);
		
	}

	@Override
	public void deleteContract(Contract contract) {
		sessionFactory.getCurrentSession().delete(contract);
		
	}

	@Override
	public Contract findContractById(Long id) {
		return (Contract) sessionFactory.getCurrentSession().get(Contract.class, id);
	}

	@Override
	public void addNationalTeam(NationalTeam nationalTeam) {
		 sessionFactory.getCurrentSession().persist(nationalTeam);
		
	}

	@Override
	public List<NationalTeam> getAllNationalTeams() {
		 return sessionFactory.getCurrentSession().getNamedQuery("nationalTeam.all").list();
	}

	@Override
	public void updateNationalTeam(NationalTeam nationalTeam) {
		sessionFactory.getCurrentSession().saveOrUpdate(nationalTeam);
		
	}

	@Override
	public void deleteNationalTeam(NationalTeam nationalTeam) {
		sessionFactory.getCurrentSession().delete(nationalTeam);
		
		
	}

	@Override
	public NationalTeam findNationalTeamById(Long id) {
		return (NationalTeam) sessionFactory.getCurrentSession().get(NationalTeam.class, id);
	}

}
