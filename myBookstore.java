package myBookstore;

import java.sql.*;
import java.util.Scanner;

public class myBookstore {

	public static void main(String[] args) {
		mainList();
	}

	public static void mainList() {

		System.out.println(
				"Would you like to \n1. Enter a new book \n2. Update and existing book \n3. Delete an existing book \n4. Search for a book or author \n0. Exit this program");

		Scanner Choice = new Scanner(System.in);
		int User = Choice.nextInt();

		do {

			if (User == 1) {
				enterBook();
				break;
			}

			else if (User == 2) {
				updateBook();
				break;
			}

			else if (User == 3) {
				deleteBook();
				break;
			}

			else if (User == 4) {
				searchBook();
				break;

			} else {
				break;
			}

		} while (User != 0);

		Choice.close();
	}

	public static void enterBook() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
				"myuser", "MyUser1981"); Statement stmt = conn.createStatement();) {

			String showLast = "SELECT * FROM books ORDER BY ID DESC LIMIT 1";
			ResultSet rset = stmt.executeQuery(showLast);
			System.out.println("The last record in the database is: ");

			while (rset.next()) {
				System.out.println(rset.getInt("id") + ", " + rset.getString("title") + ", " + rset.getString("author")
						+ ", " + rset.getInt("qty"));
			}

			Scanner scN = new Scanner(System.in);

			System.out.println("Please enter book code: ");
			int ID = scN.nextInt();

			scN.nextLine();

			System.out.println("Please enter the book name: ");
			String bookName = scN.nextLine();

			System.out.println("Please enter the author's name: ");
			String author = scN.nextLine();

			System.out.println("How many books are in stock?: ");
			int qtyBooks = scN.nextInt();

			String sqlInsert = "insert into books values (" + ID + ", '" + bookName + "', '" + author + "', " + qtyBooks
					+ ")";
			System.out.println("The SQL query is: " + sqlInsert);

			int countInserted = stmt.executeUpdate(sqlInsert);
			System.out.println(countInserted + " records inserted.\n");

			System.out.println("Enter another book? \n1. Yes \n2. Main Menu \n0. Exit");
			int More = scN.nextInt();
			if (More == 1) {
				enterBook();
			} else if (More == 2) {
				mainList();
			} else {
				System.out.println(0 + ": All done then");
			}
			scN.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateBook() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
				"myuser", "MyUser1981"); Statement stmt = conn.createStatement();) {

			Scanner scU = new Scanner(System.in);

			System.out.println("Enter the book code of the record you wish to update ");
			int ID = scU.nextInt();

			String findID = ("select * from books where ID = " + ID);
			System.out.println(findID);

			System.out.println("Which field would you like to update? \n1. ID \n2. Title \n3. Author \n4. Qty");
			int uUpdate = scU.nextInt();

			if (uUpdate == 1) {

				System.out.println("Set book ID to: ");
				int idUpdate = scU.nextInt();
				String sqlUpdate = "update books " + "set id = '" + idUpdate + "' where id = (" + ID + ")";

				System.out.println("The SQL query is: " + sqlUpdate);

				int countUpdated = stmt.executeUpdate(sqlUpdate);
				System.out.println(countUpdated + " records updated.\n");
			}

			else if (uUpdate == 2) {

				System.out.println("Set book title to: ");
				scU.nextLine();
				String titleUpdate = scU.nextLine();

				String sqlUpdate = "update books set Title = '" + titleUpdate + "' where id = (" + ID + ")";

				System.out.println("The SQL query is: " + sqlUpdate);

				int countUpdated = stmt.executeUpdate(sqlUpdate);
				System.out.println(countUpdated + " records updated.\n");
			}

			else if (uUpdate == 3) {

				System.out.println("Set author to: ");
				scU.nextLine();
				String authorUpdate = scU.nextLine();

				String sqlUpdate = "update books set author = '" + authorUpdate + "' where id = (" + ID + ")";

				System.out.println("The SQL query is: " + sqlUpdate);

				int countUpdated = stmt.executeUpdate(sqlUpdate);
				System.out.println(countUpdated + " records updated.\n");

			}

			else if (uUpdate == 4) {

				System.out.println("Set qty to: ");
				scU.nextLine();
				int qtyUpdate = scU.nextInt();

				String sqlUpdate = "update books set qty = '" + qtyUpdate + "' where id = (" + ID + ")";

				System.out.println("The SQL query is: " + sqlUpdate);

				int countUpdated = stmt.executeUpdate(sqlUpdate);
				System.out.println(countUpdated + " records updated.\n");
			}

			System.out.println("Enter another book? \n1. Yes \n2. Main Menu \n3. View Changes \n0. Exit");
			int More = scU.nextInt();

			if (More == 1) {
				enterBook();
			}

			else if (More == 2) {
				mainList();
			}

			else if (More == 3) {
				String showBooks = "select * from books";
				ResultSet rsetU = stmt.executeQuery(showBooks);
				System.out.println("Viewing your changes: ");

				while (rsetU.next()) {
					System.out.println(rsetU.getInt("id") + ", " + rsetU.getString("title") + ", "
							+ rsetU.getString("author") + ", " + rsetU.getInt("qty"));
				}

				System.out.println();
				mainList();
			}

			else {
				System.out.println(0 + ": All done then");
			}

			scU.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void deleteBook() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
				"myuser", "MyUser1981"); Statement stmt = conn.createStatement();) {

			String showBooks = "select * from books";
			ResultSet rsetD = stmt.executeQuery(showBooks);
			System.out.println("Viewing your changes: ");

			while (rsetD.next()) {
				System.out.println(rsetD.getInt("id") + ", " + rsetD.getString("title") + ", "
						+ rsetD.getString("author") + ", " + rsetD.getInt("qty"));
			}

			System.out.println("Enter the book code of the record you wish to delete ");
			Scanner scD = new Scanner(System.in);
			int recordDelete = scD.nextInt();

			String sqlDelete = "delete from books where id = '" + recordDelete + "'";

			int countUpdated = stmt.executeUpdate(sqlDelete);
			System.out.println(countUpdated + " records updated.\n");

			System.out.println();

			System.out.println(
					"Would you like to delete another record? \n1. Yes \n2. Main Menu \n3. View Changes \n0. Exit");
			int More = scD.nextInt();

			if (More == 1) {
				deleteBook();
			}

			else if (More == 2) {
				mainList();
			}

			else if (More == 3) {
				String showBooksAgain = "select * from books";
				ResultSet rsetDA = stmt.executeQuery(showBooksAgain);
				System.out.println("Viewing your changes: ");

				while (rsetDA.next()) {
					System.out.println(rsetDA.getInt("id") + ", " + rsetDA.getString("title") + ", "
							+ rsetDA.getString("author") + ", " + rsetDA.getInt("qty"));
				}

				System.out.println();
				mainList();
			}

			else {
				System.out.println(0 + ": All done then");
			}

			scD.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void searchBook() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
				"myuser", "MyUser1981"); Statement stmt = conn.createStatement();) {
			Scanner scS = new Scanner(System.in);

			System.out.println("Enter the number to search by: \n1. ID \n2. Book Name \n3. Author");
			int sChoice = scS.nextInt();

			if (sChoice == 1) {
				System.out.println("What is the book code you want to search by?: ");
				int ID = scS.nextInt();
				scS.nextLine();
				String findID = ("select * from books where ID = '" + ID + "'");
				System.out.println();
				ResultSet rsetS1 = stmt.executeQuery(findID);
				while (rsetS1.next()) {
					System.out.println(rsetS1.getInt("id") + ", " + rsetS1.getString("title") + ", "
							+ rsetS1.getString("author") + ", " + rsetS1.getInt("qty"));
				}
			}

			else if (sChoice == 2) {
				scS.nextLine();
				System.out.println("What is the book name you want to search by?: ");
				String bookName = scS.nextLine();

				String findBook = ("select * from books where Title = '" + bookName + "'");
				System.out.println();
				ResultSet rsetS2 = stmt.executeQuery(findBook);
				while (rsetS2.next()) {
					System.out.println(rsetS2.getInt("id") + ", " + rsetS2.getString("title") + ", "
							+ rsetS2.getString("author") + ", " + rsetS2.getInt("qty"));
				}

			} else {
				scS.nextLine();
				System.out.println("What is the author's name you want to search by?: ");
				String author = scS.nextLine();

				String findAuthor = ("select * from books where author = '" + author + "'");
				System.out.println();
				ResultSet rsetS3 = stmt.executeQuery(findAuthor);
				while (rsetS3.next()) {
					System.out.println(rsetS3.getInt("id") + ", " + rsetS3.getString("title") + ", "
							+ rsetS3.getString("author") + ", " + rsetS3.getInt("qty"));
				}
			}

			System.out.println();

			System.out.println("Would you like to search another record? \n1. Yes \n2. Main Menu \n0. Exit");
			int More = scS.nextInt();

			if (More == 1) {
				searchBook();
			}

			else if (More == 2) {
				mainList();
			}

			else {
				System.out.println(0 + ": All done then");
			}

			scS.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
