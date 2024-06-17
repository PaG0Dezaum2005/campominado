package CampoMinado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PainelInicial extends JPanel implements ActionListener {
    private static JPanel containerPanel;
    private static JFrame jogo;
    private JButton buttonFacil;
    private JButton buttonMedio;
    private JButton buttonAvancado;
    private JLabel titulo;
    public static JLabel subtitulo;
    
    public PainelInicial(){
    }

    public PainelInicial(JPanel containerPanel, JFrame jogo) {
        this.containerPanel = containerPanel;
        this.jogo = jogo;

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        jogo.setSize(750, 850);
        jogo.setLocationRelativeTo(null);

        titulo = new JLabel("CAMPO MINADO", SwingConstants.CENTER);
        subtitulo = new JLabel("", SwingConstants.CENTER);

        Font titleFont = new Font("Verdana", Font.BOLD, 36);
        Font subtitleFont = new Font("Verdana", Font.PLAIN, 24);
        titulo.setFont(titleFont);
        subtitulo.setFont(subtitleFont);

        add(titulo);
        add(subtitulo);

        buttonFacil = new JButton("Fácil");
        buttonMedio = new JButton("Médio");
        buttonAvancado = new JButton("Avançado");

        buttonFacil.setPreferredSize(new Dimension(220, 70));
        buttonMedio.setPreferredSize(new Dimension(220, 70));
        buttonAvancado.setPreferredSize(new Dimension(220, 70));

        Font font = new Font("Verdana", Font.BOLD, 28);

        buttonFacil.setFont(font);
        buttonMedio.setFont(font);
        buttonAvancado.setFont(font);
        
        buttonFacil.setBackground(new Color(229, 255, 204));
        buttonMedio.setBackground(new Color(229, 255, 204));
        buttonAvancado.setBackground(new Color(229, 255, 204));


        add(buttonFacil);
        add(buttonMedio);
        add(buttonAvancado);

        buttonFacil.addActionListener(this);
        buttonMedio.addActionListener(this);
        buttonAvancado.addActionListener(this);

        adjustLayout(layout);

        jogo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout(layout);
            }
        });
    }

    private void adjustLayout(SpringLayout layout) {
        SpringLayout.Constraints titleConstraints = layout.getConstraints(titulo);
        titleConstraints.setX(Spring.constant(20));
        titleConstraints.setY(Spring.constant(20));
        titleConstraints.setWidth(Spring.constant(jogo.getWidth() - 40));

        SpringLayout.Constraints subtitleConstraints = layout.getConstraints(subtitulo);
        subtitleConstraints.setX(Spring.constant(20));
        subtitleConstraints.setY(Spring.sum(Spring.constant(10), titleConstraints.getConstraint(SpringLayout.SOUTH)));
        subtitleConstraints.setWidth(Spring.constant(jogo.getWidth() - 40));

        SpringLayout.Constraints constraints1 = layout.getConstraints(buttonFacil);
        SpringLayout.Constraints constraints2 = layout.getConstraints(buttonMedio);
        SpringLayout.Constraints constraints3 = layout.getConstraints(buttonAvancado);

        constraints1.setX(Spring.constant((jogo.getWidth() - 220) / 2));
        constraints1.setY(Spring.sum(Spring.constant(50), subtitleConstraints.getConstraint(SpringLayout.SOUTH)));

        constraints2.setX(Spring.constant((jogo.getWidth() - 220) / 2));
        constraints2.setY(Spring.sum(Spring.constant(10), constraints1.getConstraint(SpringLayout.SOUTH)));

        constraints3.setX(Spring.constant((jogo.getWidth() - 220) / 2));
        constraints3.setY(Spring.sum(Spring.constant(10), constraints2.getConstraint(SpringLayout.SOUTH)));

        revalidate();
        repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonFacil) {
            iniciarTabelaJogo(Dificuldade.FACIL);
        } else if (e.getSource() == buttonMedio) {
            iniciarTabelaJogo(Dificuldade.MEDIO);
        } else if (e.getSource() == buttonAvancado) {
            jogo.setSize(1406 , 850);
            jogo.setLocationRelativeTo(null);
            iniciarTabelaJogo(Dificuldade.AVANCADO);
        }
    }
    
    public static void iniciarTabelaJogo(Dificuldade dificuldade){
        containerPanel.removeAll();
        
        TabelaJogo tabelaJogo = new TabelaJogo(dificuldade);
        Placar placar = new Placar(containerPanel, jogo);
        
        containerPanel.add(tabelaJogo, BorderLayout.CENTER);
        containerPanel.add(placar, BorderLayout.NORTH);
        
        placar.setPreferredSize(new Dimension(250, 100));

        containerPanel.revalidate();
        containerPanel.repaint();
    }
    
    public static void voltarPainel(){
        TabelaJogo.primeiraExplosao = true;
        Celula.listaBotoes.clear();
    }
}
