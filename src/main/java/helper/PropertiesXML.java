package helper;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesXML {
    public static void main(String[] args) {
        setPropertyXML("name", "Bob1", true, "prop1.xml");
        setPropertyXML("lastName", "Dou1", false, "prop1.xml");
        setPropertyXML("email", "Dou1@mail.com", false, "prop1.xml");
        System.out.println("--> "+getProperty("email", "prop1.xml"));
    }

    public static void setPropertyXML(String key, String value, boolean clearFile, String fileName) {
        Properties properties = new Properties();
        if (!clearFile) {
            try (FileInputStream fileInputStream =
                         new FileInputStream("src/test/resources/properties_xml/" + fileName)) {
                properties.loadFromXML(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties.setProperty(key, value);
        try (FileOutputStream fileOutputStream =
                     new FileOutputStream("src/test/resources/properties_xml/" + fileName)) {
            properties.storeToXML(fileOutputStream, "comment");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key, String fileName){
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream("src/test/resources/properties_xml/" + fileName)){
            properties.loadFromXML(fileInputStream);
            return properties.getProperty(key);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
