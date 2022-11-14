package app;

public class Arrow implements Projectile{

	private double speedX = 0;
	private double speedY = 0;
	private double posX;
	private double posY;

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

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

	public void fall() {
		this.speedY += gravity*timeConst;
		if (this.speedY > terminalV) {
			this.speedY = terminalV;
		}
		this.posY += this.speedY*timeConst;
	}
	
	public void fly() {
		this.posX += this.speedX*timeConst;
	}

}
