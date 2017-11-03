package com.example.jdbcdemo.service;

import java.util.List;

import com.example.jdbcdemo.domain.Player;

public interface PlayerManager {
	
	public int addPlayer(Player player);
	public int deletePlayer(Player player);
	public int deletePlayerByName(String name);
	public int deletePlayerById(Long id);
	public int deleteAllPlayers();
	public int updatePlayerData(Player player, int age, String name, double marketValue);
	public int updatePlayerName(Player player, String name);
	public List<Player> getAllPlayers();
	public Player findById(long id);
	public Player findByName(String name);

	
}
