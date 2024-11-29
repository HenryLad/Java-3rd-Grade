public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        
        Thread Green = new Thread(
            () -> stopWatch.countDown(7),ThreadColor.ANSI_GREEN.name());
        Thread Blue = new Thread(
            () -> stopWatch.countDown(7),ThreadColor.ANSI_BLUE.name());
        Thread Red = new Thread(
            () -> stopWatch.countDown(7),ThreadColor.ANSI_RED.name());
        

        Green.start();
        Blue.start();
        Red.start();
        

    }
}

class StopWatch {
    private int i;
    public StopWatch() {

    }
    


    public void countDown(int startCount) {

        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException ignore) {
            // ignore     
        }

        String color = threadColor.color();

        for (i = startCount; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s%s: %d%n", color, threadName, i);
        }
    }
}