package lesson4;

public class lesson4 {
    private static volatile lesson4  LS= null;

    // 双重校验锁
    public void getLesson() {
        if (LS == null) {
            synchronized (lesson4.class) {
                if (LS == null) {
                    LS = new lesson4();
                }
            }
        }
    }
}
