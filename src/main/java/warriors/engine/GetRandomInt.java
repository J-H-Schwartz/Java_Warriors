package warriors.engine;

import java.util.Random;

public class GetRandomInt {
	private GetRandomInt() {
	}

	public static int getRandomInt(int max) {

		Random rand = new Random();
		int randomInt = rand.nextInt(max);
		return randomInt;
	}
}
