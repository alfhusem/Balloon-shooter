package app;

public class Bow {
	
	private double posX = 140;
	private double posY = 400;
	private double arrowAdjust = 35;
	
	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void cockBow(Arrow arrow) {
		
		arrow.setPosX(this.posX); 
		arrow.setPosY(this.posY + arrowAdjust);
		arrow.setSpeedY(0);
		arrow.setSpeedX(0);
	}
	
	public void shootBow(Arrow arrow, double power, double mouseAngle) {
	
		arrow.setSpeedX(power*Math.cos(mouseAngle));
		arrow.setSpeedY(power*Math.sin(mouseAngle));
	}
}
