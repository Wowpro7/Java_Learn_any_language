import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * /**
 * /**
 * /**
 * /**
 */

public class App2 {
    private JTextArea addNewWord;
    private JTextArea addNewTranslation;
    private JPanel Jpanelmain;
    private JButton addButton;
    private JList translationList;
    private JList justAddedList;
    DefaultListModel wordsListDLM = new DefaultListModel();
    DefaultListModel justAddedListDLM = new DefaultListModel();

    String Translation = "Translation.txt";
    String Words = "Words.txt";
    ReadFile ReadFiles = new ReadFile(Words, Translation);//getting access to the ReadFile class
    WriteToFile Write = new WriteToFile(Words, Translation);//getting access to the WriteFile class
    ArrayList<String> ArWord =  ReadFiles.ReadWords();//getting the needed information through the declared access
    ArrayList<String> ArTranslation = ReadFiles.ReadTranslation();
    ArrayList<String> ArJustAddedWords = new ArrayList();
    boolean bJustAdded=false;
    private boolean unknownWord=true;
    int countWordsList=0;
    int countJustAdded=0;
    String sWords;
    String sTranslation;
    String newWord;
    String newTranslation;
    String[] sSplit;

    public  App2()
    {

       // ArJustAddedWords.add(" ");//to prevent size errors
        initializer();

        /**
         * Add button
         */
        addButton.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int keyCode = e.getKeyCode();
            if(keyCode== KeyEvent.VK_ENTER){
                addNewWord();
            }

        }
         });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addNewWord();
            }
        });
        addNewTranslation.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if(keyCode== KeyEvent.VK_ENTER){
                    addNewWord();
                    }

                    }
        });
    }

    /**
     * initialize
     */
    public void initializer(){
        addNewWord.setText("");
        addNewTranslation.setText("");
        wordsListDLM.clear();
        countWordsList=0;
        for(;countWordsList<ArWord.size();countWordsList++) {
            sWords= ArWord.get(countWordsList);
            sTranslation=ArTranslation.get(countWordsList);
            wordsListDLM.addElement(countWordsList+1 + ". " + sWords + " - " + sTranslation);
        }
        translationList.setVisibleRowCount(10);
        translationList.setModel(wordsListDLM);
        justAddedList.setVisibleRowCount(10);
        justAddedList.setModel(justAddedListDLM);
    }
    /**
     * Add new word
     */
    public void addNewWord(){
        newWord = addNewWord.getText().trim();
        newTranslation = addNewTranslation.getText().trim();

        if (newWord.equals("") || newTranslation.equals(""))
        {//preventing empty space in source
            JOptionPane.showMessageDialog(null, "You Left an Empty Text Area");
        }
        else {
            for (int j = 0; j < ArJustAddedWords.size(); j++) {
                if (ArJustAddedWords.get(j).equals(newWord))
                    bJustAdded = true;
            }
            if (bJustAdded) {
                JOptionPane.showMessageDialog(null, "You Just Added This Word");
                bJustAdded = false;
            } else {
                for (int g = 0; g < ArWord.size(); g++) { //checking the existence of the new word in the "Words" file
                    sSplit=ArWord.get(g).split(",");
                    for(int h=0;h<sSplit.length;h++)
                        if (newWord.equals(sSplit[h])) {//comparing with the Words Array
                            unknownWord = false;
                            break;
                        }
                }
                if (unknownWord == true) {//if unknown word then its being added to the list

                    if ((!findForbiddenSymbol(newWord)) && (!findForbiddenSymbol(newTranslation))) {
                        countJustAdded++;
                        //checks for forbidden symbols before continuing
                        //countWordsList++;//counting words
                        ArJustAddedWords.add(newWord);//add word
                        ArWord.add(newWord);
                        ArTranslation.add(newTranslation);
                        Write.AddNewWord(newWord);//trim(); removes white space
                        Write.AddNewTranslation(newTranslation);//trim(); removes white space
                        justAddedListDLM.addElement(countJustAdded +". "+newWord+ " - "+newTranslation);
                        initializer();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"You entered a forbidden symbol");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "The word in already in the system");
                }
                unknownWord = true;
            }
        }
    }
    /**
     * check for forbidden symbols
     */
    public boolean findForbiddenSymbol(String string){
        Pattern p = Pattern.compile("[^a-zA-Zא-תа-яА-Я', ]");
        Matcher m = p.matcher(string);
        boolean b= m.find();
        return b;
    }


    public static void main(String[] args) {
        ImageIcon iconICON = new ImageIcon(App.class.getResource("icon.png"));
        JFrame frame = new JFrame("Add New Words");
        frame.setIconImage(iconICON.getImage());
        frame.setContentPane(new App2().Jpanelmain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
