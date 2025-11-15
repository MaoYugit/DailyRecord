package learn.maoyu.oop.threegood.inheritance;

public class Teacher extends Person{
    // 老师特有的属性：科目
    private String subject;

    // 构造函数
    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    // 老师特有的行为
    public void teach() {
        System.out.println(getName() + " 老师正在教 " + this.subject);
    }

    // GET/ SET
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
