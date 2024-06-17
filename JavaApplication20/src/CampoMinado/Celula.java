package CampoMinado;

import static CampoMinado.TabelaJogo.contarBombasAoRedor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Celula extends JButton implements ActionListener, MouseListener{
    
    public static ArrayList<JButton> listaBotoes = new ArrayList<>();
    
    

    public Celula(int id) {
        addActionListener(this);
        setName(Integer.toString(id));
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(new Color(224, 224, 224));
        listaBotoes.add(this);
        setFont(new java.awt.Font("Noto Sans", 1, 24));
        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton o = (JButton)e.getSource();
        
        int marcacoes = Integer.parseInt(Pontuacao.Marcacoes.getText());
            
            if(o.getIcon() == TabelaJogo.iconFlag)
            {
                o.setIcon(null);
                Pontuacao.aumentarMarcacao();
                Menu.Mensagem.setText(" ");
                return;
            }
            
            if(o.getActionCommand().equals("PRESSIONADO") || o.getActionCommand().equals("FIM"))
            {
                return;
            }
            
            if(marcacoes == 1)
            {
                Menu.Mensagem.setText("Suas marcações estão incorretas");
            }
            
            if(marcacoes == 0)
            {
                return;
            }
            
            o.setIcon(TabelaJogo.iconFlag);
            Pontuacao.diminuirMarcacao();
            
            if(marcacoes == 1)
            {
                TabelaJogo.checarVitoria();
            }
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        JButton o = (JButton) e.getSource();
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
        
            if(o.getIcon() == TabelaJogo.iconFlag || o.getActionCommand().equals("FIM"))
            {
                return;
            }

            if(o.getActionCommand().equals("PRESSIONADO") || !o.getText().isEmpty())
            {
                return;
            }

            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            setBackground(new Color(192, 192, 192));

            if(TabelaJogo.primeiraExplosao == true)
            {
                TabelaJogo.explodirPrimeira(o);
                return;
            }

            if(o.getActionCommand().equals("BOMBA"))
            {
                explodir(o);
                return;
            }

            if(o.getActionCommand().equals("VAZIO"))
            {
                contarBombasAoRedor(o);
                Pontuacao.aumentarPontuacao();
                o.setActionCommand("PRESSIONADO");
                if(o.getText().isEmpty()){
                    TabelaJogo.explodirVazios(o);
                }
            }
        }
    }
    
    private void explodir(JButton o){
        TabelaJogo.perdeuJogo(o);
    }

    
        @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
