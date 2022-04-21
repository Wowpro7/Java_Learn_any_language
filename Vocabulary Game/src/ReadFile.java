import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private File ReadWords;
    private File ReadTranslation;
    private Scanner ScanWords;
    private Scanner ScanTranslation;
    private ArrayList<String> Words = new ArrayList();
    private ArrayList<String> Translation = new ArrayList();
    boolean FileExist = true;
    ReadFile(String WordsFile, String TranslationsFile) {//initializing variables in the constructor
        try {//in case of no files being found
            this.ReadTranslation = new File(TranslationsFile);
            this.ReadWords = new File(WordsFile);
            this.ScanTranslation = new Scanner(ReadTranslation);
            this.ScanWords = new Scanner(ReadWords);
            FileExist=true;
        }
        catch(Exception exception){//in case of no files being found
            System.out.println("No Files Were Found");
            FileExist=false;//File doesn't exist indicator
        }
    }
    ArrayList<String> ReadWords(){//providing Words from the file
        if(FileExist)//if the files exist fitch the words from it
            while(ScanWords.hasNextLine())//looking if there are more lines to read from
                this.Words.add(ScanWords.nextLine());//saving the data from these lines

        return this.Words;
    }
    ArrayList<String> ReadTranslation(){//providing Translation from a different file
        if(FileExist)//if the files exist fitch the words from it
            while(ScanTranslation.hasNextLine())//looking if there are more lines to read from
                this.Translation.add(ScanTranslation.nextLine());//saving the data from these lines

        return Translation;
    }

}

