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
			s.sM = new SocketManager(s.getSs().accept());
			String message = s.sM.Leer();
			String passphrase = message.substring(0, message.indexOf(','));
			String counter = message.substring(message.indexOf(',')+1);
			HOTP hotp = new HOTP(HOTP.AlgorithmType.SHA1,9,passphrase.getBytes());
			s.sM.Escribir(hotp.generateHTOPPassword(Long.valueOf(counter))+"");
		}
	}
}
