/* Мир не меняется, меняемся мы.
Разберитесь с ConcurrentHashMap
В отдельном файле создайте класс Producer, который будет:
1. каждые полсекунды выводить на консоль с новой строки начиная с 1 фразу [Some text for i] , пример "Some text for 1"
2. при возникновении исключения выводить в консоль [[TREAD_NAME] thread was terminated], пример "[thread-1] thread was terminated"
*/
public class Solution {
    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        Producer producer = new Producer(map);
        Consumer consumer = new Consumer(map);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer);
        executorService.submit(consumer);

        Thread.sleep(2000);

        executorService.shutdownNow();
    }
}
***********************************************************************
public class Producer implements Runnable
{
    protected ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map){this.map = map;}

    public void run()
    {
        int i = 0;
        try {
            while (true) {
                map.put(String.valueOf(i++), "Some text for " + i);
                Thread.sleep(500);
            }
        }
        catch (Exception e) {
            System.out.println(String.format("[%s] thread was terminated", Thread.currentThread().getName()));
        }
    }
}
***********************************************************************
public class Consumer implements Runnable {
    protected ConcurrentHashMap<String, String> map;

    public Consumer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    public void run() {
        Thread currentThread = Thread.currentThread();
        while (!currentThread.isInterrupted()) {
            if (!map.isEmpty()) {
                for (String key : map.keySet()) {
                    System.out.println(map.remove(key));
                }
            }
        }
    }
}
*********************************************************************
Some text for 1
Some text for 2
Some text for 3
Some text for 4
[pool-1-thread-1] thread was terminated
