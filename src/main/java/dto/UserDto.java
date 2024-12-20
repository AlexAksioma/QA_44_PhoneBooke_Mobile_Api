package dto;

import java.io.*;

public class UserDto implements Serializable{
    private static final long serialVersionUID = 6543456L;

    private String username;
    private String password;



    public UserDto(String username, String password) {
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

    public static void serializableUserDto(UserDto userDto, String fileName){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream("src/main/resources/serialazable_file/"+fileName));
            outputStream.writeObject(userDto);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static UserDto deSerializableUserDto(String fileName){
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream("src/main/resources/serialazable_file/"+fileName))){
            return (UserDto) inputStream.readObject();
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
