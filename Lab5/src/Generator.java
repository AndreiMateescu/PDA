import java.util.Random;

public class Generator
{
    private Random randomGenerator;
    private int maxValue;
    Generator(int maxValue){
        randomGenerator = new Random();
        this.maxValue = maxValue;
    }

    public int next() {
        int value = randomGenerator.nextInt(maxValue);
        return value;
    }
}