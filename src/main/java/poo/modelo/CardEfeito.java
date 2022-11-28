package poo.modelo;

public class CardEfeito extends CardMonstro{

    private boolean efeito;

    public CardEfeito(String anId, String anImageId) {
        super(anId, anImageId);
        efeito = true;
    }

    public void useEffect() {
        efeito = false;
    }

    public boolean hasEfeito() {
        return efeito;
    }
}