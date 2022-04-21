

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;


public class App {
    private JButton compareAnswerButton;
    private JPanel panelMain;
    private JTextArea getNewWordTextArea;
    private JTextArea userWordTextArea;
    private JButton getNewWordButton;
    private JButton addButton;
    private JTextArea ScoreBoardTextArea;
    private JButton clearScoreButton;
    private JTextArea correctAnswerTextArea;
    private JTextArea answerStatusTextArea;
    private JLabel correctAnswerLabel;
    private JButton switchButton;

    ImageIcon iconCongradultions = new ImageIcon(App.class.getResource("congradultions_icon.jpg"));
    ImageIcon iconTryAgain = new ImageIcon((App.class.getResource("atleast_youtried.jpg")));
    ImageIcon iconGoodJob = new ImageIcon((App.class.getResource("great-job-lets.jpg")));

    String Translation = "Translation.txt";
    String Words = "Words.txt";
    ReadFile ReadFiles = new ReadFile(Words, Translation);//getting access to the ReadFile class
    ArrayList<String> ArWord =  ReadFiles.ReadWords();//getting the needed information through the declared access
    ArrayList<String> ArTranslation =  ReadFiles.ReadTranslation();//getting the needed information through the declared access
    ArrayList<String> ArUsedRandom = new ArrayList<>();
    String preventSpaceEnter;
    String sWordForDisplay;
    int CountCorrect;
    int CountWrong;
    int CountWords;
    int countHowManyTimesFinishedVocab;
    int iFirstRandomNumber;
    int iSecondRandomNumber;
    boolean gotNewWord=false;
    boolean bSwitchMode= false;
    App2 app2=new App2();

    public App(){
        initializer();

        /**
         *
         * Compare Results
         * */
        compareAnswerButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                compareResults();
            }
        }
        );
        userWordTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if(keyCode== KeyEvent.VK_ENTER){
                    //JOptionPane.showMessageDialog(null,"ENTER");
                    compareResults();
                }
                else if(keyCode== KeyEvent.VK_F1){
                    getNewWord();
                }
            }
        });


        /**
         * GET a new word
         * */
        getNewWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getNewWord();

            }
        });

        getNewWordButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if(keyCode== KeyEvent.VK_F1){
                    getNewWord();
                }
            }
        });


        /*******
         ADD new words
         *******/
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializer();
                app2.main(null);
            }
        });

        /**
         * clears score board
         */
        clearScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArUsedRandom.clear();
                CountWords=0;
                CountCorrect=0;
                CountWrong=0;
                countHowManyTimesFinishedVocab=0;
                setScoreLadder();

            }
        });

        /**
         * changes word and translation place - new mode
         * SWITCH
         */
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String change;
                bSwitchMode=!bSwitchMode;
                change=getNewWordTextArea.getText();
                getNewWordTextArea.setText(userWordTextArea.getText());
                userWordTextArea.setText(change);
                ArUsedRandom.clear();
                JOptionPane.showMessageDialog(null,"You Switched Sides");
            }
        });



    }
    /**
     * comparing results
     */
    public  void compareResults( ){
        if (gotNewWord) {
            correctAnswerLabel.setVisible(false);

            preventSpaceEnter = userWordTextArea.getText();//.toLowerCase();
            preventSpaceEnter = preventSpaceEnter.trim();//removing white space
            if(!findForbiddenSymbol(preventSpaceEnter)) {
                if (preventSpaceEnter.equals("")) {//preventing empty space in source
                    JOptionPane.showMessageDialog(null, "You Left an Empty Text Area");
                }
                else
                {
                    gotNewWord = false;
                    CountWords++;
                    String translateWord;//Array value
                    String[] sSplit;//splits array value
                    if (!bSwitchMode) {
                        sSplit = ArTranslation.get(iFirstRandomNumber).split(",".trim());
                        translateWord = ArTranslation.get(iFirstRandomNumber);
                    } else {
                        sSplit = ArWord.get(iFirstRandomNumber).split(",".trim());
                        translateWord = ArWord.get(iFirstRandomNumber);
                    }
                    // if (preventSpaceEnter.equals(translateWord)) {
                    if (Arrays.asList(sSplit).contains(preventSpaceEnter)) {//compares userword with sSplit values
                        CountCorrect++;
                        ArUsedRandom.add(sWordForDisplay);
                        correctAnswerTextArea.setText("");
                        answerStatusTextArea.setText("Correct");
                        answerStatusTextArea.setBackground(Color.GREEN);
                        JOptionPane.showMessageDialog(null, null,null,JOptionPane.INFORMATION_MESSAGE,iconGoodJob);
                    } else {
                        CountWrong++;
                        correctAnswerLabel.setVisible(true);
                        correctAnswerTextArea.setText(translateWord);
                        answerStatusTextArea.setText("Wrong");
                        answerStatusTextArea.setBackground(Color.pink);
                        JOptionPane.showMessageDialog(null, null,null,JOptionPane.INFORMATION_MESSAGE,iconTryAgain);
                    }
                    setScoreLadder();
                 }
            }
            else {
                JOptionPane.showMessageDialog(null,"You entered a forbidden symbol");
            }

        }
        else {
            JOptionPane.showMessageDialog(null, "First Get a New Word");
        }

    }
    /**
     * get a new word action performance
     */
    public void getNewWord(){
        int i=0;
        int iHowManyWords=HowManyWords();
        gotNewWord = true;
        initializer();
        sWordForDisplay=newWord();
        for(i=0;i<ArUsedRandom.size();i++){
            if(ArUsedRandom.size()==iHowManyWords){
                countHowManyTimesFinishedVocab=countHowManyTimesFinishedVocab+1;
                setScoreLadder();
                JOptionPane.showMessageDialog(null,"Congratulations, you finished your whole vocabulary correctly ",null,JOptionPane.INFORMATION_MESSAGE,iconCongradultions);
                ArUsedRandom.clear();
            }
            else if(ArUsedRandom.get(i).equals(sWordForDisplay)) {
                sWordForDisplay = newWord();
                i=0;
            }
        }
        getNewWordTextArea.append(sWordForDisplay);

    }

    /**
     * get a new word - function
     */
    public String newWord(){
        String[] sSecondWord;
        iFirstRandomNumber=doRandom(ArWord.size());//having a random number
        if (!bSwitchMode)
            sSecondWord=(ArWord.get(iFirstRandomNumber).split(","));
        else
            sSecondWord=ArTranslation.get(iFirstRandomNumber).split(",");
        iSecondRandomNumber= doRandom(sSecondWord.length);

        return sSecondWord[iSecondRandomNumber];
    }

    /**
     * counting how many words at in the file
     * @return
     */
    public int HowManyWords(){
        String [] sSplitForCounting;
        int i=0;
        int iTotalAmountOfWords=0;
        if(bSwitchMode==false)
            for(;i<ArWord.size();i++){
                sSplitForCounting=ArWord.get(i).split(",");
                iTotalAmountOfWords+=sSplitForCounting.length;
            }
        else
            for(;i<ArTranslation.size();i++){
                sSplitForCounting=ArTranslation.get(i).split(",");
                iTotalAmountOfWords+=sSplitForCounting.length;
            }
        return iTotalAmountOfWords;
    }

    /**
     * control score board
     */
    public void setScoreLadder() {
        ScoreBoardTextArea.setText("\n\n You tried " + CountWords+" words \n\n" +
                " You succeed at "+CountCorrect+" words\n\n" +
                " You finished your whole vocabulary " + countHowManyTimesFinishedVocab+ " Times" );
    }

    /**
     * initialize
     */
    public  void initializer(){
        setScoreLadder();
        answerStatusTextArea.setBackground(null);//removes Backgound color
        answerStatusTextArea.setText("      --------");
        correctAnswerLabel.setVisible(false);
        getNewWordTextArea.setText("");
        userWordTextArea.setText("");
        correctAnswerTextArea.setText("");
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

    /**
     * do random
     * @param maxValue
     * @return
     */
    public int doRandom(int maxValue){
        maxValue=(int) (Math.random()*maxValue);
        return maxValue;
    }
    public static void main(String[] args) {
        ImageIcon iconICON = new ImageIcon(App.class.getResource("icon.png"));
        JFrame frame = new JFrame("Study Anything");
        frame.setIconImage(iconICON.getImage());
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}











