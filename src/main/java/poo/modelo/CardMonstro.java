package poo.modelo;

public class CardMonstro extends Card{
	
	private int atk;
	private int def;

	public CardMonstro(String anId, String anImageId) {
		super(anId, anImageId);
	}

	public boolean setAtk(int atk) {
		if(this.atk == 0) {
			this.atk = atk;
			return true;
		}
		return false;
	}

	public int getAtk() {
		return atk;
	}

	public boolean setDef(int def) {
		if(this.def == 0) {
			this.def = def;
			return true;
		}
		return false;
	}

	public int getDefense() {
		return def;
	}
}