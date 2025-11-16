public class Teacher extends Person{
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    public void teach() {
        System.out.println(getName() + "老师正在教" + this.subject);
    }

    @Override
    public void work() {
        System.out.println("老师的工作就是教书！");
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
