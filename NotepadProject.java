import javax.swing.*; 
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class NotepadProject 
{ 
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit,view;
    JMenuItem naya,open,save,saveAs,exit;
    JMenuItem cut,copy,paste,undo,redo,find,findNext,replace,count;
    JMenuItem wordWrap,format;
    JTextArea area;
    File currentFile;
    UndoManager undoManager= new UndoManager();
    boolean isWrapEnable=false;
    NotepadProject()
    {
        frame = new JFrame("Untitled");
        currentFile=null;

        area=new JTextArea();
        Font areaFont=NotepadUtilities.getFontFromFile();
        area.setFont(areaFont); 

        area.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                updateUndoRedoState();
            }
        });
        
        menuBar = new JMenuBar(); 
        
        file = new JMenu("File"); 
        edit = new JMenu("Edit"); 
        view = new JMenu("View"); 

        file.setMnemonic('F');
        edit.setMnemonic('E');
        view.setMnemonic('V');


        //New functionality
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


        //Open functionality
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
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText())) 
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


        //Save functionality
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
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText())){ 
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
            });


        //Save as functionality
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
            });


        //Exit functionality
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
                            if(!NotepadUtilities.isDifferent(currentFile,area.getText())){
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

        //Cut functionality
        cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                area.cut();
            }
        });

        //Copy functionality
        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                area.copy();
            }
        });

        //Paste functionality
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                area.paste();
            }
        });

        //Undo functionality
        undo = new JMenuItem("Undo"); 
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(undoManager.canUndo()) {
                    undoManager.undo();
                }
                updateUndoRedoState();
            }
        });


        //Redo functionality
        redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(undoManager.canRedo()) {
                    undoManager.redo();
                }
                updateUndoRedoState();
            }
        });
        updateUndoRedoState();


        //Find functionality
        find = new JMenuItem("Find");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        find.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                doFindDialog();

            }
        });

        //Find next functionality
        findNext = new JMenuItem("Find Next");
        findNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        findNext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                doFindNext();

            }
        });

        //Replace functionality
        replace = new JMenuItem("Replace");
        replace.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    doReplaceDialog();
                }
            });
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        // this is the code to prevent default behaviour of textarea shortcuts
        area.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control H"), "none");



        count = new JMenuItem("Count");
        count.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String text = area.getText();
                int characters = text.length();
                String trimmed = text.trim();
                int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
                int lines = text.isEmpty() ? 0 : text.split("\n").length;

                JOptionPane.showMessageDialog(frame,
                    "Words: " + words + "\nCharacters: " + characters + "\nLines: " + lines);
            }
        });


        //Format functionality 
        format = new JMenuItem("Format");
        format.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                JFrame formatFrame=new JFrame("Format");
                formatFrame.setSize(400, 320);                 
                formatFrame.setLayout(null);
                formatFrame.setLocation(150,150);


                JLabel label1=new JLabel("Fonts");
                label1.setBounds(30, 20, 80, 25);
                formatFrame.add(label1);

                //System fonts, fetch karne ke liye
                //Graphicenvironment class ka static method hai, getLocalGraphicsEnvironment(), 
                //is se hamare pass
                //LocalGraphicsEnvironment class ka object aata hai, is objects ke sath hamne 
                //getAvailableFontFamilyNames() function call kiya hai, 
                //jo ki local machine(computer) par available fonts
                //ke names ko String array ki form me return karta hai

                String[] fonts = GraphicsEnvironment.
                                        getLocalGraphicsEnvironment().
                                            getAvailableFontFamilyNames();
                JComboBox <String>fontBox=new JComboBox<String>(fonts);
                fontBox.setBounds(30, 45, 150, 25);
                formatFrame.add(fontBox);

                JLabel label2=new JLabel("Size");
                label2.setBounds(220, 20, 80, 25);
                formatFrame.add(label2);

                String sizes[]={"10","12","14","16","20","24","30","36","42","50","60","70"};
                JComboBox <String>sizeBox=new JComboBox<String>(sizes);
                sizeBox.setBounds(220, 45, 60, 25);
                formatFrame.add(sizeBox);

                JCheckBox boldCheck=new JCheckBox("Bold");
                boldCheck.setBounds(30, 80, 80, 25);
                formatFrame.add(boldCheck);

                JCheckBox italicCheck=new JCheckBox("Italics");
                italicCheck.setBounds(120, 80, 80, 25);
                formatFrame.add(italicCheck);

                JCheckBox underlineCheck=new JCheckBox("Underlined");
                underlineCheck.setBounds(200, 80, 120, 25);
                formatFrame.add(underlineCheck); 

                JLabel previewLabel=new JLabel("Preview Text");
                previewLabel.setBounds(40, 105, 240, 55);
                previewLabel.setBorder(BorderFactory.createTitledBorder("Preview"));
                previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                previewLabel.setVerticalAlignment(SwingConstants.CENTER);
                formatFrame.add(previewLabel);                

                // Add listeners for live preview
                ItemListener updatePreviewListener = e -> {
                    Font f=NotepadUtilities.getSelectedFont(fontBox, sizeBox, boldCheck, italicCheck, false);
                    previewLabel.setFont(f);
                };
                fontBox.addItemListener(updatePreviewListener);
                sizeBox.addItemListener(updatePreviewListener);
                boldCheck.addItemListener(updatePreviewListener);
                italicCheck.addItemListener(updatePreviewListener);
                JButton b=new JButton("Apply");
                b.setBounds(110, 190, 100, 30);
                b.addActionListener(e -> {
                    Font f=NotepadUtilities.getSelectedFont(fontBox, sizeBox, boldCheck, italicCheck, true);
                    area.setFont(f);
                });

                formatFrame.add(b); 
                formatFrame.setVisible(true);
                formatFrame.setResizable(false);
                formatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        //Word wrap functionality 
        wordWrap = new JMenuItem("Word Wrap");
        wordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        wordWrap.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    isWrapEnable = !isWrapEnable;
                    area.setLineWrap(isWrapEnable);
                }
            });


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
        edit.add(findNext);
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
        private void updateUndoRedoState(){
            undo.setEnabled(undoManager.canUndo());
            redo.setEnabled(undoManager.canRedo());
        }
        String lastFind = "";
        boolean lastFindCaseInsensitive = false;
        int lastFindPos = 0;

        private void doFindDialog()
        {
            JPanel p = new JPanel(new GridLayout(0, 1, 6, 6));
            JTextField tf = new JTextField(lastFind);
            JCheckBox caseInsensitive = new JCheckBox("Case-insensitive", lastFindCaseInsensitive);

            p.add(new JLabel("Find what:"));
            p.add(tf);
            p.add(caseInsensitive);

            int rv = JOptionPane.showConfirmDialog(
                    frame,
                    p,
                    "Find",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (rv == JOptionPane.OK_OPTION)
            {
                lastFind = tf.getText();
                lastFindCaseInsensitive = caseInsensitive.isSelected();
                lastFindPos = Math.max(area.getCaretPosition(), 0);
                findAndSelectNext();
            }
        }
        private void findAndSelectNext()
        {
            String content = area.getText();
            String target = lastFind;

            if (target == null || target.isEmpty())
                return;

            String hay = content;
            String needle = target;

            if (lastFindCaseInsensitive)
            {
                hay = content.toLowerCase();
                needle = target.toLowerCase();
            }

            int idx = hay.indexOf(needle, lastFindPos);

            if (idx == -1 && lastFindPos > 0)
                idx = hay.indexOf(needle, 0); // wrap to beginning

            if (idx >= 0)
            {
                area.requestFocusInWindow();
                area.select(idx, idx + target.length());
                lastFindPos = idx + target.length();
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Not found.");
            }
        }
        private void doFindNext()
        {
            if (lastFind == null || lastFind.isEmpty())
                doFindDialog();
            else
                if (lastFindPos <= 0)
                    lastFindPos = area.getCaretPosition();

            findAndSelectNext();
        }
        private void doReplaceDialog()
        {
            JPanel p = new JPanel(new GridLayout(0, 1, 6, 6));
            JTextField tfFind = new JTextField(lastFind);
            JTextField tfReplace = new JTextField("");
            JCheckBox caseInsensitive = new JCheckBox("Case-insensitive", lastFindCaseInsensitive);
            p.add(new JLabel("Find what:"));
            p.add(tfFind);
            p.add(new JLabel("Replace with:"));
            p.add(tfReplace);
            p.add(caseInsensitive);
            Object[] options = { "Replace", "Replace All", "Cancel" };
            int choice = JOptionPane.showOptionDialog(
                    frame,
                    p,
                    "Replace",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            String target = tfFind.getText();
            String replacement = tfReplace.getText();
            boolean ci = caseInsensitive.isSelected();
            lastFind = target != null ? target : "";
            lastFindCaseInsensitive = ci;
            if (choice == 0)
            {
                lastFindPos = Math.max(area.getCaretPosition(), 0);
                replaceNext(target, replacement, ci);
            }
            else if (choice == 1)   
            {
                int count = replaceAll(target, replacement, ci);
                JOptionPane.showMessageDialog(frame,"Replaced " + count + " occurrence(s).");
            }
            else
            {
            }
        }
        private void replaceNext(String target, String replacement, boolean caseInsensitive)
        {
            if (target == null || target.isEmpty())
            {
                return;
            }

            int selStart = area.getSelectionStart();
            int selEnd = area.getSelectionEnd();

            if (selEnd > selStart)
            {
                String selected = area.getSelectedText();
                String a = selected;
                String b = target;

                if (caseInsensitive)
                {
                    a = a.toLowerCase();
                    b = b.toLowerCase();
                }

                if (a.equals(b))
                {
                    area.replaceRange(replacement, selStart, selEnd);
                    lastFindPos = selStart + replacement.length();
                    area.setCaretPosition(lastFindPos);
                    findAndSelectNext();
                    return;
                }
            }

            String content = area.getText();
            String hay = content;
            String needle = target;

            if (caseInsensitive)
            {
                hay = content.toLowerCase();
                needle = target.toLowerCase();
            }

            int startFrom = Math.max(0, Math.min(lastFindPos, content.length()));
            startFrom = Math.max(startFrom,
                    Math.min(area.getCaretPosition(), content.length()));

            int idx = hay.indexOf(needle, startFrom);

            if (idx == -1 && startFrom > 0)
            {
                idx = hay.indexOf(needle, 0);
            }

            if (idx >= 0)
            {
                area.requestFocusInWindow();
                area.select(idx, idx + target.length());
                area.replaceRange(replacement, idx, idx + target.length());
                lastFindPos = idx + replacement.length();
                findAndSelectNext();
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Not found.");
            }
        }
        private int replaceAll(String target, String replacement, boolean caseInsensitive)
{
    if(target == null || target.isEmpty())
        return 0;

    String original = area.getText();
    String hay = caseInsensitive ? original.toLowerCase() : original;
    String needle = caseInsensitive ? target.toLowerCase() : target;
    int count = 0;
    int fromIndex = 0;
    int idx = -1;

    StringBuilder sb = new StringBuilder();

    while(true)
    {
        idx = hay.indexOf(needle, fromIndex);
        if(idx == -1)
        {
            sb.append(original.substring(fromIndex));
            break;
        }

        sb.append(original.substring(fromIndex, idx));
        sb.append(replacement);
        count++;

        int nextStart = idx + needle.length();
        fromIndex = nextStart;
    }

    if(count > 0)
    {
        area.setText(sb.toString());
        area.setCaretPosition(Math.min(area.getText().length(), Math.max(0, area.getCaretPosition())));
        lastFindPos = 0;
    }

    return count;
}

        } 
class UseNotepadProject
{
        public static void main(String args[])
        {
            NotepadProject n1=new NotepadProject();
        }
}