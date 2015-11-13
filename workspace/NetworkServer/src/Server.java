
import java.util.ArrayList;

public class Server {

	private String filePath;
	private ListInterface usersList;
	private StackInterface ipAddress;

	public Server(String fPath){
		this.filePath = fPath;
		usersList = new ReferenceBasedList();
		this.generateIPs(); 
	}
	
	private void generateIPs(){
		ipAddress = new StackArrayBased();
		
		ipAddress.push("192.168.1.145");
		ipAddress.push("192.168.1.189");
		ipAddress.push("192.168.1.45");
		ipAddress.push("192.168.1.204");
		ipAddress.push("192.168.1.221");
	}
	
	private void generateDataStructure(ArrayList<String> lsta){
		
	    int pos = 1;
		for (int i=0;i<lsta.size();i = i+2){
			usersList.add(pos, new UserLaptop(lsta.get(i), lsta.get(i+1)));
			pos ++;
		}
	}
	
	private void updateDS(UserLaptop newUser){
		if(usersList.isEmpty()){
			usersList.add(1, newUser);
		}
		else if(newUser.compareTo(((UserLaptop)usersList.showLast()).getUsername()) == 1){
			usersList.add(usersList.size() + 1, newUser);
		}
		else {
			int k = usersList.size();
			while(newUser.compareTo(((UserLaptop)usersList.get(k)).getUsername()) != 1){
				k--;
				if(k==0){
					k++;
					break;
				}
			}		  
			usersList.add(k+1, newUser);
		}
	}

	public void startServer(){
		ArrayList<String> lst = new ArrayList<String>();
		lst = FileRead.readFromFile(filePath);
		try {
			generateDataStructure(lst);
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			System.out.println("No users");
		}
	}
	
	public String addUser(String name, String pass){
		UserLaptop nUser = new UserLaptop(name, pass);		
		if (this.getUser(name, pass) == null){
			updateDS(nUser);
			System.out.println("The user is added.");
			return "The user is added.";
		}
		else
			System.out.println("The user with the specific name or password already exists!");
			return "The user with the specific name or password already exists!";
	}
	
	public String removeUser(String name1, String pass1){		
		for(int i=1; i<=usersList.size(); i++){
			if(name1.equals(((UserLaptop)usersList.get(i)).getUsername()) && pass1.equals(((UserLaptop)usersList.get(i)).getPassword())){
				if(((UserLaptop)usersList.get(i)).getcLap() != null){
					ipAddress.push(((UserLaptop)usersList.get(i)).getcLap().getIpAddress());
				}				
				usersList.remove(i);
				System.out.println("The user was successfully removed!");
				return "The user was successfully removed!";
			}
		}
		System.out.println("The user with specific name and password does not exist.");
		return "The user with specific name and password does not exist.";
	}
		
	public String pingIpAddress(String ipAddress){
		for(int i=1; i<=usersList.size(); i++){
			if(((UserLaptop)usersList.get(i)).isConnected()){
				if(ipAddress.equals(((UserLaptop)usersList.get(i)).getcLap().getIpAddress())){
					//System.out.println("Sending Ping Request to " + ipAddress);
					return "Sending Ping Request to " + ipAddress;
				}
				else
					//System.out.println(ipAddress + "Sorry, but the user with the Ip Address provided is not connected to the network");
					return "Sorry, but the user with the Ip Address provided ("+ipAddress+") is not connected to the network";
			}
		}
		System.out.println("Sorry, but it looks like there are no users connected");
		return "Sorry, but it looks like there are no users connected"; 
	}
	
	public String pingHostName(String hostName){
		for(int i=1; i<=usersList.size(); i++){
			if(((UserLaptop)usersList.get(i)).isConnected()){
				if(hostName.equals(((UserLaptop)usersList.get(i)).getcLap().getHostName())){
					//System.out.println("Sending Ping Request to " + hostName); 
					return "Sending Ping Request to " + hostName;
				}
				else
					//System.out.println(hostName + " Sorry, but the user with host name provided is not connected to the network");
					return " Sorry, but the user with this host name ("+ hostName+") provided is not connected to the network";
			}	
		}
		//System.out.println("Sorry, but it looks like there are no users connected");
		return "Sorry, but it looks like there are no users connected";
	}
	
	public String connectToNetwork(String hName, String name, String pass){
		ComputerLaptop laptop = new ComputerLaptop(hName, (String)ipAddress.pop());
		if (this.getUser(name, pass) != null){
			((UserLaptop)getUser(name,pass)).connect(laptop);
			return "User succesfully connected!";
		}
		else
			return "Unable to connect! User already connected or invalid username or password!";		
	}
	public String disconnectFromNetwork(String hName,String name, String pass){
		for(int i=1; i<=usersList.size(); i++){
			if(name.equals(((UserLaptop)usersList.get(i)).getUsername()) && pass.equals(((UserLaptop)usersList.get(i)).getPassword()) && 
					hName.equals(((UserLaptop)usersList.get(i)).getcLap().getHostName())){
				ipAddress.push(((UserLaptop)usersList.get(i)).getcLap().getIpAddress());
				((UserLaptop)getUser(name,pass)).disconnectFromNetwork();				
				//System.out.println();
				return "The user is succesfully disconnected";
			}
		}
		return "Sorry, but the the user with that host name ,name and password does not exist.";
	}
	//do we need the method below???
	public void showUsers(){
		for (int j=1; j<=usersList.size(); j++)
			System.out.println("Location: " + j + "  Value: " + ((UserLaptop)usersList.get(j)).toString());
	}
	
	private Object getUser(String name, String pass){
		for (int j=1; j<=usersList.size(); j++){
			if ((((UserLaptop)usersList.get(j)).getUsername().equalsIgnoreCase(name)) &&
					(((UserLaptop)usersList.get(j)).getPassword().equalsIgnoreCase(pass))){
				return usersList.get(j);
			}		
		}
		return null;
	}
	
	public String[] showConnected(){
		String[] con = new String[5];
		int pos = 0;
		boolean any = false;
		for (int j=1; j<=usersList.size(); j++){
			if(((UserLaptop)usersList.get(j)).getcLap() != null){
				//System.out.println("Location: " + j + "  Value: " + ((UserLaptop)usersList.get(j)).toString());
				con[pos] += ((UserLaptop)usersList.get(j)).toString();
				pos++;
				any=true;
			}
		}
		/*if(!any){
			System.out.println("Looks like there are no computers connected!");
		}*/
		return con;
	}	
	
	public void closeSystem(){
		String serverData = "";
		for (int i=1; i<=usersList.size(); i++){
			serverData += ((UserLaptop)usersList.get(i)).getUsername() + "\n" + ((UserLaptop)usersList.get(i)).getPassword() + "\n";
			
		}
		FileWrite.writeToFile("src/inputFile.txt", serverData);
	}
}
