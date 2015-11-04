public class test
{
	public static void main(String[] args)
	{
		

		Server s = new Server("/home/imagine/workspace/NetworkServer/src/inputfile.txt");
		s.startServer();
		s.addUser("nikos", "123");
		
		s.connectToNetwork("Kostas-pc", "dranidis", "dhr342");
		s.connectToNetwork("Thanos-pc", "nikos", "123");
		
		s.showUsers();
		
		s.showConnected();
		
		
	}
};
