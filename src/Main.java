
public class Main {
    public static void main(String[] args) throws Exception {
        new Store("dum.dum", new String[] {"/home/syntax/Programs/dum compression/src/crazy.txt"}); // Store the dum image
        System.out.println("ENCODING FINISHED");
        String decoded_data = new Dum().decode("dum.png");
        System.out.println("DECODED DATA: " + decoded_data);
    }
}
