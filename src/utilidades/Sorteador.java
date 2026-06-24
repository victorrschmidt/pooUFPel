package utilidades;
import java.util.Random;

public class Sorteador {
    private Random mecanismo;
    
    public Sorteador() {
        this.mecanismo = new Random();
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public Random getMecanismo() {
        return this.mecanismo;
    }
    
    public void setMecanismo(Random mecanismo) {
        this.mecanismo = mecanismo;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public int rolarDado(int lados) {
        return this.mecanismo.nextInt(lados) + 1;
    }
}
