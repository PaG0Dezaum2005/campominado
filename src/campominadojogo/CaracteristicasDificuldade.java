/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package campominadojogo;

/**
 *
 * @author heitor
 */
public class CaracteristicasDificuldade {
    
    static int rows, columns, numBombas, width, height;
    
    public static void setarCaracteristicas(Dificuldade dificuldade) {
        if (dificuldade == Dificuldade.FACIL)
        {
            rows = 9;
            columns = 9;
            numBombas = 10;
            width = 50;
            height = 50;
        }
        if (dificuldade == Dificuldade.MEDIO)
        {
            rows = 16;
            columns = 16;
            numBombas = 40;
            width = 40;
            height = 40;
        }
        if (dificuldade == Dificuldade.AVANCADO)
        {
            rows = 16;
            columns = 30;
            numBombas = 99;
            width = 30;
            height = 30;
        }

    }
}
