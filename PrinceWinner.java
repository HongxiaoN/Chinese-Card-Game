package finalGame;

/*Winning
 * Prince Niu
 * December 21st, 2017
 */
import java.util.*;

public class PrinceWinner {
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static void main(String[] args) {
		System.out.println("Welcome to the Winner!" + "\nThis game is simple poker game which has three players. "
				+ "\nIn the game, you will be one player and play with two computers. " + "\nYou will get 18 cards. "
				+ "\nThe winning way of this game is that to finish your poker cards as soon as possible. "
				+ "\nThe order of this card is 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A, 2, Joker Silver(JS), Joker Golden(JG). "
				+ "\nThe person who has Heart3 can be the first player, and then use clockwise direction to named second player and the third player.");
		String name;
		String[] suit = { "H", "C", "D", "S" };
		String[] card = { "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2" };
		int[] order = { 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8,
				9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 14, 15 };
		String[] deck = new String[54];
		String[] ndeck = new String[54];// This is new deck for shuffled cards.
		String[] user = new String[18];
		String[] player1 = new String[18];// This is new deck for computer player1.
		String[] player2 = new String[18];// This is new deck for computer player2.
		String[] Joker = { "JS", "JG" };// This is two jokers in the deck.
		int index = 0;// This is for deck index.
		int pow[] = { 0, 0, 0 };// This is for detect who should play card.
		// There are three ArrayList to show three players cards.
		ArrayList ulist = new ArrayList(18);
		ArrayList p1list = new ArrayList(18);
		ArrayList p2list = new ArrayList(18);
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < suit.length; j++) {
				deck[index] = suit[j] + card[i];
				index++;
			}
		}
		deck[52] = "JS";
		deck[53] = "JG";
		int x = (int) (Math.random() * 54 + 0);
		int y = (int) (Math.random() * 54 + 0);
		while (y == x) {
			y = (int) (Math.random() * 54 + 0);
		}
		ndeck[x] = Joker[0];
		ndeck[y] = Joker[1];
		for (int i = 0; i < deck.length; i++) {
			if (i == x || i == y)
				i++;
				ndeck[i] = suit[(int) (Math.random() * 4 + 0)] + card[(int) (Math.random() * 13 + 0)];
			for (int a = 0; a < i; a++) {
				if (ndeck[a].equals(ndeck[i])) {
					i--;
				}
			}
		}
		for (int i = 0; i < 18; i++) {
			user[i] = ndeck[i];
		}
		for (int i = 0; i < 18; i++) {
			player1[i] = ndeck[18 + i];
		}
		for (int i = 0; i < 18; i++) {
			player2[i] = ndeck[36 + i];
		}
		System.out.print("Please, enter your name:");
		name = n();
		// Make order for the cards in their hands.
		String[] nuser = sortArry(user, deck);
		String[] nplayer1 = sortArry(player1, deck);
		String[] nplayer2 = sortArry(player2, deck);
		for (int i = 0; i < 18; i++) {
			ulist.add(nuser[i]);
		}
		for (int i = 0; i < 18; i++) {
			p1list.add(nplayer1[i]);
		}
		for (int i = 0; i < 18; i++) {
			p2list.add(nplayer2[i]);
		}
		int field;// The card which currently on the table.
		first(user, player1, player2, pow);// Detect who is the first one that play card.
		for (int i = 0;; i++) {
			field = 0;
			field = start(pow, order, ulist, p1list, p2list, deck);
			if (ulist.isEmpty() || p1list.isEmpty() || p2list.isEmpty()) {
				break;
			}
			round(pow, order, ulist, p1list, p2list, deck, field);
			if (ulist.isEmpty() || p1list.isEmpty() || p2list.isEmpty()) {
				break;
			}
		}
		if (ulist.isEmpty()) {
			System.out.println(name + "You are the winner.");
		} else if (p1list.isEmpty()) {
			System.out.println("The computer1 is the winner.");
		} else if (p2list.isEmpty()) {
			System.out.println("The computer2 is the winner.");
		}

	}

	/**
	 * This method is for ask user's name. Pre: String name Post: Return the user's
	 * name.
	 */
	@SuppressWarnings("resource")
	public static String n() {
		String name;
		Scanner input = new Scanner(System.in);
		name = input.nextLine();
		return name;
	}

	/**
	 * This method make order for cards that in players hand. Pre: ArryList
	 * arraylist[], String deck[]. Post: Return the arraylist which is already in
	 * the order.
	 */
	public static String[] sortArry(String arrayList[], String deck[]) {
		String temp;
		for (int i = 0; i < 54; i++) {
			for (int j = 0; j < 18; j++) {
				for (int a = 0; a < 18; a++) {
					if (arrayList[a].equalsIgnoreCase(deck[i])) {
						temp = arrayList[j];
						arrayList[j] = arrayList[a];
						arrayList[a] = temp;
					}
				}
			}
		}
		return arrayList;
	}

	/**
	 * This method is for detect who will be the first to play card. Pre: String
	 * user[], String player1[], String player2[], int pow[].
	 */
	public static void first(String user[], String player1[], String player2[], int pow[]) {
		for (int i = 0; i < 18; i++) {
			if (user[i].equalsIgnoreCase("H3")) {
				pow[2]++;
				break;
			} else if (player1[i].equalsIgnoreCase("H3")) {
				pow[0]++;
				break;
			} else if (player2[i].equalsIgnoreCase("H3")) {
				pow[1]++;
				break;
			}
		}
		System.out.println("---------");
		if (pow[2] == 1) {
			System.out.println("You are the first one who play card(s).");
		}
		if (pow[0] == 2) {
			System.out.println("You are the third one who play card(s).");
		}
		if (pow[1] == 3) {
			System.out.println("You are the Second one who play card(s).");
		}
	}

	/**
	 * This method is for detect the first cards when game start and when others
	 * passed. Pre: int power[], int order[], ArrayList ulist, ArrayList p1list,
	 * ArrayList p2list, String deck[]. Post: Return the card that just played in
	 * the table.
	 */
	@SuppressWarnings({ "rawtypes", "resource", "unused" })
	public static int start(int pow[], int order[], ArrayList ulist, ArrayList p1list, ArrayList p2list,
			String deck[]) {
		String card;
		int field = 0;// At the begin, field is zero.
		int keep = ulist.size();// this is for storing the size of old ulist.
		Scanner input = new Scanner(System.in);
		if (pow[0] == 1) {
			// Change the power of players.
			pow[0]--;
			pow[1]++;
			System.out.println("The computer1 played " + p1list.get(0) + ".");
			for (int i = 0; i < deck.length; i++) {
				if (String.valueOf(p1list.get(0)).equals(deck[i])) {
					field = order[i];
					break;
				}
			}
			p1list.remove(0);
		}

		else if (pow[1] == 1) {
			pow[1]--;
			pow[2]++;
			System.out.println("The computer2 played " + p2list.get(0) + ".");
			for (int i = 0; i < deck.length; i++) {
				if (String.valueOf(p2list.get(0)).equals(deck[i])) {
					field = order[i];
					break;
				}
			}
			p2list.remove(0);
		}

		else if (pow[2] == 1) {
			pow[2]--;
			pow[0]++;
			System.out.println("There are your cards:" + ulist);
			System.out.println("Please, play a card:");
			for (int b = 0;; b++) {
				card = input.next();
				for (int a = 0; a < ulist.size(); a++) {
					if (String.valueOf(ulist.get(a)).equalsIgnoreCase(card)) {
						for (int i = 0; i < deck.length; i++) {
							if (card.equalsIgnoreCase(deck[i])) {
								field = order[i];
								break;
							}
						}
						ulist.remove(a);
					}
				}
				if (keep == ulist.size()) {
					System.out.print("Sorry, you must play a card:");
				} else {
					break;
				}
			}
		}
		return field;
	}

	/**
	 * This method is let three players play Pre: int power[], int order[],
	 * ArrayList ulist, ArrayList p1list, ArrayList p2list, String deck[], int
	 * field.
	 */
	@SuppressWarnings({ "rawtypes", "resource", "unused" })
	public static void round(int pow[], int order[], ArrayList ulist, ArrayList p1list, ArrayList p2list, String deck[],
			int field) {
		String card;// The card that user play.
		int pass = 0;// Recording how many players are passing.
		// Recording the old size of three players arraylist.
		int keep1;
		int keep2;
		int keep3;
		Scanner input = new Scanner(System.in);
		for (int c = 0;; c++) {
			System.out.println("---------");
			keep1 = p1list.size();
			keep2 = p2list.size();
			keep3 = ulist.size();
			if (pow[0] == 1) {
				pow[0]--;
				pow[1]++;
				int keep = 0;// Carrying the number of deck.
				for (int a = 0; a < p1list.size(); a++) {
					for (int i = 0; i < deck.length; i++) {
						if (String.valueOf(p1list.get(a)).equals(deck[i])) {
							keep = i;
							break;
						}
					}
					if (order[keep] > field) {
						field = order[keep];
						System.out.println("The computer1 played " + p1list.get(a) + ".");
						p1list.remove(a);
						pass = 0;
						break;
					}
				}
				if (keep1 == p1list.size()) {
					pass++;
					System.out.println("The computer1 passed");
				}
			} else if (pow[1] == 1) {
				pow[1]--;
				pow[2]++;
				int keep = 0;
				for (int a = 0; a < p2list.size(); a++) {
					for (int i = 0; i < deck.length; i++) {
						if (String.valueOf(p2list.get(a)).equals(deck[i])) {
							keep = i;
							break;
						}
					}
					if (order[keep] > field) {
						field = order[keep];
						System.out.println("The computer2 played " + p2list.get(a) + ".");
						p2list.remove(a);
						pass = 0;
						break;
					}
				}
				if (keep2 == p2list.size()) {
					pass++;
					System.out.println("The computer2 passed");
				}
			} else if (pow[2] == 1) {
				System.out.println("There are your cards:" + ulist);
				pow[2]--;
				pow[0]++;
				int keep = 0;
				System.out.println("Please choose play a card or pass:");
				for (int b = 0;; b++) {
					card = input.next();
					for (int a = 0; a < ulist.size(); a++) {
						if (String.valueOf(ulist.get(a)).equalsIgnoreCase(card)) {
							for (int i = 0; i < deck.length; i++) {
								if (card.equalsIgnoreCase(deck[i])) {
									keep = i;
									break;
								}
							}
							if (order[keep] > field) {
								field = order[keep];
								ulist.remove(a);
								pass = 0;
							}
						}
					}
					if (card.equalsIgnoreCase("pass")) {
						pass++;
						break;
					} else if (keep3 == ulist.size()) {
						System.out.print("Sorry, you may want to play a bigger card & play a card in your hand:");
					} else {
						break;
					}
				}
			}
			if (ulist.isEmpty() || p1list.isEmpty() || p2list.isEmpty()) {
				break;
			}
			if (pass == 2) {
				break;
			}
		}
	}
}