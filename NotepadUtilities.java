import java.io.*;        
import java.awt.*;       
import javax.swing.*;    
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
    public static void writeFontOnFile(String fontName, int style, int fontSize)
    {
        String fontConfiguration = fontName + ", " + style + ", " + fontSize;
        try
        {
            FileWriter fw = new FileWriter("FontConfiguration.config");
            fw.write(fontConfiguration);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong while saving font config");
        }
    }
    public static Font getFontFromFile()
    {
        Font f = new Font("Times New roman", Font.PLAIN, 10);
        try
        {
            FileInputStream fr = new FileInputStream("FontConfiguration.config");
            byte fontData[] = new byte[fr.available()];
            fr.read(fontData);

            String fontConfiguration = new String(fontData);
            String fontConfi[] = fontConfiguration.split(", ");
            String fontName = fontConfi[0];
            int fontStyle = Integer.parseInt(fontConfi[1]);
            int fontSize = Integer.parseInt(fontConfi[2]);

            f = new Font(fontName, fontStyle, fontSize);
            fr.close();
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong while saving font config");
        }
        return f;
    }
    public static Font getSelectedFont(
        JComboBox<String> fontBox,
        JComboBox<String> sizeBox,
        JCheckBox boldCheck,
        JCheckBox italicCheck,
        boolean saveOnFile
)
    {
        String fontName = (String) fontBox.getSelectedItem();
        int fontSize = Integer.parseInt((String) sizeBox.getSelectedItem());
        int style = Font.PLAIN;

        if (boldCheck.isSelected())
            style |= Font.BOLD;

        if (italicCheck.isSelected())
            style |= Font.ITALIC;

        Font font = new Font(fontName, style, fontSize);

        if (saveOnFile)
        {
            writeFontOnFile(fontName, style, fontSize);
        }

        return font;
    }
}