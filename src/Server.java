import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
	static class AdditionHandle implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			String response;

			String[] nums = t.getRequestURI().getQuery().split(",");

			if (nums.length != 2)
				response = "Wrong input. Please use format: <num1>,<num2>";

			else {
				AdditionAndMultiplication action = new AdditionAndMultiplication();
				response = "Answer:" + action.add(Long.parseLong(nums[0], 10), Long.parseLong(nums[1], 10));
			}
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class MultiHandle implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			String response;

			String[] nums = t.getRequestURI().getQuery().split(",");

			if (nums.length != 2)
				response = "Wrong input. Please use format: <num1>,<num2>";
			else {
				AdditionAndMultiplication action = new AdditionAndMultiplication();
				response = "Anwer: " + action.mult(Long.parseLong(nums[0], 10), Long.parseLong(nums[1], 10));
			}
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}


	public void start() throws Exception  {
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);

		server.createContext("/add", new AdditionHandle());
		server.createContext("/mult", new MultiHandle());

		// default executor
		server.setExecutor(null); 
		server.start();
	}
}
