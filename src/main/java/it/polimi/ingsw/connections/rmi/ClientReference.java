package it.polimi.ingsw.connections.rmi;

public class ClientReference {

    private String username;
    private Callback clientCallback;

    public ClientReference(String username, Callback clientCallback) {
        this.username = username;
        this.clientCallback = clientCallback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Callback getClientCallback() {
        return clientCallback;
    }

    public void setClientCallback(Callback clientCallback) {
        this.clientCallback = clientCallback;
    }
}
