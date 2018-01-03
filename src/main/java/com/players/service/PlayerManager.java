package com.players.service;

import java.util.List;
import com.players.domain.Contract;
import com.players.domain.NationalTeam;
import com.players.domain.Player;

public interface PlayerManager {
	
		public void addPlayer(Player player);
		public List<Player> getAllPlayers();
		public void updatePlayer(Player player);
		public void deletePlayer(Player player);
		public Player findById(long id);
		public Player findByName(String name);
		
	 	void addContract(Contract contract);
	 	List<Contract> getAllContracts();
	 	void updateContract(Contract contract);
	 	void deleteContract(Contract contract);
	 	Contract findContractById(Long id);
	 	
	 	void addNationalTeam(NationalTeam nationalTeam);
	 	List<NationalTeam> getAllNationalTeams();
	 	void updateNationalTeam(NationalTeam nationalTeam);
	 	void deleteNationalTeam(NationalTeam nationalTeam);
	 	NationalTeam findNationalTeamById(Long id);
	
}
