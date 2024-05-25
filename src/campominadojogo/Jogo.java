/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package campominadojogo;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author heitor
 */
public class Jogo implements ActionListener, MouseListener{
    
    boolean primeiraJogada;
    List<JButton> botoes = new ArrayList<>();
    ImageIcon iconBomba, iconFlag;
    
    public Jogo() {
        
    }  
    
    public void jogar(List<JButton> TodosBotoes, Dificuldade dificuldade)
    {
        botoes = TodosBotoes;
        primeiraJogada = true;
        CaracteristicasDificuldade.setarCaracteristicas(dificuldade);
        setarIcons();
        
        int ID = 0;
        for (JButton button : TodosBotoes) {
            button.setText("");
            button.setActionCommand("NADA");
            button.setFont(new java.awt.Font("Noto Sans", 1, 24));
            button.setBackground(Color.LIGHT_GRAY);
            button.addActionListener(this);
            button.addMouseListener(this);
            String numberStr = Integer.toString(ID);
            button.setName(numberStr);
            ID++;
        }          
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        JButton sourceButton = (JButton) e.getSource();
        
        if(sourceButton.getIcon() == iconFlag)
        {
            //TODO adicionar som de erro?
            return;
        }
        
        if (primeiraJogada)
        {
            explosaoInicial(sourceButton);
            return;
        }
        
        if (sourceButton.getActionCommand().equals("BOMBA"))
        {
            sourceButton.setIcon(iconBomba);
            sourceButton.setActionCommand("EXPLODIDA");
            return;
        }
        if (sourceButton.getActionCommand().equals("VAZIO"))
        {
            sourceButton.setBackground(Color.GRAY);
            sourceButton.setActionCommand("PRESSIONADO");
            setarBomasAoRedor(sourceButton);
        }
    }
    
    private void explosaoInicial(JButton button)
    {
        button.setBackground(Color.RED);
        button.setActionCommand("VAZIO");
        setarBomasAoRedor(button);
        primeiraJogada = false;
        
        Random rand = new Random();
        int totalTiles = CaracteristicasDificuldade.rows * CaracteristicasDificuldade.columns;
        int porcentagemExplosaoInicial = rand.nextInt(((int) (totalTiles * 0.35) - (int) (totalTiles * 0.2)) + 1) + (int) (totalTiles * 0.2);
        
        int posicaoAtual = Integer.parseInt(button.getName());
        
        for(int i = 0; i < porcentagemExplosaoInicial; i++)
        {
            int direcao = rand.nextInt(4);
            
            if (direcao == 0 && posicaoAtual >= CaracteristicasDificuldade.columns) { // norte
                posicaoAtual = posicaoAtual - CaracteristicasDificuldade.columns;
                button = encontrarBotaoPeloNome(Integer.toString(posicaoAtual));
                button.setBackground(Color.RED);
                button.setActionCommand("VAZIO");
                button.setIcon(null);
            } else if (direcao == 1 && ((posicaoAtual + 1) % CaracteristicasDificuldade.columns) != 0) { // leste
                posicaoAtual++;
                button = encontrarBotaoPeloNome(Integer.toString(posicaoAtual));
                button.setBackground(Color.RED);
                button.setActionCommand("VAZIO");
                button.setIcon(null);
            } else if (direcao == 2 && posicaoAtual < (CaracteristicasDificuldade.rows * CaracteristicasDificuldade.columns) - CaracteristicasDificuldade.columns) { // sul
                posicaoAtual += CaracteristicasDificuldade.columns;
                button = encontrarBotaoPeloNome(Integer.toString(posicaoAtual));
                button.setBackground(Color.RED);
                button.setActionCommand("VAZIO");
                button.setIcon(null);
            } else if (direcao == 3 && posicaoAtual % CaracteristicasDificuldade.columns != 0) { // oeste
                posicaoAtual--;
                button = encontrarBotaoPeloNome(Integer.toString(posicaoAtual));
                button.setBackground(Color.RED);
                button.setActionCommand("VAZIO");
                button.setIcon(null);
            } 
        }
        
        setarMinas(botoes);
        
        for(JButton botao : botoes)
        {
            if(botao.getBackground().equals(Color.RED))
            {
                setarBomasAoRedor(botao);
            }
        }
    }
    
    private JButton encontrarBotaoPeloNome(String nome){
        for (JButton button : botoes) {
            if(button.getName().equals(nome))
            {
                return button;
            }
        }
        return null;
    }
   
    
    @Override
    public void mouseClicked(MouseEvent e) {
    JButton button = (JButton) e.getSource();
    if (e.getButton() == MouseEvent.BUTTON3) {
        if (button.getIcon() == iconBomba || button.getActionCommand().equals("PRESSIONADO")){
            return;
        }
        if (button.getIcon() == iconFlag) {
            button.setIcon(null);
        } else {
            button.setIcon(iconFlag);
        }
    }
    }
    
    private void setarBomasAoRedor(JButton botao)
    {
        int nameBotao = Integer.parseInt(botao.getName());
        String[] stringBombasAoRedor = new String[8];
        int[] idBombasAoRedor = new int[8];

        stringBombasAoRedor[0] = Integer.toString(nameBotao - (CaracteristicasDificuldade.columns - 1)); // 5
        stringBombasAoRedor[1] = Integer.toString(nameBotao - (CaracteristicasDificuldade.columns)); // 4
        stringBombasAoRedor[2] = Integer.toString(nameBotao - (CaracteristicasDificuldade.columns + 1)); // 3
        stringBombasAoRedor[3] = Integer.toString(nameBotao - 1); // 12
        stringBombasAoRedor[4] = Integer.toString(nameBotao + 1); // 14
        stringBombasAoRedor[5] = Integer.toString(nameBotao + (CaracteristicasDificuldade.columns - 1)); // 21
        stringBombasAoRedor[6] = Integer.toString(nameBotao + CaracteristicasDificuldade.columns); // 22
        stringBombasAoRedor[7] = Integer.toString(nameBotao + (CaracteristicasDificuldade.columns + 1)); // 23
        
        int j = 0;
        for (JButton button : botoes) {
            for(String name : stringBombasAoRedor)
            {
                if(button.getName().equals(name))
                {
                    idBombasAoRedor[j] = botoes.indexOf(button);
                    j++;
                }
            }  
        }
        
        int numBombas = 0;
        for(int i = 0; i < 8; i++)
        {
            if(botoes.get(idBombasAoRedor[i]).getActionCommand().equals("BOMBA")){
                numBombas++;
            }
        }
        if(numBombas > 0)
        {
            botao.setText(Integer.toString(numBombas));     
        }
    }
    
    
    private void setarIcons(){
        iconBomba = new ImageIcon(getClass().getResource("/campominadojogo/resource/bombear.png"));
        Image imageBomba = iconBomba.getImage().getScaledInstance(CaracteristicasDificuldade.width, CaracteristicasDificuldade.height, java.awt.Image.SCALE_SMOOTH);
        iconBomba = new ImageIcon(imageBomba);
        
        iconFlag = new ImageIcon(getClass().getResource("/campominadojogo/resource/bandeira-vermelha.png"));
        Image imageFlag = iconFlag.getImage().getScaledInstance(CaracteristicasDificuldade.width, CaracteristicasDificuldade.height, java.awt.Image.SCALE_SMOOTH);
        iconFlag = new ImageIcon(imageFlag);
    }
    
    private void setarMinas(List<JButton> TodosBotoes){
        Collections.shuffle(TodosBotoes);
        int bombas = 0;
        for (int i = 0; bombas < CaracteristicasDificuldade.numBombas && i < TodosBotoes.size(); i++) {
            JButton button = TodosBotoes.get(i);
            if(button.getActionCommand().equals("NADA") && (button.getBackground().equals(Color.RED)) != true){
              button.setActionCommand("BOMBA");
              bombas++;
            }
        }
        
        for (JButton button : TodosBotoes) {
            if(button.getActionCommand().equals("BOMBA") != true)
            {
                button.setActionCommand("VAZIO");
            }
        }
        
        
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
