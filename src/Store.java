import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Store {
    private String dumLocation;

    public Store(String dumLocation, String[] files) throws FileNotFoundException, IOException {
        this.dumLocation = dumLocation;

        Dum dum = new Dum();

        for (String file : files) {
            dum.addData(file, readFile(file));
        }
        dum.storeImage(dum.encode(), "dum.png");
    }

    public static String readFile(String filePath) throws FileNotFoundException, IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

}
