package CampoMinado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener{

    private final JButton Jogo;
    private final JButton Ajuda;
    private static JPanel containerPanel;
    private static JFrame jogo;
    public static JLabel Mensagem;
    
       public Menu(JPanel containerPanel, JFrame jogo) {
        Menu.containerPanel = containerPanel;
        Menu.jogo = jogo;
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Jogo = new JButton("Jogo");
        Ajuda = new JButton("Ajuda");
        Mensagem = new JLabel(" ", JLabel.CENTER);
        
        Jogo.setBackground(new Color(229, 255, 204));
        Ajuda.setBackground(new Color(229, 255, 204));
        
        buttonPanel.add(Jogo);
        buttonPanel.add(Ajuda);
        
        add(buttonPanel, BorderLayout.NORTH);
        add(Mensagem, BorderLayout.CENTER);
        
        Jogo.addActionListener(this);
        Ajuda.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Jogo){
            voltarPainelInicial();            
        }
        else if(e.getSource() == Ajuda){
            containerPanel.removeAll();
            Ajuda ajuda = new Ajuda();
            containerPanel.add(this, BorderLayout.NORTH);
            containerPanel.add(ajuda, BorderLayout.CENTER);
            
            containerPanel.revalidate();
            containerPanel.repaint();
        }
    }
    
    public static void voltarPainelInicial(){
            containerPanel.removeAll();

            PainelInicial reiniciar = new PainelInicial(containerPanel, jogo);
            containerPanel.add(reiniciar, BorderLayout.CENTER);

            containerPanel.revalidate();
            containerPanel.repaint();
            
            PainelInicial.voltarPainel();
    }
    
}
