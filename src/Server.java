import java.io.IOException;
import java.net.ServerSocket;



public class Server {
	
	private ServerSocket ss;
	private boolean terminar;
	private SocketManager sM;

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

	public SocketManager getsM() {
		return sM;
	}

	public void setsM(SocketManager sM) {
		this.sM = sM;
	}

	public static void main(String[] args) throws IOException{	
		Server s = new Server(8080);
		while (!s.isTerminar()) {		
			System.out.println("Hola");
			s.setsM(new SocketManager(s.getSs().accept()));
			//sM.Leer();
		}
	}
}
