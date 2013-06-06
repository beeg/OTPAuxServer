import java.io.IOException;
import java.net.ServerSocket;



public class Server {
	
	private static ServerSocket ss;
	private static boolean terminar;
	private static SocketManager sM;

	public Server(int puerto) {
		terminar = false;	
		try {
			ss = new ServerSocket(puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerSocket getSs() {
		return ss;
	}

	public void setSs(ServerSocket ss) {
		this.ss = ss;
	}

	public boolean isTerminar() {
		return terminar;
	}

	public void setTerminar(boolean terminar) {
		this.terminar = terminar;
	}

	public static SocketManager getsM() {
		return sM;
	}

	public static void setsM(SocketManager sM) {
		Server.sM = sM;
	}

	public static void main(String[] args) throws IOException{	
		Server s = new Server(8080);
		while (!s.isTerminar()) {		
			s.setsM(new SocketManager(ss.accept()));
			//sM.Leer();
		}
	}
}
