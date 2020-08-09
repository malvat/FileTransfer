

public class Util {
	public enum CMD {PUT, GET, QUIT, ERROR};
	
	public static CMD readCommand(String command) {
		if(command.equals("quit")) {
			// return quit the process
			return CMD.QUIT;
		} else {
			String[] splits = command.split(" ");
			if(splits[0].equals("put")) {
				// put file
				return CMD.PUT;
			} else if(splits[0].equals("get")) {
				// get file
				return CMD.GET;
			} else {
				// some error
				return CMD.ERROR;
			}
		}
	}
}
