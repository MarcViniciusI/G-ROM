package Modelos;

public class ReturneInfo {
    private String characterName;
    private String lastLoginDate; // Adiciona a data do último login
    private String server; // Nova variável para o servidor
    
    public ReturneInfo(String characterName, String lastLoginDate, String server) {
        this.characterName = characterName;
        this.lastLoginDate = lastLoginDate;
        this.server = server;
    }

    // Getters e Setters
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

}
