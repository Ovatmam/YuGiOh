package poo.modelo;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//import poo.modelo.GameEvent.Action;
//import poo.modelo.GameEvent.Target;

public class Game {
	private static Game game = new Game();
	private int vidasJ1, vidasJ2;
	private CardDeck deckJ1, deckJ2;
	private CardDeck mesaJ1, mesaJ2;
	private int player;
	private boolean placedMagicCard;
	private boolean placedMonsterCard;
	private boolean usedMagic;
	private int turnos;
	private List<GameListener> observers;
	
	public static Game getInstance() {
		return game;
	}

	private Game() {
		vidasJ1 = 2;
		vidasJ2 = 2;
		turnos = 1;
		deckJ1 = new CardDeck(CardDeck.NCARDS);
		deckJ2 = new CardDeck(CardDeck.NCARDS);
		mesaJ1 = new CardDeck(0);
		mesaJ2 = new CardDeck(0);
		player = 1;
		placedMagicCard = false;
		placedMonsterCard = false;
		usedMagic = false;
		observers = new LinkedList<>();
		mesaJ1.clear();
		mesaJ2.clear();
	}

	public void nextPlayer() {
		turnos++;
		player++;
		placedMagicCard = false;
		placedMonsterCard = false;
		if (player == 3) {
			player = 1;
		}
		GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWTABLE, "2");
		for (var observer : observers) {
			observer.notify(gameEvent);
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

	public void placeMagic() {
		placedMagicCard = true;
	}

	public boolean placedMagicCard() {
		return placedMagicCard;
	}

	public void placeMonster() {
		placedMonsterCard = true;
	}

	public boolean placedMonsterCard() {
		return placedMonsterCard;
	}

	public void play(CardDeck deckAcionado) {
		GameEvent gameEvent = null;
		if (deckAcionado == deckJ1) {
			if (player != 1) {	
				gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "2");
				for (var observer : observers) {
					observer.notify(gameEvent);
				}
			} 
			
		} else if (deckAcionado == deckJ2) {
			if (player != 2) {
				gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "1");
				for (var observer : observers) {
					observer.notify(gameEvent);
				}
			} 
		}
	}

	public void Erro(String str) {
		Alert alert;
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
		return;
	}

	public void attack() {
		if(turnos <= 2)
			return;
		if(player == 1) {
			if(!(mesaJ1.getSelectedCard() instanceof CardMonstro) || mesaJ1.getSelectedCard().isDefending()) {
				Erro("Atacante invalido");
				return;
			}
			else {
				CardMonstro c = (CardMonstro)mesaJ1.getSelectedCard();
				if(!c.hasAttacked()) {
					if(!mesaJ2.hasMonsters() && !c.equals(null)) {
						c.attack();
						vidasJ2--;
						if(vidasJ2 == 0)
							finalizarTurno();
						GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, "");
						for (var observer : observers) {
							observer.notify(gameEvent);
						}
						return;
					}
				}
			}
		}
		if(player == 2) {
			if(!(mesaJ2.getSelectedCard() instanceof CardMonstro) || mesaJ2.getSelectedCard().isDefending()) {
				Erro("Atacante invalido");
				return;
			}
			else {
				CardMonstro c = (CardMonstro)mesaJ2.getSelectedCard();
				if(!c.hasAttacked()) {
					if(!mesaJ1.hasMonsters() && !c.equals(null)) {
						c.attack();
						vidasJ1--;
						if(vidasJ1 == 0)
							finalizarTurno();
						GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, "");
						for (var observer : observers) {
							observer.notify(gameEvent);
						}
						return;
					}
				}
			}
		}
		CardMonstro c1 = (CardMonstro)mesaJ1.getSelectedCard();
		CardMonstro c2 = (CardMonstro)mesaJ2.getSelectedCard();

		if(c1.equals(null) || c2.equals(null)) {
			Erro("Alguma das cartas consta como null");
			return;
		}
		//Marcar que a carta já atacou
		if(player == 1) {
			if(((CardMonstro)mesaJ1.getSelectedCard()).hasAttacked()) {
				Erro("Essa carta já atacou");
				return;
			}
			c1.attack();
		} 
		else if(player == 2) {
			if(((CardMonstro)mesaJ2.getSelectedCard()).hasAttacked()) {
				Erro("Essa carta já atacou");
				return;
			}
			c2.attack();
		}

		if(c2.isDefending()) {
			if(c1.getAtk() > c2.getDefense()) {  //Carta 1 com ataque maior que a defesa da carta 2
				mesaJ2.removeSel();
				return;
			}
			if(c1.getAtk() < c2.getDefense()) { //Carta 1 com ataque menor que a defesa da carta 2
				mesaJ1.removeSel();
				return;
			}
			else { //Empate
				mesaJ1.removeSel();
				mesaJ2.removeSel();
				return;
			}
		}
		else if(c1.isDefending()) {
			if(c2.getAtk() > c1.getDefense()) { //Carta 2 com ataque maior que a defesa da carta 1
				mesaJ1.removeSel();
				return;
			}
			if(c2.getAtk() < c1.getDefense()) { //Carta 1 com defesa maior que o ataque da carta 2
				mesaJ2.removeSel();
				return;
			}
			else { //Empate
				mesaJ1.removeSel();
				mesaJ2.removeSel();
				return;
			}
		}
		else { //As duas cartas estão em modo de ataque (posição vertical)
			if(c1.getAtk() > c2.getAtk()) { //Carta 1 com ataque maior que a carta 2
				mesaJ2.removeSel();
				return;
			}
			else if(c1.getAtk() < c2.getAtk()) { //Carta 1 com ataque menor que a carta 2
				mesaJ1.removeSel();
				return;
			}
			else { //Empate
				mesaJ1.removeSel();
				mesaJ2.removeSel();
				return;
			}

		}
	}

	public void useCardEffect() {
		if(player == 1) {
			if(mesaJ1.getSelectedCard().equals(null) || mesaJ2.isEmpty()) {
				Erro("Sem carta selecionada ou a mesa do oponente está vazia");
				return;
			}
			if(mesaJ1.getSelectedCard() instanceof CardEfeito && !mesaJ2.getSelectedCard().equals(null)) {
				if(!((CardEfeito)mesaJ1.getSelectedCard()).hasEfeito()) {
					Erro("Efeito já utilizado");
					return;
				}
				((CardEfeito)mesaJ1.getSelectedCard()).useEffect();
				mesaJ2.removeSel();
			}
			if(!usedMagic && mesaJ1.getSelectedCard() instanceof CardMagia && !mesaJ2.getSelectedCard().equals(null)) {
				if(!((CardMagia)mesaJ1.getSelectedCard()).isUsable())
					return;
				((CardMagia)mesaJ1.getSelectedCard()).useCard();
				mesaJ1.removeSel();
				mesaJ2.removeSel();
				usedMagic = true;
			}
		}
		if(player == 2) {
			if(mesaJ2.getSelectedCard().equals(null) || mesaJ1.isEmpty()) {
				Erro("Sem carta selecionada ou a mesa do oponente está vazia");
				return;
			}
			if(mesaJ2.getSelectedCard() instanceof CardEfeito && !mesaJ1.getSelectedCard().equals(null)) {
				if(!((CardEfeito)mesaJ2.getSelectedCard()).hasEfeito()) {
					Erro("Efeito já utilizado");
					return;
				}
				((CardEfeito)mesaJ2.getSelectedCard()).useEffect();
				mesaJ1.removeSel();
			}
			if(!usedMagic && mesaJ2.getSelectedCard() instanceof CardMagia && !mesaJ1.getSelectedCard().equals(null)) {
				if(!((CardMagia)mesaJ2.getSelectedCard()).isUsable())
					return;
				((CardMagia)mesaJ2.getSelectedCard()).useCard();
				mesaJ1.removeSel();
				mesaJ2.removeSel();
				usedMagic = true;
			}
		}

		GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWTABLE, "2");
		for (var observer : observers) {
			observer.notify(gameEvent);
		}
	}

	public void drawCards() {
		if(player == 2)
			deckJ2.drawCard();
		if(player == 1)
			deckJ1.drawCard();
	}

	public void changeMode() {
		if(player == 1) {
			if(mesaJ1.getSelectedCard() instanceof CardEfeito) {
				CardEfeito c = (CardEfeito) mesaJ1.getSelectedCard();
				c.changeMode();
				mesaJ1.removeSel();
				mesaJ1.addCard(c);
			}
			else if(mesaJ1.getSelectedCard() instanceof CardMonstro) {
				CardMonstro c = (CardMonstro) mesaJ1.getSelectedCard();
				c.changeMode();
				mesaJ1.removeSel();
				mesaJ1.addCard(c);
			}
		}
		else if(player == 2) {
			if(mesaJ2.getSelectedCard() instanceof CardEfeito) {
				CardEfeito c = (CardEfeito) mesaJ2.getSelectedCard();
				c.changeMode();
				mesaJ2.removeSel();
				mesaJ2.addCard(c);
			}
			if(mesaJ2.getSelectedCard() instanceof CardMonstro) {
				CardMonstro c = (CardMonstro) mesaJ2.getSelectedCard();
				c.changeMode();
				mesaJ2.removeSel();
				mesaJ2.addCard(c);
			}
		}
		GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWTABLE, "2");
		for (var observer : observers) {
			observer.notify(gameEvent);
		}
	}

	public void finalizarTurno() {
		mesaJ1.resetCardsAttacks();
		mesaJ2.resetCardsAttacks();
		GameEvent gameEvent = null;
		gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWTABLE, "");
		for (var observer : observers) {
			observer.notify(gameEvent);
		}
		if (vidasJ1 == 0 || vidasJ2 == 0) {
			gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.ENDGAME, "");
			for (var observer : observers) {
				observer.notify(gameEvent);
			}
			return;
		}
		nextPlayer();
	}
	
	public void addGameListener(GameListener listener) {
		observers.add(listener);
	}
}
