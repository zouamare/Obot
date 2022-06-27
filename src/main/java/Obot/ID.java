package Obot;

public class ID {
    private String userID;
    private String serverID;

    public ID(String userID, String serverID){
        this.userID = userID;
        this.serverID = serverID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }
}
