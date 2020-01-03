package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	@Override
	public Film findFilmById(int filmId) {
		String sqlTxt = "SELECT * FROM film WHERE id = ?";
		Film film = null;
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement prepStmt = conn.prepareStatement(sqlTxt);
				prepStmt.setInt(1,filmId);
				ResultSet rs = prepStmt.executeQuery();) {
			

			film = new Film(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
					rs.getInt("year"),rs.getInt("language_id"),rs.getInt("rental_duration"),rs.getDouble("rental_rate"),
					rs.getInt("length"),rs.getDouble("replacement_cost"),rs.getString("rating"),rs.getString("special_features"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}
