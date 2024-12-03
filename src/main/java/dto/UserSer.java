package dto;

import java.io.*;

public class UserSer implements Serializable{
    @Serial
    private static final long serialVersionUID = 65434123L;

    public static int anInt;
    private String username;
    transient private String password;

    final String email = "name@mail.com";

    public UserSer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void serializableUser(UserSer userDto, String fileName){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream("src/main/resources/serialazable_file/"+fileName));
            outputStream.writeObject(userDto);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static UserSer deSerializableUser(String fileName){
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream("src/main/resources/serialazable_file/"+fileName))){
            return (UserSer) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
