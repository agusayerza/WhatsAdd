import javax.xml.bind.DatatypeConverter;
import java.io.*;

public class ImageSaver {

    public static String saveFile(String folder,String prefix,  String base64String) {
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String name = prefix + System.currentTimeMillis() + "." + extension;
        String path = folder + "/" + name;
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStream outputStream = new BufferedOutputStream(fileOutputStream);
            outputStream.write(data);
            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
        return name;
    }
}
