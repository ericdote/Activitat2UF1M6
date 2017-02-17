
package exercici2;


public class Producte {
    
    
    int codi, unitats;
    double preu;
    String nom;

    public Producte(int codi, String nom, int unitats, double preu) {
        this.codi = codi;
        this.unitats = unitats;
        this.preu = preu;
        this.nom = nom;
    }    
    
    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public int getUnitats() {
        return unitats;
    }

    public void setUnitats(int unitats) {
        this.unitats = unitats;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
}
