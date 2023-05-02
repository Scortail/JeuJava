public class Note {
    private double note;
    private double coefficient;

    class WrongNoteException extends Exception{

        public String toString(){
            return "Erreur: La note n'est pas compris entre 0 et 20.";
        }

    }

    public Note(double note, double coefficient) {
        if (coefficient <= 0) {
            this.coefficient = 1;
            System.out.println("Coefficient nul ou négatif, le coefficient a était modifier par 1");
        }
        else {
            this.coefficient = coefficient;
        }
        this.note = note;
    }

    public Note(double note) {
        this.note = note;
        this.coefficient = 1;
    }

    public double getNote() {
        return note;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String toString() {
        return "" + this.note + " Coef" + this.coefficient;
    }
}
