import javax.swing.*; 
import java.awt.event.*;
import java.io.*;
public class NotepadTrail 
{ 
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit,view;
    JMenuItem naya,open,save,saveAs,exit;
    JMenuItem cut,copy,paste,undo,redo,find,replace,count;
    JMenuItem wordWrap,format;
    JTextArea area;
    File currentFile;
    NotepadTrail()
    {
        frame = new JFrame("Menu Example");
        currentFile=null;

        area=new JTextArea();
        
        menuBar = new JMenuBar(); 
        
        file = new JMenu("File"); 
        edit = new JMenu("Edit"); 
        view = new JMenu("View"); 


        naya = new JMenuItem("New");
        open = new JMenuItem("Open"); 

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                JFileChooser fileChooser=new JFileChooser();
                int rv=fileChooser.showOpenDialog(frame);
                if(rv==JFileChooser.APPROVE_OPTION)
                {   currentFile=fileChooser.getSelectedFile();

                    try
                    {   FileInputStream fr=new FileInputStream(currentFile);
                        int capacity=fr.available();
                        byte data[]=new byte[capacity];
                        fr.read(data);
                        String textData=new String(data);
                        area.setText(textData);
                        frame.setTitle(currentFile.getName());
                        //set the title of jframe, show the name of file in the title bar
                        //if we do any changes in the file, then before opening a new file, 
                        //a confirmation dialog box should pop up asking whether to save file 
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);                        
                    }
                }           
            }
        });
        save = new JMenuItem("Save"); 
        save.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    //show the save dialog box using the file chooser
                    //read data of text area into a string
                    //if approve option is pressed
                    //get the selected file, open it using FileOuputStream, and store it into currentFile
                    //Write the data of the textarea into the file
                    //finally update the title of jframe by the name of the file
                    
                }
            }
        );
        saveAs = new JMenuItem("Save As");
        exit = new JMenuItem("Exit"); 

        cut = new JMenuItem("Cut"); 
        copy = new JMenuItem("Copy"); 
        paste = new JMenuItem("Paste");
        undo = new JMenuItem("Undo"); 
        redo = new JMenuItem("Redo"); 
        find = new JMenuItem("Find");
        replace = new JMenuItem("Replace"); 
        count = new JMenuItem("Count"); 

        format = new JMenuItem("Format");
        wordWrap = new JMenuItem("Word Wrap");


        file.add(naya);
        file.add(open); 
        file.add(save);
        file.add(saveAs); 
        file.add(exit); 

        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(undo);
        edit.add(redo);
        edit.add(find);
        edit.add(replace);
        edit.add(count);

        view.add(format);
        view.add(wordWrap);


        menuBar.add(file); 
        menuBar.add(edit);
        menuBar.add(view);



        frame.setJMenuBar(menuBar); 
        frame.add(new JScrollPane(area));
        frame.setSize(500, 500); 
        frame.setVisible(true); 

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
} 
class UseNotepadTrial
{
        public static void main(String args[])
        {
            NotepadTrail n1=new NotepadTrail() ;
        }
}