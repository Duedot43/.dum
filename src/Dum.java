
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.awt.Color;

public class Dum {
    private BufferedImage image; // The dum image
    public String chars; // The dum chars


    public Dum(String chars) {
        this.chars = base64_encode(chars);
        
    }

    
    public Dum() {
        this.chars = base64_encode("DUM,000");
        
    }


    public BufferedImage encode() {
        int[] charArray = new int[this.chars.length()];
        for (int i=0; i<this.chars.length(); i++) {
            charArray[i] = this.chars.charAt(i);
        }



        int[][] charColor = new int[((int) charArray.length / 3) + (charArray.length % 3)][3];
        int charCount = 0;
        int charCount2 = 0;
        for (int i=0; i<charArray.length + ((int) charArray.length % 3); i+=3) {
            for (int j=i; j<i+3; j++) {
                try {
                    charColor[charCount][charCount2] = charArray[j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    charColor[charCount][charCount2] = 0;
                }
                charCount2++;
            }
            charCount2 = 0;
            charCount++;
        }   



        int[] rez = calcRez(charColor.length); // Calculate the resolution
        this.image = new BufferedImage(rez[0], rez[1], BufferedImage.TYPE_INT_RGB); // Make the image



        charCount = 0;
        for (int y=0; y<rez[0]; y++) {
            for (int x=0; x<rez[0]; x++) {
                if (charCount < charColor.length) {
                    Color color = new Color(charColor[charCount][0], charColor[charCount][1], charColor[charCount][2]); // Goofy ahh solution
                    this.image.setRGB(x, y, color.getRGB());
                    charCount++;
                }
            }
        }

        return image;
    }


    public String decode(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return null;
        }
        int y = this.image.getHeight();
        int x = this.image.getWidth();
        String result = "";

        for (int i=0; i<y; i++) {
            for (int j=0; j<x; j++) {
                Color color = new Color(this.image.getRGB(j, i));
                result += (char) color.getRed();
                result += (char) color.getGreen();
                result += (char) color.getBlue();
            }
        }
        
        for (int i=0; i<result.length(); i++) {
            if (result.charAt(i) == 0) {
                result = result.substring(0, i);
                break;
            }
        }

        return base64_decode(result);
    }


    public void addData(String name, String data) {
        ArrayList<String> chars = getChars();
        chars.add(name + ":" + data);
        setChars(chars);
    }


    public void appendData(String data) {
        chars = base64_decode(this.chars);
        chars += data;
        this.chars = base64_encode(chars);
    }


    public ArrayList<String> getChars() {
        String[] data = base64_decode(this.chars).split(",");
        ArrayList<String> output = new ArrayList<String>(Arrays.asList(data));
        return output;
    }

    public void setChars(ArrayList<String> chars) {
        String output = "";
        for (int i=0; i<chars.size(); i++) {
            output += chars.get(i);
            if (i != chars.size() - 1) {
                output += ",";
            }
        }

        this.chars = base64_encode(output);
    }


    public int[] calcRez(int chars) {
        return new int[] {(int) Math.ceil(Math.sqrt(chars)), (int) Math.ceil(Math.sqrt(chars))};
    }


    public void storeImage(BufferedImage image, String path) {
        try {
            File outputfile = new File(path);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    public String base64_encode(String string) {
        return java.util.Base64.getEncoder().withoutPadding().encodeToString(string.getBytes());
    }

    public String base64_decode(String string) {
        return new String(java.util.Base64.getDecoder().decode(string));
    }
}
