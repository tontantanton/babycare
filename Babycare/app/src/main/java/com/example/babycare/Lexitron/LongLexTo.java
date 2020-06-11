package com.example.babycare.Lexitron;
import java.io.*;
import java.util.*;

public class LongLexTo {


  private Trie dict;
  private LongParseTree ptree;


  private Vector indexList;
  private Vector lineList;
  private Vector typeList;
  private Iterator iter;

  /*******************************************************************/
  /*********************** Return index list *************************/
  /*******************************************************************/
  public Vector getIndexList() {
    return indexList; }

  /*******************************************************************/
  /*********************** Return type list *************************/
  /*******************************************************************/
  public Vector getTypeList() {
    return typeList; }
    
  /*******************************************************************/
  /******************** Iterator for index list **********************/
  /*******************************************************************/
  //Return iterator's hasNext for index list 
  public boolean hasNext() {
    if(!iter.hasNext())
      return false;
    return true;
  }
  
  //Return iterator's first index
  public int first() {
    return 0;
  }
  
  //Return iterator's next index
  public int next() {
    return((Integer)iter.next()).intValue();
  }
  
  /*******************************************************************/
  /********************** Constructor (default) **********************/
  /*******************************************************************/
  public LongLexTo() throws IOException {
    
    dict=new Trie();
    File dictFile=new File("lexitron.txt");
    if(dictFile.exists())
      addDict(dictFile);
    else
      System.out.println(" !!! Error: Missing default dictionary file, lexitron.txt");
    indexList=new Vector();
    lineList=new Vector();
    typeList=new Vector();
    ptree=new LongParseTree(dict, indexList, typeList);
  } //Constructor
    
  /*******************************************************************/
  /************** Constructor (passing dictionary file ) *************/
  /*******************************************************************/
  public LongLexTo(File dictFile) throws IOException {

    dict=new Trie();
    if(dictFile.exists())
      addDict(dictFile);
    else
      System.out.println(" !!! Error: The dictionary file is not found, " + dictFile.getName());
    indexList=new Vector();
    lineList=new Vector();
    typeList=new Vector();
    ptree=new LongParseTree(dict, indexList, typeList);
  } //Constructor
  
  /*******************************************************************/
  /**************************** addDict ******************************/
  /*******************************************************************/
  public void addDict(File dictFile) throws IOException {
   
    //Read words from dictionary
    String line, word, word2;
    int index;
    FileReader fr = new FileReader(dictFile);
    BufferedReader br = new BufferedReader(fr);
    
    while((line=br.readLine())!=null) {
      line=line.trim();
      if(line.length()>0)
        dict.add(line);
    }
  } //addDict
  
  /****************************************************************/
  /************************** wordInstance ************************/
  /****************************************************************/
  public void wordInstance(String text) {

    indexList.clear();
    typeList.clear();    
    int pos, index;
    String word;
    boolean found;
    char ch;

    pos=0;
    while(pos<text.length()) {

      //Check for special characters and English words/numbers
      ch=text.charAt(pos);

      //English
      if(((ch>='A')&&(ch<='Z'))||((ch>='a')&&(ch<='z'))) {
        while((pos<text.length())&&(((ch>='A')&&(ch<='Z'))||((ch>='a')&&(ch<='z'))))
          ch=text.charAt(pos++);
        if(pos<text.length())
          pos--;
        indexList.addElement(new Integer(pos));
        typeList.addElement(new Integer(3));
      }
      //Digits
      else if(((ch>='0')&&(ch<='9'))||((ch>='ð')&&(ch<='ù'))) {
        while((pos<text.length())&&(((ch>='0')&&(ch<='9'))||((ch>='ð')&&(ch<='ù'))||(ch==',')||(ch=='.')))
          ch=text.charAt(pos++);
        if(pos<text.length())
          pos--;
        indexList.addElement(new Integer(pos));
        typeList.addElement(new Integer(3));
      }
      //Special characters
      else if((ch<='~')||(ch=='æ')||(ch=='Ï')||(ch=='“')||(ch=='”')||(ch==',')) {
        pos++;
        indexList.addElement(new Integer(pos));
        typeList.addElement(new Integer(4));
      }
      //Thai word (known/unknown/ambiguous)
      else
        pos=ptree.parseWordInstance(pos, text);
    } //While all text length
    iter=indexList.iterator();
  } //wordInstance
        
  /****************************************************************/
  /************************** lineInstance ************************/
  /****************************************************************/
  public void lineInstance(String text) {

    int windowSize=10; //for detecting parentheses, quotes
    int curType, nextType, tempType, curIndex, nextIndex, tempIndex;
    lineList.clear();
    wordInstance(text);
    int i;
    for(i=0; i<typeList.size()-1; i++) {
      curType=((Integer)typeList.elementAt(i)).intValue();
      curIndex=((Integer)indexList.elementAt(i)).intValue();

      if((curType==3)||(curType==4)) {
    	//Parenthesese
    	if((curType==4)&&(text.charAt(curIndex-1)=='(')) {
          int pos=i+1;
          while((pos<typeList.size())&&(pos<i+windowSize)) {
	    tempType=((Integer)typeList.elementAt(pos)).intValue();
    	    tempIndex=((Integer)indexList.elementAt(pos++)).intValue();  
 	    if((tempType==4)&&(text.charAt(tempIndex-1)==')')) {
    	      lineList.addElement(new Integer(tempIndex));
    	      i=pos-1;
              break;
    	    }
    	  }
        }    	  
        //Single quote
    	else if((curType==4)&&(text.charAt(curIndex-1)=='\'')) {
          int pos=i+1;
          while((pos<typeList.size())&&(pos<i+windowSize)) {
	    tempType=((Integer)typeList.elementAt(pos)).intValue();
    	    tempIndex=((Integer)indexList.elementAt(pos++)).intValue();  
 	    if((tempType==4)&&(text.charAt(tempIndex-1)=='\'')) {
    	      lineList.addElement(new Integer(tempIndex));
    	      i=pos-1;
              break;
    	    }
    	  } 	    
    	}
    	//Double quote
    	else if((curType==4)&&(text.charAt(curIndex-1)=='\"')) {
          int pos=i+1;
          while((pos<typeList.size())&&(pos<i+windowSize)) {
	    tempType=((Integer)typeList.elementAt(pos)).intValue();
    	    tempIndex=((Integer)indexList.elementAt(pos++)).intValue();  
 	    if((tempType==4)&&(text.charAt(tempIndex-1)=='\"')) {
    	      lineList.addElement(new Integer(tempIndex));
    	      i=pos-1;
              break;
    	    }
    	  } 	    
    	}    	  
        else
          lineList.addElement(new Integer(curIndex));
      }
      else {
        nextType=((Integer)typeList.elementAt(i+1)).intValue();
        nextIndex=((Integer)indexList.elementAt(i+1)).intValue();
        if((nextType==3)||
          ((nextType==4)&&((text.charAt(nextIndex-1)==' ')||(text.charAt(nextIndex-1)=='\"')||
                           (text.charAt(nextIndex-1)=='(')||(text.charAt(nextIndex-1)=='\''))))
          lineList.addElement(new Integer(((Integer)indexList.elementAt(i)).intValue()));
        else if((curType==1)&&(nextType!=0)&&(nextType!=4))
          lineList.addElement(new Integer(((Integer)indexList.elementAt(i)).intValue()));
      }
    }
    if(i<typeList.size())
      lineList.addElement(new Integer(((Integer)indexList.elementAt(indexList.size()-1)).intValue()));
    iter=lineList.iterator(); 
  } //lineInstance
  
  /****************************************************************/
  /*************************** Demo *******************************/
  /****************************************************************/
  public static void main(String[] args) throws IOException {
     
    LongLexTo tokenizer=new LongLexTo(new File("lexitron.txt"));
    File unknownFile=new File("unknown.txt");
    if(unknownFile.exists())
      tokenizer.addDict(unknownFile);
    Vector typeList;
    String text="", line, inFileName, outFileName;
    char ch;
    int begin, end, type; 
 
    File inFile, outFile;
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
 
    BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in)); 
    
    System.out.println("\n\n*******************************");
    System.out.println("*** LexTo: Lexeme Tokenizer ***");
    System.out.println("*******************************");
    do {      
      //Get input file name
      do {
      	System.out.print("\n >>> Enter input file ('q' to quit): ");
        inFileName=(streamReader.readLine()).trim();
        if(inFileName.equals("q"))
          System.exit(1);
        inFile=new File(System.getProperty("user.dir") + "//" + inFileName);
      } while(!inFile.exists());
      
      //Get output file name
      System.out.print(" >>> Enter output file (.html only): ");
      outFileName=(streamReader.readLine()).trim();
      outFile=new File(System.getProperty("user.dir") + "//" + outFileName);
      
      fr=new FileReader(inFile);
      br=new BufferedReader(fr);
      fw=new FileWriter(outFile);  

      while((line=br.readLine())!=null) {
        line=line.trim();
        if(line.length()>0) {
   
          fw.write("<b>Text:</b> " + line);
          fw.write("<br>\n");
    
          fw.write("<b>Word instance:</b> ");
          tokenizer.wordInstance(line);
          typeList=tokenizer.getTypeList();
          begin=tokenizer.first();
          int i=0;
          while(tokenizer.hasNext()) {
            end=tokenizer.next();
            type=((Integer)typeList.elementAt(i++)).intValue();
            if(type==0)
              fw.write("<font color=#ff0000>" + line.substring(begin, end) + "</font>");
            else if(type==1)
              fw.write("<font color=#00bb00>" + line.substring(begin, end) + "</font>");
            else if(type==2)
              fw.write("<font color=#0000bb>" + line.substring(begin, end) + "</font>");
            else if(type==3)
              fw.write("<font color=#aa00aa>" + line.substring(begin, end) + "</font>");
            else if(type==4)
              fw.write("<font color=#00aaaa>" + line.substring(begin, end) + "</font>");
            fw.write("<font color=#000000>|</font>");
            begin=end;
          }
          fw.write("<br>\n");
          
          fw.write("<b>Line instance:</b> ");
          tokenizer.lineInstance(line);    
          begin=tokenizer.first();
          while(tokenizer.hasNext()) {
            end=tokenizer.next();
            fw.write(line.substring(begin, end) + "<font color=#ff0000>|</font>");
            begin=end;
          }
          fw.write("<br><br>\n");        
        }
      } //while all line
      fw.write("<hr>");
      fw.write("<font color=#ff0000>unknown</font> | ");
      fw.write("<font color=#00bb00>known</font> | ");
      fw.write("<font color=#0000bb>ambiguous</font> | ");
      fw.write("<font color=#a00aa>English/Digits</font> | ");
      fw.write("<font color=#00aaaa>special</font>\n");
      fr.close();
      fw.close();
      System.out.println("\n *** Status: Use Web browser to view result: " + outFileName);
    } while(true);
  } //main
}
