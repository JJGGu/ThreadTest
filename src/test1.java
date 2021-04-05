public class test1 {
    public static void main(String[] args) {
        int M = 10, N = 100;
        Object o = new Object();
//        for (int i = 0; i < M; i++) {
//            new Thread(new PrintRunnable(i + 1, M, N, o)).start();
//        }
        for (int i = 0; i < M; i++) {
            new Thread(new PrintRunnable1(i + 1, M, N)).start();
        }
    }
}

// M 个线程  打印 N个数
// 加锁方式实现  （最后可能出现死锁，需要解决）
class PrintRunnable implements Runnable {
    public static volatile int curPrintNum = 1;
    private int threadId;
    private int maxThread;
    private int maxNum;
    private Object o;
    public PrintRunnable(int threadId, int maxThread, int maxNum, Object o) {
        this.threadId = threadId;
        this.maxThread = maxThread;
        this.maxNum = maxNum;
        this.o = o;
    }
    @Override
    public void run() {
        while (curPrintNum <= maxNum) {
            synchronized (o) {
                if ((curPrintNum - threadId) % maxThread == 0) {
                    System.out.println("threadId: " + threadId + " curNum: " + curPrintNum);
                    ++curPrintNum;
                    o.notifyAll();
                } else {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


// 不加锁方式实现
class PrintRunnable1 implements Runnable {
    public static volatile int curPrintNum = 1;
    private int threadId;
    private int maxThread;
    private int maxNum;
    public PrintRunnable1(int threadId, int maxThread, int maxNum) {
        this.threadId = threadId;
        this.maxThread = maxThread;
        this.maxNum = maxNum;
    }
    @Override
    public void run() {
        while (curPrintNum <= maxNum) {
                if ((curPrintNum - threadId) % maxThread == 0) {
                    System.out.println("threadId: " + threadId + " curNum: " + curPrintNum);
                    ++curPrintNum;
                }
        }
    }
}