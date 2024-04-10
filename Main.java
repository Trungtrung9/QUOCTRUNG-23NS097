import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentController controller = new StudentController("students.xml");
        Thread thread1 = new Thread(() -> {
            controller.displayStudents();
        });

        Thread thread2 = new Thread(() -> {
            try {
               
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.calculateStudentAges();
            controller.encodeBirthDates();
        });

        Thread thread3 = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.checkPrimeBirthYears();
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        controller.writeResultsToXML("kq.xml");
    }
}
