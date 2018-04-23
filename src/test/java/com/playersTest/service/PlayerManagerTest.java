package com.playersTest.service;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.players.domain.Contract;
import com.players.domain.NationalTeam;
import com.players.domain.Player;
import com.players.service.PlayerManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PlayerManagerTest {

	private final static double MARKETVALUE_1 = 200000.50;
	private final static String NAME_1 = "Leo Messi";
	private final static int AGE_1 = 30;
	
	private final static double MARKETVALUE_2 = 500000.50;
	private final static String NAME_2 = "Cristiano Ronaldo";
	private final static int AGE_2 = 33;
	
	private final static double SALARY_1 = 10000.50;
	private final static String TITLE_1 = "Xmas Comercial";
	private final static String COMPANYNAME_1 = "Nike";
	
	private final static double SALARY_2 = 55500.50;
	private final static String TITLE_2 = "Charity spot";
	private final static String COMPANYNAME_2 = "FIFA";
	
	private final static double SALARY_3 = 100.0;
	private final static String TITLE_3 = "Charity spot for poor children in south africa";
	private final static String COMPANYNAME_3 = "UNESCO";
	
	private final static String NATIONALNAME_1 = "Argentina";
	
	private final static String NATIONALNAME_2 = "Portugal";

	@Autowired
	PlayerManager playerManager;
	  
	
	  @Test
	  public void shouldGetPlayers() {
	    int players = playerManager.getAllPlayers().size();
	    assertThat(players, either(is(0)).or(is(1)));
	  }
	
	  @Test
	  public void shouldAddPlayer() {
	    int counterBeforeAdd= playerManager.getAllPlayers().size();
	    Player player = new Player(AGE_1, NAME_1, MARKETVALUE_1,new NationalTeam());
	    playerManager.addPlayer(player);
	    int counterNow = playerManager.getAllPlayers().size();
	    assertEquals(counterBeforeAdd + 1, counterNow);
	    Player player2 = playerManager.getAllPlayers().get(counterNow - 1);
	    playerManager.deletePlayer(player2);
	  }
	  
	  @Test
	  public void shouldUpdatePlayer() {
	    Player player = new Player(AGE_1, NAME_1, MARKETVALUE_1, new NationalTeam());
	    playerManager.addPlayer(player);
	    int playersCount = playerManager.getAllPlayers().size();
	    Player playerGot = playerManager.getAllPlayers().get(playersCount - 1);
	    playerGot.setAge(AGE_1);
	    playerGot.setName(NAME_1);
	    playerGot.setMarketValue(MARKETVALUE_1);
	    playerGot.setNationalTeam(new NationalTeam(NATIONALNAME_1));
	    playerManager.updatePlayer(playerGot);
	    playerGot = playerManager.getAllPlayers().get(playersCount - 1);
	    
	    assertEquals(playerGot.getAge(), AGE_1);
	    assertEquals(playerGot.getName(), NAME_1);
	    assertEquals(playerGot.getMarketValue(), MARKETVALUE_1, 0.001);
	    assertEquals(playerGot.getNationalTeam().getName(), NATIONALNAME_1);
	    
	    Player playerTmp = playerManager.getAllPlayers().get(playersCount - 1);
	    playerManager.deletePlayer(playerTmp);
	  }

	  @Test
	  public void shouldDeletePlayer() {
		  Player player = new Player(AGE_2, NAME_2, MARKETVALUE_2, new NationalTeam());
		  playerManager.addPlayer(player);
		  int playersCount = playerManager.getAllPlayers().size();
		  Player playerGot = playerManager.getAllPlayers().get(playersCount - 1);
		  playerManager.deletePlayer(playerGot);
		  int playersCountNow = playerManager.getAllPlayers().size();
		  assertEquals(playersCount-1, playersCountNow);
	  }
	  
	  @Test
	  public void shouldFindPlayer() {
		Player player = new Player(AGE_2, NAME_2, MARKETVALUE_2, new NationalTeam());
		playerManager.addPlayer(player);
	    long id = player.getId();
	    Player playerGot = playerManager.findById(id);
	    assertEquals(playerGot, player);
	    playerManager.deletePlayer(playerGot);
	  }
	  
	  @Test
	  public void shouldFindPlayerByName() {
		Player player = new Player(AGE_2, NAME_2, MARKETVALUE_2, new NationalTeam());
		playerManager.addPlayer(player);
	    String name = player.getName();
	    Player playerGot = playerManager.findByName(name);
	    assertEquals(playerGot, player);
	    playerManager.deletePlayer(playerGot);
	  }
	  
	  @Test
	  public void shouldGetContracts() {
	    int contractCount = playerManager.getAllContracts().size();
	    assertThat(contractCount, either(is(0)).or(is(1)));
	  }
	  
	  @Test
	  public void shouldAddContract() {
	    int countractCountBefore = playerManager.getAllContracts().size();
	    Contract contract = new Contract(new Player(),SALARY_1, TITLE_1, COMPANYNAME_1);
	    playerManager.addContract(contract);
	    int contractCount = playerManager.getAllContracts().size();
	    assertEquals(countractCountBefore + 1, contractCount);
	    Contract contractTmp = playerManager.getAllContracts().get(contractCount - 1);
	    playerManager.deleteContract(contractTmp);
	  }

	  @Test
	  public void shouldUpdateContract() {
		  Contract contract = new Contract(new Player(),SALARY_1, TITLE_1, COMPANYNAME_1);
		  playerManager.addContract(contract);
		  int countractCountBefore = playerManager.getAllContracts().size();
		  Contract contractGot = playerManager.getAllContracts().get(countractCountBefore - 1);
		  contractGot.setSalary(SALARY_2);
		  contractGot.setTitle(TITLE_2);
		  contractGot.setCompanyName(COMPANYNAME_2);
		  contractGot.setPlayer(new Player(AGE_2, NAME_2, MARKETVALUE_2, new NationalTeam()));
		  playerManager.updateContract(contractGot);
		  contractGot = playerManager.getAllContracts().get(countractCountBefore - 1);
		  
		  assertEquals(contractGot.getSalary(), SALARY_2,0.001);
		  assertEquals(contractGot.getTitle(), TITLE_2);
		  assertEquals(contractGot.getCompanyName(), COMPANYNAME_2);
		  assertEquals(contractGot.getPlayer().getName(), NAME_2);
	    
		  Contract contractTmp = playerManager.getAllContracts().get(countractCountBefore - 1);
		  playerManager.deleteContract(contractTmp);
	  }

	  @Test
	  public void shouldDeleteContract() {
		Contract contract = new Contract(new Player(),SALARY_1, TITLE_1, COMPANYNAME_1);
		playerManager.addContract(contract);
	    int countractCountBefore = playerManager.getAllContracts().size();
	    Contract contractGot = playerManager.getAllContracts().get(countractCountBefore - 1);
	    playerManager.deleteContract(contractGot);
	    int contractCountNow = playerManager.getAllContracts().size();
	    assertEquals(countractCountBefore - 1, contractCountNow);
	  }

	  @Test
	  public void shouldFindContract() {
		Contract contract = new Contract(new Player(),SALARY_1, TITLE_1, COMPANYNAME_1);
		playerManager.addContract(contract);
	    long id = contract.getId();
	    Contract contractGot = playerManager.findContractById(id);
	    assertEquals(contractGot, contract);
	    playerManager.deleteContract(contractGot);
	  }
	  
	  @Test
	  public void shouldGetNationalTeams() {
	    int nationalTeamCount = playerManager.getAllNationalTeams().size();
	    assertThat(nationalTeamCount, either(is(0)).or(is(1)));
	  }
	  
	  @Test
	  public void shouldAddNationalTeam() {
	    int nationalTeamCountBefore = playerManager.getAllNationalTeams().size();
	    NationalTeam nationalTeam = new NationalTeam(NATIONALNAME_1);
	    playerManager.addNationalTeam(nationalTeam);
	    int nationalTeamCount = playerManager.getAllNationalTeams().size();
	    assertEquals(nationalTeamCountBefore + 1, nationalTeamCount);
	    NationalTeam nationalTeamTmp = playerManager.getAllNationalTeams().get(nationalTeamCount - 1);
	    playerManager.deleteNationalTeam(nationalTeamTmp);
	  }

	  @Test
	  public void shouldUpdateNationalTeam() {
		    NationalTeam nationalTeam = new NationalTeam(NATIONALNAME_1);
		    playerManager.addNationalTeam(nationalTeam);
		    int nationalTeamCountBefore = playerManager.getAllNationalTeams().size();
		    NationalTeam nationalTeamGot = playerManager.getAllNationalTeams().get(nationalTeamCountBefore - 1);
		    nationalTeamGot.setName(NATIONALNAME_2);
		    playerManager.updateNationalTeam(nationalTeamGot);
		    nationalTeamGot = playerManager.getAllNationalTeams().get(nationalTeamCountBefore - 1);
		    assertEquals(nationalTeamGot.getName(), NATIONALNAME_2);
		    NationalTeam nationalTeamTmp = playerManager.getAllNationalTeams().get(nationalTeamCountBefore - 1);
		    playerManager.deleteNationalTeam(nationalTeamTmp);
	  }

	  @Test
	  public void shouldDeleteNationalTeam() {
		  	NationalTeam nationalTeam = new NationalTeam(NATIONALNAME_1);
		    playerManager.addNationalTeam(nationalTeam);
		    int nationalTeamCountBefore = playerManager.getAllNationalTeams().size();
		    NationalTeam nationalTeamGot = playerManager.getAllNationalTeams().get(nationalTeamCountBefore - 1);
		    playerManager.deleteNationalTeam(nationalTeamGot);
		    int nationalTeamCountNow = playerManager.getAllNationalTeams().size();
		    assertEquals(nationalTeamCountBefore - 1, nationalTeamCountNow);
	  }

	  @Test
	  public void shouldFindNationalTeam() {
		  	NationalTeam nationalTeam = new NationalTeam(NATIONALNAME_1);
		    playerManager.addNationalTeam(nationalTeam);
		    long id = nationalTeam.getId();
		    NationalTeam nationalTeamGot = playerManager.findNationalTeamById(id);
		    assertEquals(nationalTeamGot, nationalTeam);
		    playerManager.deleteNationalTeam(nationalTeamGot);
	  }
	  
	  @Test
	  public void shouldReturnArraySizePlayer() {
		  Player player = new Player(AGE_2, NAME_2, MARKETVALUE_2, new NationalTeam());
		  Contract contract1 = new Contract(player,SALARY_1, TITLE_1, COMPANYNAME_1);
		  Contract contract2 = new Contract(player,SALARY_2, TITLE_2, COMPANYNAME_2);
		  Contract contract3 = new Contract(player,SALARY_3, TITLE_3, COMPANYNAME_3);
		  List<Contract> contracts = new ArrayList<Contract>();
		  contracts.add(contract1);
		  contracts.add(contract2);
		  contracts.add(contract3);
		  player.setContracts(contracts);
		  playerManager.addPlayer(player);
		  int playersCount = playerManager.getAllPlayers().size();
		  Player playerGot = playerManager.getAllPlayers().get(playersCount - 1);
		  assertEquals(playerGot.getContracts().size(), 3);
		  playerManager.deletePlayer(playerGot);
	  }
	  
	  @Test
	  public void shouldReturnArraySizeNationalTem() {
		  NationalTeam nationalTeam = new NationalTeam(NATIONALNAME_1);
		  Player player1 = new Player(AGE_1, NAME_1, MARKETVALUE_1, nationalTeam);
		  Player player2 = new Player(AGE_2, NAME_2, MARKETVALUE_2, nationalTeam);
		  List<Player> players = new ArrayList<Player>();
		  players.add(player1);
		  players.add(player2);
		 
		  nationalTeam.setPlayers(players);
		  playerManager.addNationalTeam(nationalTeam);
		  int nationalTeamsCount = playerManager.getAllNationalTeams().size();
		  NationalTeam nationalTeamGot = playerManager.getAllNationalTeams().get(nationalTeamsCount - 1);
		  assertEquals(nationalTeamGot.getPlayers().size(), 2);
		  playerManager.deleteNationalTeam(nationalTeamGot);
	  }
}
