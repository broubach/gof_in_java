package behavioral.state;

import java.io.OutputStream;

class TCPConnection {
	private TCPState state = TCPClosed.getInstance();

	public void activeOpen() {
		state.activeOpen(this);
	}

	public void passiveOpen() {
		state.passiveOpen(this);
	}

	public void close() {
		state.close(this);
	}

	public void ackowledge() {
		state.acknowledge(this);
	}

	public void synchronize() {
		state.acknowledge(this);
	}

	public void changeState(TCPState state) {
		this.state = state;
	}

	public void processStream(OutputStream stream) {
		
	}
	
	public String toString() {
		return state.toString();
	}
}

class TCPState {

	public void transmit(TCPConnection connection, OutputStream stream) {}
	public void activeOpen(TCPConnection connection) {}
	public void passiveOpen(TCPConnection connection) {}
	public void close(TCPConnection connection) {}
	public void synchronize(TCPConnection connetion) {}
	public void acknowledge(TCPConnection connection) {}
	public void send(TCPConnection connection) {}
	
	public void changeState(TCPConnection connection, TCPState state) {
		connection.changeState(state);
	}
}

class TCPEstablished extends TCPState {
	private static TCPEstablished instance;

	public static TCPEstablished getInstance() {
		if (instance == null) {
			instance = new TCPEstablished();
		}
		return instance;
	}
	
	public void transmit(TCPConnection connection, OutputStream stream) {
		connection.processStream(stream);
	}

	public void close(TCPConnection connection) {
		changeState(connection, TCPListen.getInstance());
	}

	public String toString() {
		return "TCPEstablished";
	}
}

class TCPListen extends TCPState {
	private static TCPListen instance;
	
	public static TCPState getInstance() {
		if (instance == null) {
			instance = new TCPListen();
		}
		return instance;
	}

	public void send(TCPConnection connection) {
		changeState(connection, TCPEstablished.getInstance());
	}

	public String toString() {
		return "TCPListen";
	}
}

class TCPClosed extends TCPState {
	private static TCPClosed instance;

	public static TCPState getInstance() {
		if (instance == null) {
			instance = new TCPClosed();
		}
		return instance;
	}

	public void activeOpen(TCPConnection connection) {
		changeState(connection, TCPEstablished.getInstance());
	}
	
	public void passiveOpen(TCPConnection connection) {
		changeState(connection, TCPListen.getInstance());
	}
	
	public String toString() {
		return "TCPClosed";
	}
}

public class Client {

	public static void main(String[] args) {
		TCPConnection connection = new TCPConnection();
		System.out.println("Connection is: " + connection);
		
		connection.activeOpen();
		System.out.println("Connection is: " + connection);
		
		connection.close();
		System.out.println("Connection is: " + connection);
	}
}
