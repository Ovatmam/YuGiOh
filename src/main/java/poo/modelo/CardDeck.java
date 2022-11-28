package poo.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//import poo.modelo.GameEvent.Action;
//import poo.modelo.GameEvent.Target;

public class CardDeck {
	public static int NCARDS = 5;
	private List<Card> cartas;
	private Card selected;
	private List<GameListener> observers;

	public CardDeck(int nroCartas) {
		cartas = new ArrayList<>(nroCartas);
		selected = null;
		Random r = new Random();
		for (int i = 0; i < nroCartas-1; i++) {
			int n = r.nextInt(6) + 1;
			Card c = new Card("C" + n, "img" + n);
			setCardStats(c);
		}
		setCardStats(new Card("C6", "img6"));
		observers = new LinkedList<>();
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cartas);
	}

	public int getNumberOfCards() {
		return cartas.size();
	}

	public boolean isEmpty() {
		return cartas.size() == 0;
	}

	public void clear() {
		cartas.clear();
	}

	public boolean hasMonsters() {
		for(Card c : cartas) {
			if(c instanceof CardMonstro)
				return true;
		}
		return false;
	}

	public void drawCard() {
		Random r = new Random();
		int n = r.nextInt(6) + 1;
		Card c = new Card("C" + n, "img" + n);
		addCard(c);
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

	public void addCard(Card card) {
		System.out.println("add: "+ card);
		setCardStats(card);
		GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, "");
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

	public void resetCardsAttacks() {
		for(Card c : cartas) {
			if(c instanceof CardEfeito) {
				CardEfeito ce = (CardEfeito) c;
				ce.resetAttack();
			}
			else if(c instanceof CardMonstro) {
				CardMonstro cm = (CardMonstro) c;
				cm.resetAttack();
			}
		}
	}

	private void setCardStats(Card c) {
		switch (c.getImageId()) {
			case "img1":
				CardMonstro c1 = new CardMonstro(c.getId(), c.getImageId());
				c1.setName("Dragão Branco de Olhos Azuis");
				c1.setAtk(3000);
				c1.setDef(2500);
				if(c.isDefending())
					c1.changeMode();
				cartas.add(c1);
				break;
			case "img2":
				CardMonstro c2 = new CardMonstro(c.getId(), c.getImageId());
				c2.setName("Dragão Negro de Olhos Vermelhos");
				c2.setAtk(2400);
				c2.setDef(2000);
				if(c.isDefending())
					c2.changeMode();
				cartas.add(c2);
				break;
			case "img3":
				CardMonstro c3 = new CardMonstro(c.getId(), c.getImageId());
				c3.setName("Mago Negro");
				c3.setAtk(2500);
				c3.setDef(2100);
				cartas.add(c3);
				if(c.isDefending())
					c3.changeMode();
				break;
			case "img4":
				CardEfeito c4 = new CardEfeito(c.getId(), c.getImageId());
				c4.setName("Grande Escudo Gardna");
				c4.setAtk(100);
				c4.setDef(2600);
				if(c.isDefending())
					c4.changeMode();
				cartas.add(c4);
				break;
			
			case "img5":
				CardEfeito c5 = new CardEfeito(c.getId(), c.getImageId());
				c5.setName("Obelisco o Atormentador");
				c5.setAtk(4000);
				c5.setDef(4000);
				if(c.isDefending())
					c5.changeMode();
				cartas.add(c5);
				break;
			case "img6":
				CardMagia c6 = new CardMagia(c.getId(), c.getImageId());
				c6.setName("Polimerização");
				if(!c.isFacedUp())
					c6.flip();
				cartas.add(c6);
				break;
		}
	}
}
