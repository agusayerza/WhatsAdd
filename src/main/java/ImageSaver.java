import javax.xml.bind.DatatypeConverter;
import java.io.*;

class ImageSaver {

    static String saveFile(String folder, String prefix, String base64String) {
        String[] strings = base64String.split(",");
        String extension;
        //check image's extension
        //should write cases for more images types
        if ("data:image/png;base64".equals(strings[0])) {
            extension = "png";
        } else {
            extension = "jpg";
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String name = prefix + System.currentTimeMillis() + "." + extension;
        String path = folder + File.separator + name;
        File file = new File(path);

        if(!new File(folder).exists()){
            if(!new File(folder).mkdirs()){
                throw new RuntimeException("Couldn't create path to save images: " + folder);
            }
        }

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
