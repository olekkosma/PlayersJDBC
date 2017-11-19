package com.example.jdbcdemo.service;

import static org.hamcrest.CoreMatchers.either;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.example.jdbcdemo.domain.Player;

public class PlayerManagerTest {

	private static PlayerManagerJDBC playerManager = new PlayerManagerJDBC();
	private static List<Player> Players = new ArrayList<>();
	private final static double MARKETVALUE_1 = 200000.50;
	private final static String NAME_1 = "Leo Messi";
	private final static int AGE_1 = 30;
	
	@Before
	 public  void checkAddPlayer(){
		Player player = new Player(AGE_1,NAME_1,MARKETVALUE_1);
		assertEquals(1,playerManager.addPlayer(player));
	 }
	
	@Before
	 public void checkAddMultiplePlayers(){
		/*
		assertEquals(1,playerManager.addPlayer(new Player(25,"Ivan Perisic",5500000.1)));
		assertEquals(1,playerManager.addPlayer(new Player(10,"Ivan Rakitic",4500000.5)));
		assertEquals(1,playerManager.addPlayer(new Player(45,"Luca Modric",6500000.7)));
		assertEquals(1,playerManager.addPlayer(new Player(22,"Niejaki Williams",2450000.7)));
		*/
		//Transaction implementation
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(new Player(25,"Ivan Perisic",5500000.1));
		playersList.add(new Player(10,"Ivan Rakitic",4500000.5));
		playersList.add(new Player(45,"Luca Modric",6500000.7));
		playersList.add(new Player(22,"Niejaki Williams",2450000.7));
		//playersList.add(new Player(22,"Niejaki Williams",2450000.7));
		
		int addedPlayers = playerManager.addAllPlayersFromList(playersList);
		assertThat(addedPlayers, either(is(0)).or(is(4))); 
	 }
	
	@Test
	 public void checkConnection(){
		assertNotNull(playerManager.getConnection());
	 }
	
	@Test
	 public void checkGetAllPlayers() {
		
	    assertEquals(5, playerManager.getAllPlayers().size());
	 }
	
	@Test
	 public void checkUpdatePlayer() {
		int age = 26;
	    String name = "Iwan Perisic";
	    double marketValue =4500000.5 ;
	    Players = playerManager.getAllPlayers();
	    playerManager.updatePlayerData(Players.get(0), age, name, marketValue);
	    Players = playerManager.getAllPlayers();
	    
	    assertEquals(age, Players.get(0).getAge());
	    assertEquals(name, Players.get(0).getName());
	    assertEquals(marketValue, Players.get(0).getMarketValue(),0.001);
	 }
	
	@Test
	 public void checkUpdatePlayerName() {
	    String name = "Iwona Perisic";
	    Players = playerManager.getAllPlayers();
	    playerManager.updatePlayerName(Players.get(0),name);
	    Players = playerManager.getAllPlayers();
	    
	    assertEquals(name, Players.get(0).getName());

	 }
	
	
	@Test
	  public void checkDeletePlayerById() {
		Players = playerManager.getAllPlayers();
		int size = Players.size();
	    Player playerToDelete = Players.get(0);
	    playerManager.deletePlayerById(playerToDelete.getId());;
	    Players = playerManager.getAllPlayers();
	    
	    assertEquals(size-1, Players.size());
	    playerManager.addPlayer(playerToDelete);
	  }
	
	@Test
	  public void checkDeletePlayerByName() {
		Players = playerManager.getAllPlayers();
		int size = Players.size();
	    Player playerToDelete = Players.get(0);
	    playerManager.deletePlayerByName(playerToDelete.getName());;
	    Players = playerManager.getAllPlayers();
	    
	    assertEquals(size-1, Players.size());
	    playerManager.addPlayer(playerToDelete);
	  }
	
	@Test
	  public void checkFindPlayerById() {
		Players = playerManager.getAllPlayers();
	    Player playerToFind = Players.get(0);
	    Player foundedPlayer = playerManager.findById(playerToFind.getId());
	    
	    assertEquals(playerToFind.getName(), foundedPlayer.getName());
	    assertEquals(playerToFind.getAge(), foundedPlayer.getAge());
	    assertEquals(playerToFind.getMarketValue(), foundedPlayer.getMarketValue(), 0.001);
	  }
	
	@Test
	  public void checkFindPlayerByName() {
		Players = playerManager.getAllPlayers();
	    Player playerToFind = Players.get(0);
	    Player foundedPlayer = playerManager.findByName(playerToFind.getName());
	    
	    assertEquals(playerToFind.getName(), foundedPlayer.getName());
	    assertEquals(playerToFind.getAge(), foundedPlayer.getAge());
	    assertEquals(playerToFind.getMarketValue(), foundedPlayer.getMarketValue(), 0.001);
	  }
	
	@After
	  public void checkDeleteAllPlayers() {
		playerManager.deleteAllPlayers();
	    assertTrue(playerManager.getAllPlayers().size() == 0);
	 }
	
	@AfterClass
	public static void checkCloseConnection() {
	   assertNotNull(playerManager.closeConnection());
	}
}
