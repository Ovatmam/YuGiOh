package poo;

public class CardEfeito extends CardMonstro{

    private boolean efeito;

    public CardEfeito(String anId, String anImageId) {
        super(anId, anImageId);
        efeito = true;
    }

    public boolean getEfeito() {
        return efeito;
    }
}
