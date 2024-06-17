package CampoMinado;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Jogo extends JFrame{
    
    public JFrame jogo;

    public static void main(String[] args) {
        
        JFrame jogo = new JFrame("Campo Minado");
        
        jogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jogo.setVisible(true);
        jogo.setSize(750, 850);
        jogo.setLocationRelativeTo(null);
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        jogo.add(containerPanel);
        
        PainelInicial painelInicial = new PainelInicial(containerPanel, jogo);
        containerPanel.add(painelInicial, BorderLayout.CENTER);
        
    }
    

}