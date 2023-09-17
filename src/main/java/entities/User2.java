package entities;

public class User2 {
    private Integer Id;
    private String chatId;
    private String userName;
    private String email;

    public User2(Integer id, String chatId, String userName, String email) {
        Id = id;
        this.chatId = chatId;
        this.userName = userName;
        this.email = email;

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User2{" +
                "Id=" + Id +
                ", chatId='" + chatId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
