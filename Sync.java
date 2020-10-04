public class Sync implements Runnable {

    public volatile float balance = 0.0f;

    public static void main(String args[]) {
        Sync b = new Sync();
        Thread t1 = new Thread(b, "t1");
        Thread t2 = new Thread(b, "t2");
        Thread t3 = new Thread(b, "t3");
        t1.start();
        t2.start();
        t3.start();
    }

    public void run() {
        synchronized(this) {
            try{
                if (Thread.currentThread().getName() == "t1") {
                    this.save(100.0f);
                    // this.save(100.0f);
                    this.notify();
                }else{
                    if (this.balance == 0.0f)
                        this.wait();
                    this.draw(100.0f);
                }
            }catch (InterruptedException e) {

            }
        }
    }

    public void save(float balance) {
        synchronized(this) {
            System.out.println(Thread.currentThread().getName());
            this.balance += balance;
            System.out.println("save account balance: " + this.balance);
        }
    }

    public void draw(float balance) {
        synchronized(this) {
            System.out.println(Thread.currentThread().getName());
            if (this.balance < balance) {
                System.out.println("Insufficient Balance");
            }else{
                this.balance -= balance;
                System.out.println("draw account balance: " + this.balance);
            }
        }
    }
}