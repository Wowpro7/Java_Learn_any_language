import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteToFile {
    private File fileToWords;
    private FileWriter fileWriterWords;
    private BufferedWriter bufferWords;
    private PrintWriter printWriterWords;

    private File fileToTrans;
    private FileWriter fileWriterTrans;
    private BufferedWriter bufferTrans;
    private PrintWriter printWriterTrans;
    // private ReadFile checkWordExistence;

    WriteToFile(String WordsFile, String TranslationsFile){
        try{
            this.fileToWords = new File(WordsFile);
            this.fileWriterWords  = new FileWriter(fileToWords,true);
            this.bufferWords = new BufferedWriter(fileWriterWords);
            this.printWriterWords = new PrintWriter(bufferWords);

            this.fileToTrans = new File(TranslationsFile);
            this.fileWriterTrans  = new FileWriter(fileToTrans,true);
            this.bufferTrans = new BufferedWriter(fileWriterTrans);
            this.printWriterTrans = new PrintWriter(bufferTrans);
        }
        catch (Exception exception){
            System.out.println("No File Was Found");
        }
    }
    public void AddNewWord(String newWord){
        printWriterWords.println(newWord);
        printWriterWords.flush();//uploads the word into the file immediately
        System.out.println("'" + newWord + "' has been added to the 'Words' list \n now add the Translation" );
        // printWriterWords.close();//uploads the word into the file after closing line
    }
    public void AddNewTranslation(String newTranslation){
        printWriterTrans.println(newTranslation);
        printWriterTrans.flush();//uploads the word into the file immediately
        System.out.println("'" + newTranslation +"' has been added to the 'Translation' list \n " );
        //  printWriterTrans.close();//uploads the word into the file after closing line
    }

}
