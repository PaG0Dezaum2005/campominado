package CampoMinado;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Placar extends JPanel{
    

    public Placar(JPanel containerPanel, JFrame jogo) {
        
        setLayout(new BorderLayout());
        
        Menu menu = new Menu(containerPanel, jogo);
        add(menu, BorderLayout.NORTH);
        
        Pontuacao pontuacao = new Pontuacao();
        add(pontuacao, BorderLayout.CENTER);
    }
    
}
