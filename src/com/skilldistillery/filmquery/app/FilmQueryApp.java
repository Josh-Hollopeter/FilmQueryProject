package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
// made language a list to future proof in case films release with multiple languages
	}

	private void test() {
		Film film = db.findFilmById(2);
		Actor actor = db.findActorById(1);
		List<Actor> alist = db.findActorsByFilmId(5);
		System.out.println(film);
		System.out.println(actor);
		System.out.println(alist);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		while (true) {
			System.out.println("Please select an option");
			System.out.println("1: Select a film by id");
			System.out.println("2: Search for film by keyword");
			System.out.println("3: Exit application");
			String userSelection = input.nextLine();
			switch (userSelection) {
				case "1":
					findFilmById(input);
					continue;
				case "2":
					findFilmByKeyword(input);
					continue;
				case "3":
					System.out.println("Goodbye");
					break;
				default:
					System.out.println("Invalid");
					continue;

			}
			break;
		}

	}

	private void findFilmById(Scanner input) {
		while (true) {
			try {
				System.out.println("Select a film id!");
				int filmId = input.nextInt();
				Film film = db.findFilmById(filmId);
				if(film != null) {
				System.out.println(film);
				}else System.out.println("No matches");
				input.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Input must be a number\n");
				input.nextLine();
			}

		}
	}

	private void findFilmByKeyword(Scanner input) {

		System.out.println("Enter a keyword");
		String keyWord = input.nextLine();
		List<Film> filmList = db.findFilmByKeyword(keyWord);

		if (filmList.size() >0 ) {
			for (Film film : filmList) {
				System.out.println(film);
			}
		} else {
			System.out.println("No matches");
		}

	}

}
