package CampoMinado;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ajuda extends JPanel{
    
    private final JLabel textoTutorial;
    

    public Ajuda() {
        textoTutorial = new JLabel();
        
        String htmlText = "<html>"
                + "<h1>Seu objetivo é marcar todas as bombas com o botão direito<br><br>"
                +    "sem explodir nenhuma bomba ao fazer isso.<br><br>"
                +    "A quantidade de marcações faltantes estão à direita do botão<br>"
                +    "'reiniciar'.<br><br>"
                +    "Do lado esquerdo temos a quantidade de botoes sem minas.<br>";
        
        textoTutorial.setText(htmlText);
        add(textoTutorial);
        
    }
    
    
    
}
