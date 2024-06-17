package CampoMinado;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TabelaJogo extends JPanel{

    public static int colunas, linhas, bombas, tamanhoBotao;
    public static ImageIcon iconBomba, iconFlag;
    public static boolean primeiraExplosao = true;
    private static Dificuldade dificuldade;

    
    public TabelaJogo(Dificuldade dificuldade) {
        
        TabelaJogo.dificuldade = dificuldade;                
        
        if(dificuldade == Dificuldade.FACIL)
        {
            colunas = 9;
            linhas = 9;
            bombas = 10;
        }
        
        if(dificuldade == Dificuldade.MEDIO)
        {
            colunas = 16;
            linhas = 16;
            bombas = 40;
        }
        
        if(dificuldade == Dificuldade.AVANCADO)
        {
            colunas = 30;
            linhas = 16;
            bombas = 99;

        }
        
        setLayout(new GridLayout(linhas, colunas));

        for (int botoes = 0; botoes < linhas * colunas; botoes++) {
            add(new Celula(botoes));
        }
        setarIcons();
        
        
    }
    
    private static void setBombasIniciais(){
        ArrayList<JButton> listaBotoesShuffled = new ArrayList<>();
        
        for(JButton botoes : Celula.listaBotoes)
        {
            if(!botoes.getActionCommand().equals("PRESSIONADO"))
            {
                listaBotoesShuffled.add(botoes);
            }
        }
        
        Collections.shuffle(listaBotoesShuffled);
        
        for(int i = 0; i < bombas; i++)
        {
            listaBotoesShuffled.get(i).setActionCommand("BOMBA");
        }
        
        for(int i = bombas; i < listaBotoesShuffled.size(); i++)
        {
            listaBotoesShuffled.get(i).setActionCommand("VAZIO");
        }
    }
    
    
    private void setarIcons(){
        iconBomba = new ImageIcon(getClass().getResource("/CampoMinado/resource/bombear.png"));
        Image imageBomba = iconBomba.getImage().getScaledInstance(500/linhas, 500/linhas, java.awt.Image.SCALE_SMOOTH);
        iconBomba = new ImageIcon(imageBomba);
        
        iconFlag = new ImageIcon(getClass().getResource("/CampoMinado/resource/bandeira-vermelha.png"));
        Image imageFlag = iconFlag.getImage().getScaledInstance(500/linhas, 500/linhas, java.awt.Image.SCALE_SMOOTH);
        iconFlag = new ImageIcon(imageFlag);
    }
    
    
    public static void perdeuJogo(JButton o){
        o.setIcon(TabelaJogo.iconBomba);
        o.setBackground(Color.RED);
        o.setActionCommand("EXPLODIDO");
        
        Menu.Mensagem.setText("Você perdeu! Tente novamente.");
        
        Pontuacao.deixarBonecoTriste();
        
        for(JButton cadaBomba : Celula.listaBotoes)
        {
            
            if(cadaBomba.getActionCommand().equals("BOMBA"))
            {
                cadaBomba.setIcon(TabelaJogo.iconBomba);
                cadaBomba.setBackground(Color.RED);
                cadaBomba.setActionCommand("EXPLODIDO");

            }
            cadaBomba.setActionCommand("FIM");
        }
    }
    
    public static void reiniciarJogo(){
        primeiraExplosao = true;
        Celula.listaBotoes.clear();
        PainelInicial.iniciarTabelaJogo(dificuldade);
    }
    
    
    
    public static void explodirPrimeira(JButton o){
        
        primeiraExplosao = false;
        
        o.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        o.setBackground(new Color(192, 192, 192));
        Pontuacao.aumentarPontuacao();
        o.setActionCommand("PRESSIONADO");
        
        int posicao, y, x;
        
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1}, // noroeste, norte, nordeste
            {0, -1},         {0, 1},  // oeste, leste
            {1, -1}, {1, 0}, {1, 1}   // sudoeste,  sul, sudeste
        };
        
        JButton inicial = o;
        
        posicao = Integer.parseInt(inicial.getName());
        x = posicao / colunas;
        y = posicao % colunas; 
        
        Queue<JButton> filaBotoes = new LinkedList<>();
        
        for (int[] direcao : direcoes) {
            int novoX = x + direcao[0];
            int novoY = y + direcao[1];
            if(novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas){
                int novaPosicao = novoX * colunas + novoY;
                JButton botaoAdjacente = Celula.listaBotoes.get(novaPosicao);
                filaBotoes.add(botaoAdjacente);
                botaoAdjacente.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                botaoAdjacente.setBackground(new Color(192, 192, 192));
                Pontuacao.aumentarPontuacao();
                botaoAdjacente.setActionCommand("PRESSIONADO");
                if(botaoAdjacente.getIcon() == iconFlag)
                    {
                        removerIcon(botaoAdjacente);
                        Pontuacao.aumentarMarcacao();
                    }
            }
        }
        
        
        setBombasIniciais();
        
        while(!filaBotoes.isEmpty()){
            inicial = filaBotoes.poll();
            posicao = Integer.parseInt(inicial.getName());
            x = posicao / colunas;
            y = posicao % colunas;
            for (int[] direcao : direcoes) {
                int novoX = x + direcao[0];
                int novoY = y + direcao[1];
                int novaPosicao = novoX * colunas + novoY;
                if(novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas){
                    JButton botaoAdjacente = Celula.listaBotoes.get(novaPosicao);
                    if(botaoAdjacente.getActionCommand().equals("BOMBA"))
                    {
                        continue;
                    }
                    contarBombasAoRedor(botaoAdjacente);
                    if (botaoAdjacente.getText().isEmpty() && botaoAdjacente.getActionCommand().equals("VAZIO")) {
                        botaoAdjacente.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        botaoAdjacente.setBackground(new Color(192, 192, 192));
                        Pontuacao.aumentarPontuacao();
                        botaoAdjacente.setActionCommand("PRESSIONADO");
                        filaBotoes.add(botaoAdjacente);
                    }
                    else if (!botaoAdjacente.getText().isEmpty() && botaoAdjacente.getActionCommand().equals("VAZIO")){
                        botaoAdjacente.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        Pontuacao.aumentarPontuacao();
                        botaoAdjacente.setActionCommand("PRESSIONADO");
                    }
                    if(botaoAdjacente.getIcon() == iconFlag)
                    {
                        removerIcon(botaoAdjacente);
                        Pontuacao.aumentarMarcacao();
                    }
                }
            }
        }
        
    }
    
    public static void explodirVazios(JButton o){
        
        int posicao, y, x;
        
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1}, // noroeste, norte, nordeste
            {0, -1},         {0, 1},  // oeste, leste
            {1, -1}, {1, 0}, {1, 1}   // sudoeste,  sul, sudeste
        };
        
        LinkedList<JButton> listaDosAbertos = new LinkedList<>();
        Queue<JButton> filaBotoes = new LinkedList<>();
        filaBotoes.add(o);
        
        
        while (!filaBotoes.isEmpty()) {
            
            JButton inicial = filaBotoes.poll();
            
            posicao = Integer.parseInt(inicial.getName());
            x = posicao / colunas;
            y = posicao % colunas;
            for (int[] direcao : direcoes) {
                int novoX = x + direcao[0];
                int novoY = y + direcao[1];
                int novaPosicao = novoX * colunas + novoY;
                if(novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas){
                    JButton botaoAdjacente = Celula.listaBotoes.get(novaPosicao);

                    if(botaoAdjacente.getIcon() == iconFlag)
                        {
                            continue;
                        }

                    botaoAdjacente.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    botaoAdjacente.setBackground(new Color(192, 192, 192));
                    Pontuacao.aumentarPontuacao();
                    contarBombasAoRedor(botaoAdjacente);

                    if (botaoAdjacente.getText().isEmpty() && !botaoAdjacente.getActionCommand().equals("temp")){
                        filaBotoes.add(botaoAdjacente);
                        listaDosAbertos.add(botaoAdjacente);
                    }
                    
                    botaoAdjacente.setActionCommand("temp");
                }
            }
        }
        for(JButton temp : listaDosAbertos)
        {
            temp.setActionCommand("PRESSIONADO");
        }
    }

    
    private static void removerIcon(JButton button)
    {
        button.setIcon(null);
    }
    
    
    public static void contarBombasAoRedor(JButton button) {
        int posicao = Integer.parseInt(button.getName());
        int x = posicao / colunas;
        int y = posicao % colunas;
        int contadorBombas = 0;

        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1}, // noroeste, norte, nordeste
            {0, -1},         {0, 1},  // oeste, leste
            {1, -1}, {1, 0}, {1, 1}   // sudoeste,  sul, sudeste
        };

        for (int[] direcao : direcoes) {
            int novoX = x + direcao[0];
            int novoY = y + direcao[1];
            if (novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas) {
                int novaPosicao = novoX * colunas + novoY;
                JButton botaoAdjacente = Celula.listaBotoes.get(novaPosicao);
                if (botaoAdjacente.getActionCommand().equals("BOMBA")) {
                    contadorBombas++;
                }
            }
        }
        
        String bombasAoRedor = Integer.toString(contadorBombas);
        if(!bombasAoRedor.equals("0")){
            button.setText(bombasAoRedor);
        }
            
        switch (contadorBombas) {
            case 1 -> button.setBackground(new Color(192, 210, 192));
            case 2 -> button.setBackground(new Color(200, 210, 192));
            case 3 -> button.setBackground(new Color(230, 200, 192));
            case 4 -> button.setBackground(new Color(250, 100, 50));
            case 5 -> button.setBackground(new Color(250, 50, 50));
            case 6 -> button.setBackground(new Color(250, 50, 50));
            case 7 -> button.setBackground(new Color(250, 50, 50));
            case 8 -> button.setBackground(new Color(250, 50, 50));
            default -> {
            }
        }
    }
    
    public static void checarVitoria(){
        
        int marcacoesCertas = 0;
        
        for(JButton botoes : Celula.listaBotoes)
        {
            if(botoes.getIcon() == TabelaJogo.iconFlag && botoes.getActionCommand().equals("BOMBA"))
            {
                marcacoesCertas++;
            }
        }
        
        if (marcacoesCertas == TabelaJogo.bombas)
        {   
            Menu.voltarPainelInicial();
            PainelInicial.subtitulo.setText("Parabéns! Você ganhou.");
            return;
        }
        
        Menu.Mensagem.setText("Suas marcações estão incorretas.");
        
    }
}
