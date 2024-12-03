package serialazable_tests;

import dto.UserDto;
import dto.UserSer;
import org.testng.annotations.Test;
import static dto.UserDto.*;

import static dto.UserSer.*;

public class SerialazableTest {

    @Test
    public void serialTest(){
        UserDto user = new UserDto("Bob", "Family");
        serializableUserDto(user, "user1.ser");
        System.out.println(deSerializableUserDto("user1.ser").toString());
    }

    @Test
    public void serialTest1(){
        UserSer user = new UserSer("Bob1", "Family1");
        anInt = 100;
        serializableUser(user, "user2.ser");
        anInt = 200;
        System.out.println(deSerializableUser("user2.ser").toString());
        System.out.println(anInt);
    }

    @Test
    public void serialTest3(){
        UserSer user = new UserSer("Bob1", "Family1");
        serializableUser(user, "user3.ser");
        System.out.println(deSerializableUser("user3.ser").toString());

    }


}
