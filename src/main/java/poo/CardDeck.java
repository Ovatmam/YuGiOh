package poo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardDeck {
	public static int NCARDS = 3;
	private ArrayList<Card> cartas;
	private Card selected;
	private List<GameListener> observers;

	public CardDeck() {
		cartas = new ArrayList<>(NCARDS);
		selected = null;
		Random r = new Random();
		for (int i = 0; i < NCARDS; i++) {
			int n = r.nextInt(3) + 1;
			Card c = new Card("C" + n, "img" + n);
			c.flip();
			setCardStats(c);
		}
		observers = new LinkedList<>();
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cartas);
	}

	public int getNumberOfCards() {
		return cartas.size();
	}

	public void drawCard() {
		Random r = new Random();
		int n = r.nextInt(10) + 1;
		Card c = new Card("C" + n, "img" + n);
		c.flip();
		cartas.add(c);
	}

	public void removeSel() {
		if (selected == null) {
			return;
		}
		cartas.remove(selected);
		selected = null;
		GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.REMOVESEL, "");
		for (var observer : observers) {
			observer.notify(gameEvent);
		}
	}

	

	public void setSelectedCard(Card card) {
		selected = card;
	}

	public Card getSelectedCard() {
		return selected;
	}

	public void addGameListener(GameListener listener) {
		observers.add(listener);
	}

	private void setCardStats(Card c) {
		switch (c.getImageId()) {
			case "img1":
				CardMonstro c1 = new CardMonstro(c.getId(), c.getImageId());
				c1.setName("Dragão Branco de Olhos Azuis");
				c1.setAtk(3000);
				c1.setDef(2500);
				cartas.add(c1);
				break;
			case "img2":
				CardMonstro c2 = new CardMonstro(c.getId(), c.getImageId());
				c2.setName("Dragão Negro de Olhos Vermelhos");
				c2.setAtk(2400);
				c2.setDef(2000);
				cartas.add(c2);
				break;
			case "img3":
				CardMonstro c3 = new CardMonstro(c.getId(), c.getImageId());
				c3.setName("Mago Negro");
				c3.setAtk(2500);
				c3.setDef(2100);
				cartas.add(c3);
				break;
			case "img4":
				CardEfeito c4 = new CardEfeito(c.getId(), c.getImageId());
				c4.setName("Grande Escudo Gardna");
				c4.setAtk(100);
				c4.setDef(2600);
				cartas.add(c4);
				break;
			
			case "img5":
				c.setName("Obelisco o Atormentador");
				//atk = 4000;
				//def = 4000;
				//efeito ??????????????????????????????????????????????????????????????????????????????????????????????????????????????
				break;
			case "img6":
				break;
			case "img7":
				break;
			case "img8":
				break;
			case "img9":
				break;
			case "img10":
				break;
		}
	}
}
