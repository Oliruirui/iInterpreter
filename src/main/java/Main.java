import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Main {
    public static void main(String[] args) {

        Main tester = new Main();
        try{
            List<String> res = tester.readFile("myFirstOLanguageFile.oli");
            System.out.println(res);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }



    /**
     * @param input cannot be null
     * @return
     * @throws FileNotFoundException
     */
    public List<String> readFile(String input) throws FileNotFoundException{
        List<String> stringList = new ArrayList<>();
        try {
            File myFile = new File(input);
            Scanner mySc = new Scanner(myFile);

            while (mySc.hasNext()){
                stringList.add(mySc.nextLine());
            }
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("File Not Found");
        }
        return stringList;
    }
}
