package poo;

public class CardMonstro extends Card{
	
	private int atk;
	private int def;
	private boolean defMode;

	public CardMonstro(String anId, String anImageId) {
		super(anId, anImageId);
		defMode = false;
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

	public void changeMode() {
		defMode = !defMode;
	}

	public boolean isDefending() {
		return defMode;
	}
}
