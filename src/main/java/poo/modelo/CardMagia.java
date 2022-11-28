package poo.modelo;

public class CardMagia extends Card{

    private boolean usable;

    public CardMagia(String anId, String anImageId) {
        super(anId, anImageId);
        usable = true;
    }

    public void useCard() {
        usable = false;
    }
    
    public boolean isUsable() {
        return usable;
    }
}
