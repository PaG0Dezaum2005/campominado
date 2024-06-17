package CampoMinado;

import static CampoMinado.TabelaJogo.linhas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pontuacao extends JPanel implements ActionListener{

    public static JLabel Pontuacao, Marcacoes;
    private static JButton Reiniciar;
    public static ImageIcon iconFeliz, iconTriste;
    
    public Pontuacao() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        Pontuacao = new JLabel("0");
        Reiniciar = new JButton("");
        Marcacoes = new JLabel(Integer.toString(TabelaJogo.bombas));
        
        Reiniciar.setBackground(new Color(229, 255, 204));
        Reiniciar.setPreferredSize(new Dimension(40, 40));

        add(Box.createHorizontalGlue());
        add(Pontuacao);
        add(Box.createRigidArea(new Dimension(270, 0)));
        add(Reiniciar);
        add(Box.createRigidArea(new Dimension(270, 0)));
        add(Marcacoes);
        add(Box.createHorizontalGlue());
        
        Reiniciar.addActionListener(this);
        
        
        iconFeliz = new ImageIcon(getClass().getResource("/CampoMinado/resource/feliz.png"));
        Image imageFeliz = iconFeliz.getImage().getScaledInstance(300/linhas, 300/linhas, java.awt.Image.SCALE_SMOOTH);
        iconFeliz = new ImageIcon(imageFeliz);
        
        iconTriste = new ImageIcon(getClass().getResource("/CampoMinado/resource/triste.png"));
        Image imageTriste = iconTriste.getImage().getScaledInstance(300/linhas, 300/linhas, java.awt.Image.SCALE_SMOOTH);
        iconTriste = new ImageIcon(imageTriste);
        
        deixarBonecoFeliz();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Reiniciar){
            TabelaJogo.reiniciarJogo();
        }
    }
    
    public static void aumentarPontuacao(){
        int pontos = Integer.parseInt(Pontuacao.getText());
        pontos++;
        Pontuacao.setText(Integer.toString(pontos));
    }
    
    public static void aumentarMarcacao(){
        int marcacoes = Integer.parseInt(Marcacoes.getText());
        marcacoes++;
        Marcacoes.setText(Integer.toString(marcacoes));
    }
    
    public static void diminuirMarcacao(){
        int marcacoes = Integer.parseInt(Marcacoes.getText());
        marcacoes--;
        Marcacoes.setText(Integer.toString(marcacoes));
    }
    
    public static void deixarBonecoFeliz(){
        Reiniciar.setIcon(iconFeliz);
    }
    
    public static void deixarBonecoTriste(){
        Reiniciar.setIcon(iconTriste);
    }
}
