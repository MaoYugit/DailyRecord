package heigh_level;

public class ThreadCreationPractice {
    public static void main(String[] args) {
        System.out.println("主线程开始...");

        DownloaderThread downloader1 = new DownloaderThread("http://example.com/movie.mp4");
        DownloaderThread downloader2 = new DownloaderThread("http://example.com/music.mp3");

        downloader1.start();
        downloader2.start();

        System.out.println("主线程继续做其他事情...");
    }
}

class DownloaderThread extends Thread {
    private String url;

    public DownloaderThread(String url) {
        this.url = url;
    }

    // 2. 重写 run 方法，定义线程要干的活
    @Override
    public void run() {
        System.out.println("线程 " + Thread.currentThread().getName() + " 开始下载: " + url);
        try {
            // 模拟下载耗时
            Thread.sleep(3000); // 让线程休眠3秒 (3000毫秒)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程 " + Thread.currentThread().getName() + " 下载完成!");
    }
}
