package app;

public class Player implements Projectile {

	private double speedX = 0;
	private double speedY = 0;
	private double posX = 280;
	private double posY = 300;
	private double width = 53;
	private double height = 77; //79
	private double hitboxU = 18;
	private double hitboxL = 7;
	private double hitboxR = 5;
	private boolean playerFree;
	private double posY0;
	public boolean isPlayerFree() {
		return playerFree;
	}

	public void setPlayerFree(boolean playerFree) {
		this.playerFree = playerFree;
	}

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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void fall() {
		this.speedY += gravity * timeConst;
		if (this.speedY > terminalV) {
			this.speedY = terminalV;
		}
		this.posY += this.speedY * timeConst;
	}

	public void fly() {
		this.posX += this.speedX * timeConst;
	}

	public void launchPlayer(double power, double mouseAngle) {
		this.setSpeedX(power * Math.cos(mouseAngle));
		this.setSpeedY(power * Math.sin(mouseAngle));
	}
	
	//Vertical Y
	public void handleVerticalCollision(Platform platform) {
		if (posY + height > platform.getPosY() && posY + hitboxU < platform.getPosY() + platform.getHeight()
				&& posX + width - hitboxR > platform.getPosX() && posX + hitboxL < platform.getPosX() + platform.getWidth()) {

			// teleport out of platform
			if(posY + height < platform.getPosY() + platform.getHeight()/2) {
				//on top of platform
				posY = platform.getPosY() - height;
				speedY = 0;
			}
			else if(posY > platform.getPosY() + platform.getHeight()/2) {
				//below platform
				posY = platform.getPosY() + platform.getHeight() - hitboxU;
				speedY = -speedY/10;
			}
			
			speedX = 0;
			playerFree = false;

		} 
		else {
			playerFree = true;

		}
	}
	//Horisontal X
	public void handleHorisontalCollision(Platform platform) {
		if (posY + height > platform.getPosY() && posY < platform.getPosY() + platform.getHeight() &&
				posX + width - hitboxR > platform.getPosX() && posX + hitboxL < platform.getPosX() + platform.getWidth()) {


			// teleport out of platform
			if (posX + width < platform.getPosX() + platform.getWidth()/2 && Math.abs(speedX) > Math.abs(speedY)) {
				posX = platform.getPosX() - width + hitboxR;
			} else if (posX > platform.getPosX() + platform.getWidth()/2 && Math.abs(speedX) > Math.abs(speedY)) {
				posX = platform.getPosX() + platform.getWidth() - hitboxL;
			}
			
			speedX = -speedX/10;
			speedY = 0;
			playerFree = false;

		} 
		else {
			playerFree = true;
		}
	}
}
