public class Plane implements Flyable{
    @Override
    public void fly() {
        System.out.println("波音747正在高空巡航，速度 " + MAX_FLY_SPEED);
    }
}
