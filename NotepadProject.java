import javax.swing.*; 
import java.awt.event.*;
import java.io.*;
class NotepadUtilities
{
    public static boolean isDifferent(File file,String data)
    {
        try
        {   FileInputStream fr=new FileInputStream(file);
            int capacity=fr.available();
            byte byteData[]=new byte[capacity];
            fr.read(byteData);
            fr.close();   
            return data.equals(new String(byteData));
        }
        catch(Exception e)
        {   return false;   
        }
    }
}
public class NotepadProject 
{ 
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit,view;
    JMenuItem naya,open,save,saveAs,exit;
    JMenuItem cut,copy,paste,undo,redo,find,replace,count;
    JMenuItem wordWrap,format;
    JTextArea area;
    File currentFile;
    NotepadProject()
    {
        frame = new JFrame("Untitled");
        currentFile=null;

        area=new JTextArea();
        
        menuBar = new JMenuBar(); 
        
        file = new JMenu("File"); 
        edit = new JMenu("Edit"); 
        view = new JMenu("View"); 

        file.setMnemonic('F');
        edit.setMnemonic('E');
        view.setMnemonic('V');

        naya = new JMenuItem("New");
        naya.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        naya.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
        try{
            if((frame.getTitle()).equals("Untitled")){
                if(!(area.getText().isEmpty())){
                    int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                        if(choice==JOptionPane.YES_OPTION){
                            JFileChooser fileChooser=new JFileChooser();
                            int rv=fileChooser.showSaveDialog(frame);
                            if(rv==JFileChooser.APPROVE_OPTION)
                            {   currentFile=fileChooser.getSelectedFile();

                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                                frame.setTitle("Untitled");
                            }
                            area.setText("");
                            }
                            if(choice==JOptionPane.NO_OPTION){
                                area.setText("");
                            }
                            if(choice==JOptionPane.CANCEL_OPTION)
                                {
                                    return;
                                }
                        }
                    }
                else{
                    if(!NotepadUtilities.isDifferent(currentFile,area.getText())){
                        int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                        if(choice==JOptionPane.YES_OPTION)
                            {       
                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                                area.setText("");
                                frame.setTitle("Untitled");
                                currentFile=null;

                            }
                            if(choice==JOptionPane.CANCEL_OPTION)
                            {
                                return;
                            }
                            if(choice==JOptionPane.NO_OPTION){
                                area.setText("");
                                frame.setTitle("Untitled");
                                currentFile=null;
                            }

                    }
                    else{
                        area.setText("");
                        frame.setTitle("Untitled");
                        currentFile=null;
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    });

        //if frame is untitled
            //data hai kya?
                //confirmation dialog box-if u want to save or not?
                    //save dialog box
                    //clear text area
                //else
                    //clear text area
        //else 
            //isDifferent
                //confirmation dialog box-if u want to save or not?
                    //write data into file
                    //clear text area
                    //frame title=untitled
                    //current file=null
                //else
                    //clear text area
                    //frame title=untitled
                    //current file=null
            //else
                //clear text area
                //frame title=untitled
                //current file=null
        open = new JMenuItem("Open"); 
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                    try
                    {   if((frame.getTitle()).equals("Untitled"))
                        {
                            if(!(area.getText().isEmpty()))
                            {
                                int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                                if(choice==JOptionPane.YES_OPTION){
                                    JFileChooser fileChooser=new JFileChooser();
                                    int rv=fileChooser.showSaveDialog(frame);
                                    if(rv==JFileChooser.APPROVE_OPTION)
                                    {   currentFile=fileChooser.getSelectedFile();

                                        FileOutputStream fw=new FileOutputStream(currentFile);
                                        byte data[]=area.getText().getBytes();
                                        fw.write(data);
                                        fw.close();
                                        frame.setTitle(currentFile.getName());
                                    }
                                    else
                                    {
                                        return;
                                    }
                                }
                                if(choice==JOptionPane.CANCEL_OPTION)
                                {
                                    return;
                                }
                            }
                        }
                        else
                        {
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText()))//agr changes kiye to hi option pane confimation dialog box aana chahiye 
                            {
                            int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                            if(choice==JOptionPane.YES_OPTION)
                            {       
                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                            }
                            if(choice==JOptionPane.CANCEL_OPTION)
                            {
                                return;
                            }
                            }
                        }       
                                JFileChooser fileChooser=new JFileChooser();
                                int rv=fileChooser.showOpenDialog(frame);
                                if(rv==JFileChooser.APPROVE_OPTION)
                                {   currentFile=fileChooser.getSelectedFile();
                                FileInputStream fr=new FileInputStream(currentFile);
                                int capacity=fr.available();
                                byte data[]=new byte[capacity];
                                fr.read(data);
                                fr.close();
                                String textData=new String(data);
                                area.setText(textData);
                                frame.setTitle(currentFile.getName());
                                }
                            }
                    catch(Exception e)
                    {
                        System.out.println(e);                        
                    }            
            }
        });
        save = new JMenuItem("Save"); 
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {
                    try
                    {   if((frame.getTitle()).equals("Untitled"))
                        {
                            JFileChooser fileChooser=new JFileChooser();
                            int rv=fileChooser.showSaveDialog(frame);
                            if(rv==JFileChooser.APPROVE_OPTION)
                            {   currentFile=fileChooser.getSelectedFile();

                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                                frame.setTitle(currentFile.getName());
                            }
                        }
                        else
                        {
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText())){//agr changes kiye to hi option pane confimation dialog box aana chahiye 
                            int choice=JOptionPane.showConfirmDialog(
                            frame,
                            "Saving will overwrite this file",
                            "Warning",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE
                            );
                            if(choice==JOptionPane.OK_OPTION){
                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                            }
                        }
                    }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);                        
                    }
                }
            }
        );
        saveAs = new JMenuItem("Save As");
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
        saveAs.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    try
                    {
                        JFileChooser fileChooser=new JFileChooser();
                        int rv=fileChooser.showSaveDialog(frame);
                        if(rv==JFileChooser.APPROVE_OPTION)
                        {
                            currentFile = fileChooser.getSelectedFile();
                            FileOutputStream fw = new FileOutputStream(currentFile);
                            byte data[] = area.getText().getBytes();
                            fw.write(data);
                            fw.close();
                            frame.setTitle(currentFile.getName());
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }
        );
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                    try
                    {   if((frame.getTitle()).equals("Untitled"))
                        {
                            if(!(area.getText().isEmpty()))
                            {
                                int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                                if(choice==JOptionPane.YES_OPTION){
                                    JFileChooser fileChooser=new JFileChooser();
                                    int rv=fileChooser.showSaveDialog(frame);
                                    if(rv==JFileChooser.APPROVE_OPTION)
                                    {   currentFile=fileChooser.getSelectedFile();

                                        FileOutputStream fw=new FileOutputStream(currentFile);
                                        byte data[]=area.getText().getBytes();
                                        fw.write(data);
                                        fw.close();
                                        frame.setTitle(currentFile.getName());
                                    }
                                    else
                                    {
                                        return;
                                    }
                                }
                                if(choice==JOptionPane.CANCEL_OPTION)
                                {
                                    return;
                                }
                            }
                        }
                        else
                        {
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText()))//agr changes kiye to hi option pane confimation dialog box aana chahiye 
                            {
                            int choice=JOptionPane.showConfirmDialog(frame,"Do you want to save changes?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                            if(choice==JOptionPane.YES_OPTION)
                            {       
                                FileOutputStream fw=new FileOutputStream(currentFile);
                                byte data[]=area.getText().getBytes();
                                fw.write(data);
                                fw.close();
                            }
                            if(choice==JOptionPane.CANCEL_OPTION)
                            {
                                return;
                            }
                            }
                        }       
                                System.exit(0);
                            }
                    catch(Exception e)
                    {
                        System.out.println(e);                        
                    }            
            }
        });

        cut = new JMenuItem("Cut"); 
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copy = new JMenuItem("Copy"); 
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        undo = new JMenuItem("Undo"); 
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redo = new JMenuItem("Redo"); 
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        find = new JMenuItem("Find");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        replace = new JMenuItem("Replace"); 
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
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
class UseNotepadProject
{
        public static void main(String args[])
        {
            NotepadProject n1=new NotepadProject();
        }
}