package poo.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import poo.modelo.Game;
import poo.modelo.GameEvent;
import poo.modelo.GameListener;

public class PlacarView extends GridPane implements GameListener {
	private TextField ptsJ1, ptsJ2, player, qtdTurnos;

	public PlacarView() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Game.getInstance().addGameListener(this);

		ptsJ1 = new TextField();
		ptsJ2 = new TextField();
		player = new TextField();
		qtdTurnos = new TextField();

		ptsJ1.setText("" + Game.getInstance().getVidasJ1());
		ptsJ2.setText("" + Game.getInstance().getVidasJ2());
		player.setText("" + Game.getInstance().getPlayer());
		qtdTurnos.setText("" + Game.getInstance().getTurnos());

		this.add(new Label("Jogador 1:"), 0, 0);
		this.add(ptsJ1, 1, 0);
		this.add(new Label("Jogador Atual:"), 0, 1);
		this.add(player, 1, 1);
		this.add(new Label("Qtd. Turnos:"), 0, 2);
		this.add(qtdTurnos, 1, 2);
		this.add(new Label("Jogador 2:"), 0, 3);
		this.add(ptsJ2, 1, 3);
	}

	@Override
	public void notify(GameEvent event) {
		ptsJ1.setText("" + Game.getInstance().getVidasJ1());
		ptsJ2.setText("" + Game.getInstance().getVidasJ2());
		player.setText("" + Game.getInstance().getPlayer());
		qtdTurnos.setText("" + Game.getInstance().getTurnos());
	}
}
