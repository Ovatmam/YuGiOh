package poo.modelo;

public class CardMonstro extends Card{
	
	private int atk;
	private int def;
	private boolean attacked;

	public CardMonstro(String anId, String anImageId) {
		super(anId, anImageId);
		attacked = false;
	}

	public void attack() {
		attacked = true;
	}

	public void resetAttack() {
		attacked = false;
	}

	public boolean hasAttacked() {
		return attacked;
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