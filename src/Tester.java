public class Tester {

	public static void main(String[] args) {

		Server test = new Server();
		try {
			test.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
