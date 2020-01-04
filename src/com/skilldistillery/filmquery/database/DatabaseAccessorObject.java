package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	private String user = "student";
	private String pass = "student";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private PreparedStatement prepStatementGenerator(Connection conn, String sqlTxt, int id) throws SQLException  {
		PreparedStatement prepStmt = conn.prepareStatement(sqlTxt);
		prepStmt.setInt(1,id);
		
		return prepStmt;
	}

	@Override
	public Film findFilmById(int filmId) {
		int id = filmId;
		String sqlTxt = "SELECT * FROM film WHERE id = ?";
		Film film = null;
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement prepStmt = prepStatementGenerator(conn,sqlTxt,id);
				ResultSet rs = prepStmt.executeQuery();) {
			while(rs.next()) {
			film = new Film(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
					rs.getInt("release_year"),rs.getInt("language_id"),rs.getInt("rental_duration"),rs.getDouble("rental_rate"),
					rs.getInt("length"),rs.getDouble("replacement_cost"),rs.getString("rating"),rs.getString("special_features"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		int id = actorId;
		String sqlTxt = "SELECT * FROM actor WHERE id = ?";
		Actor actor = null;
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement prepStmt = prepStatementGenerator(conn,sqlTxt,id);
				ResultSet rs = prepStmt.executeQuery();) {
			while(rs.next()) {
			actor = new Actor(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		int id = filmId;
		String sqlTxt = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film.id = film_actor.film_id WHERE film.id = ?";
		
		Actor actor = null;
		List<Actor> filmList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement prepStmt = prepStatementGenerator(conn,sqlTxt,id);
				ResultSet rs = prepStmt.executeQuery();) {
			while(rs.next()) {
				filmList.add(new Actor(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filmList;
	}

}
