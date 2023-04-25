public class Note {
    private int note;
    private int coefficient;

    public Note(int note, int coefficient) {
        if (coefficient <= 0) {
            this.coefficient = 1;
            System.out.println("Coefficient nul ou négatif, le coefficient a était modifier par 1");
        }
        else {
            this.coefficient = coefficient;
        }
        this.note = note;
    }

    public Note(int note) {
        this.note = note;
        this.coefficient = 1;
    }

    public int getNote() {
        return note;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public String toString() {
        return "" + this.note + " Coef" + this.coefficient;
    }
}
