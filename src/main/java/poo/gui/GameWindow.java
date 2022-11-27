package poo.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import poo.modelo.Card;
import poo.modelo.CardEfeito;
import poo.modelo.CardMonstro;
import poo.modelo.Game;
import poo.modelo.GameEvent;
import poo.modelo.GameListener;

public class GameWindow extends Application implements GameListener {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Game.getInstance().addGameListener(this);

		primaryStage.setTitle("Batalha de Cartas");

		Group root = new Group();

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Jogador 1");
        Tab tab2 = new Tab("Jogador 2");
        Tab tab3 = new Tab("Mesa");
        //Tab tab4 = new Tab("Mesa Jogador 2");

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);


		GridPane grid1 = new GridPane();
		grid1.setAlignment(Pos.CENTER);
		grid1.setHgap(10);
		grid1.setVgap(10);
		grid1.setPadding(new Insets(25, 25, 25, 25));

		DeckView deckJ1 = new DeckView(1);
		ScrollPane sd1 = new ScrollPane();
		sd1.setPrefSize(1200, 250);
		sd1.setContent(deckJ1);
		grid1.add(sd1, 0, 0);



		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));
		
		DeckView deckJ2 = new DeckView(2);
		ScrollPane sd2 = new ScrollPane();
		sd2.setPrefSize(1200, 250);
		sd2.setContent(deckJ2);
		grid2.add(sd2, 0, 0);



		GridPane grid3 = new GridPane();
		grid3.setAlignment(Pos.CENTER);
		grid3.setHgap(10);
		grid3.setVgap(10);
		grid3.setPadding(new Insets(25, 25, 25, 25));

		DeckView mesaJ1 = new DeckView(-1);
		ScrollPane sdM1 = new ScrollPane();
		sdM1.setPrefSize(1200, 250);
		sdM1.setContent(mesaJ1);
		grid3.add(sdM1, 1, 0);

		PlacarView placar = new PlacarView();
		grid3.add(placar, 1, 1);

		



		Button butDraw1 = new Button("Draw");
		Button butDraw2 = new Button("Draw");
		Button butSummon1 = new Button("Summon");
		Button butSummon2 = new Button("Summon");
		Button butSet1 = new Button("Set");
		Button butSet2 = new Button("Set");
		Button butAtk = new Button("Attack");
		Button butMode = new Button("Change Mode");
		Button butFinal = new Button("Finalizar Turno");

		butDraw1.setOnAction(e -> {Game.getInstance().drawCards();
								   Game.getInstance().getDeckJ1().setSelectedCard(null);
								   butDraw1.setDisable(true);
								  });
		grid1.add(butDraw1, 1, 0);

		butDraw2.setOnAction(e -> {Game.getInstance().drawCards();
								   Game.getInstance().getDeckJ2().setSelectedCard(null);
								   butDraw2.setDisable(true);
								  });
		grid2.add(butDraw2, 1, 0);

		butSummon1.setOnAction(e -> {Card c = Game.getInstance().getDeckJ1().getSelectedCard();
									 Game.getInstance().getMesaJ1().addCard(Game.getInstance().getDeckJ1().getSelectedCard());
									 Game.getInstance().getDeckJ1().removeSel();
									 Game.getInstance().getDeckJ1().setSelectedCard(c);
									 butSummon1.setDisable(true);
									 butSet1.setDisable(true);
									});
		grid1.add(butSummon1, 0, 1);

		butSummon2.setOnAction(e -> {Card c = Game.getInstance().getDeckJ2().getSelectedCard();
									 Game.getInstance().getMesaJ2().addCard(Game.getInstance().getDeckJ2().getSelectedCard());
									 Game.getInstance().getDeckJ2().removeSel();
									 Game.getInstance().getDeckJ2().setSelectedCard(c);
									 butSummon2.setDisable(true);
									 butSet2.setDisable(true);
									});
		grid2.add(butSummon2, 0, 1);
		
		butSet1.setOnAction(e -> {CardMonstro c = (CardMonstro) Game.getInstance().getDeckJ1().getSelectedCard();
									 c.changeMode();
									 Game.getInstance().getMesaJ1().addCard(c);
									 Game.getInstance().getDeckJ1().removeSel();
									 Game.getInstance().getDeckJ1().setSelectedCard(c);
									 butSet1.setDisable(true);
									 butSummon1.setDisable(true);
									});
		grid1.add(butSet1, 1, 1);

		butSet2.setOnAction(e -> {CardMonstro c = (CardMonstro) Game.getInstance().getDeckJ2().getSelectedCard();
									 c.changeMode();
									 Game.getInstance().getMesaJ2().addCard(c);
									 Game.getInstance().getDeckJ2().removeSel();
									 Game.getInstance().getDeckJ2().setSelectedCard(c);
									 butSet2.setDisable(true);
									 butSummon2.setDisable(true);
									});
		grid2.add(butSet2, 1, 1);

		butAtk.setOnAction(e->Game.getInstance().attack());
		grid3.add(butAtk, 0, 1);

		butMode.setOnAction(e->{
				if(Game.getInstance().getPlayer() == 1){
					if(Game.getInstance().getMesaJ1().getSelectedCard() instanceof CardEfeito) {
						CardEfeito c = (CardEfeito) Game.getInstance().getMesaJ1().getSelectedCard();
						c.changeMode();
						Game.getInstance().getMesaJ1().removeSel();
						Game.getInstance().getMesaJ1().addCard(c);
					}
					else if(Game.getInstance().getMesaJ1().getSelectedCard() instanceof CardMonstro) {
						CardMonstro c = (CardMonstro) Game.getInstance().getMesaJ1().getSelectedCard();
						c.changeMode();
						Game.getInstance().getMesaJ1().removeSel();
						Game.getInstance().getMesaJ1().addCard(c);
					}
				}
				else if(Game.getInstance().getPlayer() == 2) {
					if(Game.getInstance().getMesaJ2().getSelectedCard() instanceof CardEfeito) {
						CardEfeito c = (CardEfeito) Game.getInstance().getMesaJ2().getSelectedCard();
						c.changeMode();
						Game.getInstance().getMesaJ1().removeSel();
						Game.getInstance().getMesaJ1().addCard(c);
					}
					if(Game.getInstance().getMesaJ2().getSelectedCard() instanceof CardMonstro) {
						CardMonstro c = (CardMonstro) Game.getInstance().getMesaJ2().getSelectedCard();
						c.changeMode();
						Game.getInstance().getMesaJ1().removeSel();
						Game.getInstance().getMesaJ1().addCard(c);
					}
				}
			});
		grid3.add(butMode, 2, 0);

		butFinal.setOnAction(e -> {Game.getInstance().finalizarTurno();
								   //Game.getInstance().nextPlayer();
								   butDraw1.setDisable(false);
								   butDraw2.setDisable(false);
								   butSummon1.setDisable(false);
								   butSummon2.setDisable(false);
								   butSet1.setDisable(false);
								   butSet2.setDisable(false);
								   butAtk.setDisable(false);
								  }
							);
		grid3.add(butFinal, 2, 1);



		DeckView mesaJ2 = new DeckView(-2);
		ScrollPane sdM2 = new ScrollPane();
		sdM2.setPrefSize(1200, 250);
		sdM2.setContent(mesaJ2);
		grid3.add(sdM2, 1, 2);

		tab1.setContent(grid1);
        tab2.setContent(grid2);
        tab3.setContent(grid3);


		root.getChildren().add(tabPane);
		
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
	

	@Override
	public void notify(GameEvent eg) {
		Alert alert;
		if (eg == null) return;
		if (eg.getTarget() == GameEvent.Target.GWIN) {
			switch (eg.getAction()) {
			case INVPLAY:
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Atenção !!");
				alert.setHeaderText("Jogada inválida!!");
				alert.setContentText("Era a vez do jogador " + eg.getArg());
				alert.showAndWait();
				break;
			case MUSTCLEAN:
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Atenção !!");
				alert.setHeaderText(null);
				alert.setContentText("Utilize o botao \"Clean\"");
				alert.showAndWait();
				break;
			case ENDGAME:
				String text = "Fim de Jogo !!\n";
				if (Game.getInstance().getVidasJ1() > Game.getInstance().getVidasJ2()) {
					text += "O jogador 1 ganhou !! :-)";
				} else {
					text += "O jogador 2 ganhou !! :-)";
				}
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Parabens !!");
				alert.setHeaderText(null);
				alert.setContentText(text);
				alert.showAndWait();
				break;
			default:
			}
		}
	}

}
