package project;

public class User implements Comparable<User> {
	private int score;
	private String name;

	public User(int s, String n) {
		score = s;
		name = n;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(User u) {
		return getScore() < u.getScore() ? -1 : getScore() == u.getScore() ? 0 : 1;
	}
}
