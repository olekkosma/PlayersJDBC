package com.example.jdbcdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.jdbcdemo.domain.Player;

public class PlayerManagerJDBC implements PlayerManager{

	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private String 	createTable = "CREATE TABLE Player(id bigint GENERATED BY DEFAULT AS IDENTITY"
	+ ", age INTEGER,name VARCHAR(40),marketValue float)";
	private PreparedStatement addPlayerStatement;
	private PreparedStatement selectPlayerStatement;
	private PreparedStatement findPlayerByIdStatement;
	private PreparedStatement findPlayerByNameStatement;
	private PreparedStatement deletePlayerStatement;
	private PreparedStatement deletePlayerByIdStatement;
	private PreparedStatement deletePlayerByNameStatement;
	private PreparedStatement deleteAllPlayersStatement;
	private PreparedStatement updatePlayerStatement;
	private PreparedStatement updatePlayerNameStatement;
	private Statement statement;
	
	public void generateStatements() throws SQLException{
		
		addPlayerStatement = connection.prepareStatement(
		"INSERT INTO Player (age,name,marketValue) VALUES (?, ?, ?)");
		selectPlayerStatement = connection.prepareStatement(
		"SELECT id,age, name, marketValue FROM Player ORDER BY id ASC");
		findPlayerByIdStatement = connection.prepareStatement(
		"SELECT id,age, name, marketValue FROM Player WHERE id = ?");
		findPlayerByNameStatement = connection.prepareStatement(
		"SELECT id,age, name, marketValue FROM Player WHERE name = ?");
		deletePlayerStatement = connection.prepareStatement(
		"DELETE FROM Player WHERE id = ?");
		deletePlayerByNameStatement = connection.prepareStatement(
		"DELETE FROM Player WHERE name = ?");
		deletePlayerByIdStatement = connection.prepareStatement(
		"DELETE FROM Player WHERE id = ?");
		deleteAllPlayersStatement = connection.prepareStatement(
		"DELETE FROM Player");
		updatePlayerStatement = connection.prepareStatement(
		"UPDATE Player SET   id = id,age = ?, name = ?, marketValue = ? WHERE id = ?");
		updatePlayerNameStatement = connection.prepareStatement(
		"UPDATE Player SET id = id,age = age, name = ?, marketValue = marketValue WHERE id = ?");
	}
	public PlayerManagerJDBC(){
		
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			ResultSet rs = connection.getMetaData().getTables(null, null, null,null);
			boolean tableExists = false;
			while (rs.next()) {
				
				if ("Player".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
					}
				}
		
			if(!tableExists){
				statement.executeUpdate(createTable);
			}
			generateStatements();	
		} catch (SQLException e) {
				
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public boolean closeConnection() {
	    try {
	      connection.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	      return false;
	    }
	    return true;
	  }

	@Override
	public int addPlayer(Player player) {
		int addedPlayer = 0;
		try{
			addPlayerStatement.setInt(1, player.getAge());
			addPlayerStatement.setString(2, player.getName());
			addPlayerStatement.setDouble(3, player.getMarketValue());
			addedPlayer = addPlayerStatement.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return addedPlayer;
	}
	
	@Override
	public int deletePlayer(Player player) {
		int id = (int)player.getId();
		try{
			
			deletePlayerStatement.setInt(1, id);	
			deletePlayerStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	

	@Override
	public List<Player> getAllPlayers() {
		
		List<Player> players = new ArrayList<Player>();
		
		try {
			ResultSet result = selectPlayerStatement.executeQuery();
			while (result.next()) {
				Player newPlayer = new Player();
				newPlayer.setId(result.getInt("id"));
				newPlayer.setAge(result.getInt("age"));
				newPlayer.setName(result.getString("name"));
				newPlayer.setMarketValue(result.getDouble("marketValue"));
				players.add(newPlayer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}


	@Override
	public int deletePlayerByName(String name) {
		int deletedPlayer = 0;
		try{
			
			deletePlayerByNameStatement.setString(1, name);	
			deletedPlayer = deletePlayerByNameStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return deletedPlayer;
	}

	@Override
	public int deletePlayerById(Long id) {
		int intId =  id.intValue();
		try{
			
			deletePlayerByIdStatement.setLong(1, intId);	
			deletePlayerByIdStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return intId;
	}

	@Override
	public int deleteAllPlayers() {
		int numberOfPlayers = 0;
		try {
			numberOfPlayers = deleteAllPlayersStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfPlayers;
		
	}

	@Override
	public int updatePlayerData(Player player, int age, String name, double marketValue) {
		
		int updatedPlayer = 0;
	    try {
	    updatePlayerStatement.setInt(1, age);
	      updatePlayerStatement.setString(2, name);
	      updatePlayerStatement.setDouble(3, marketValue);
	      updatePlayerStatement.setLong(4, player.getId());
	      updatedPlayer = updatePlayerStatement.executeUpdate();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return updatedPlayer;
		
	}

	@Override
	public Player findById(long id) {
		Player searchedPlayer = null;
		
		try {
			findPlayerByIdStatement.setLong(1,id);
			ResultSet resultSet = findPlayerByIdStatement.executeQuery();
			if (resultSet.next() == true) {
				int age = resultSet.getInt("age");
		        String name = resultSet.getString("name");
		        double marketValue = resultSet.getDouble("marketValue");
		        searchedPlayer = new Player(age, name, marketValue);
		      }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return searchedPlayer;
	}

	@Override
	public Player findByName(String name) {
		Player searchedPlayer = null;
		
		try {
			findPlayerByNameStatement.setString(1,name);
			ResultSet resultSet = findPlayerByNameStatement.executeQuery();
			if (resultSet.next() == true) {
				int age = resultSet.getInt("age");
		        String name2 = resultSet.getString("name");
		        double marketValue = resultSet.getDouble("marketValue");
		        searchedPlayer = new Player(age, name2, marketValue);
		      }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return searchedPlayer;
	}
	@Override
	public int updatePlayerName(Player player, String name) {
		
		int updatedPlayer = 0;
	    try {
	    	updatePlayerNameStatement.setString(1, name);
	    	updatePlayerNameStatement.setLong(2, player.getId());
	    	updatedPlayer = updatePlayerNameStatement.executeUpdate();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return updatedPlayer;
		
	}
}
