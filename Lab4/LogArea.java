import javax.swing.JTextArea;

public class LogArea extends JTextArea{
    @Override
    public void append(String str){
        super.append(str);
        super.append("\n");
    }
}
