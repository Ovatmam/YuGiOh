package poo.modelo;

import java.util.LinkedList;
import java.util.List;

//import poo.modelo.GameEvent.Action;
//import poo.modelo.GameEvent.Target;

public class Game {
	private static Game game = new Game();
	private int vidasJ1, vidasJ2;
	private CardDeck deckJ1, deckJ2;
	private CardDeck mesaJ1, mesaJ2;
	private int player;
	private int jogadas;
	private int turnos;
	private List<GameListener> observers;
	
	public static Game getInstance() {
		return game;
	}

	private Game() {
		vidasJ1 = 1;
		vidasJ2 = 1;
		turnos = 1;
		deckJ1 = new CardDeck(CardDeck.NCARDS);
		deckJ2 = new CardDeck(CardDeck.NCARDS);
		mesaJ1 = new CardDeck(0);
		mesaJ2 = new CardDeck(0);
		player = 1;
		jogadas = CardDeck.NCARDS;
		observers = new LinkedList<>();
	}

	public void nextPlayer() {
		turnos++;
		player++;
		if (player == 4) {
			player = 1;
		}
	}

	public int getPlayer() {
		return player;
	}

	public int getTurnos() {
		return turnos;
	}

	public int getVidasJ1() {
		return vidasJ1;
	}

	public int getVidasJ2() {
		return vidasJ2;
	}

	public CardDeck getDeckJ1() {
		return deckJ1;
	}

	public CardDeck getDeckJ2() {
		return deckJ2;
	}

	public CardDeck getMesaJ1() {
		return mesaJ1;
	}

	public CardDeck getMesaJ2() {
		return mesaJ2;
	}

	public void play(CardDeck deckAcionado) {
		GameEvent gameEvent = null;
		if (player == 3) {
			gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.MUSTCLEAN, "");
			for (var observer : observers) {
				observer.notify(gameEvent);
			}
			return;
		}
		if (deckAcionado == deckJ1) {
			if (player != 1) {	
				gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "2");
				for (var observer : observers) {
					observer.notify(gameEvent);
				}
			} else {
				// Vira a carta
				//deckJ1.getSelectedCard().flip();
				// Proximo jogador
				nextPlayer();
			}
		} else if (deckAcionado == deckJ2) {
			if (player != 2) {
				gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "2");
				for (var observer : observers) {
					observer.notify(gameEvent);
				}
			} else {
				// Vira a carta
				//deckJ2.getSelectedCard().flip();
				CardMonstro CardJ1 = new CardMonstro(deckJ1.getSelectedCard().getId(), deckJ1.getSelectedCard().getImageId());
				CardMonstro CardJ2 = new CardMonstro(deckJ2.getSelectedCard().getId(), deckJ2.getSelectedCard().getImageId());
				// Verifica quem ganhou a rodada
				if (CardJ1.getAtk() > CardJ2.getAtk()) {
					vidasJ1++;
				} else if (CardJ1.getAtk() < CardJ2.getAtk()) {
					vidasJ2++;
				}
				for (var observer : observers) {
					observer.notify(gameEvent);
				}
				// Próximo jogador
				nextPlayer();
			}
		}
	}


	public void drawCards() {
		if(player == 2)
			deckJ2.drawCard();
		if(player == 1)
			deckJ1.drawCard();
	}

	// Acionada pelo botao de limpar
	public void removeSelected() {
		GameEvent gameEvent = null;
		if (player != 3) {
			return;
		}
		jogadas--;
		if (jogadas == 0) {
			gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.ENDGAME, "");
			for (var observer : observers) {
				observer.notify(gameEvent);
			}
		}
		
		mesaJ1.addCard( deckJ1.getSelectedCard() );
		deckJ1.removeSel();
		
		mesaJ2.addCard( deckJ2.getSelectedCard() );
		deckJ2.removeSel();
		nextPlayer();
	}
	
	public void addGameListener(GameListener listener) {
		observers.add(listener);
	}
}
