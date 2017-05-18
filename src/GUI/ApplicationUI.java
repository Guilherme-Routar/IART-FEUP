package GUI;
import javax.swing.JFrame;

public class ApplicationUI extends JFrame {
    public static void main(String[] args) {
        try {
        	ApplicationUI frame = new ApplicationUI();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ApplicationUI() {
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main()
    {
    	new ApplicationUI();
    }
}


