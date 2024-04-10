import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StudentController {
    private List<Students> studentList;

    public StudentController(String fileName) {
        this.studentList = readStudentsFromXML(fileName);
    }

    public List<Students> getStudentList() {
        return studentList;
    }

    public void displayStudents() {
        studentList.forEach(student -> System.out.println(student));
    }

    public void calculateStudentAges() {
        studentList.forEach(student -> {
            int age = student.calculateAge();
            System.out.println("Tuổi của sinh viên " + student.getName() + ": " + age);
        });
    }

    public void encodeBirthDates() {
        studentList.forEach(student -> {
            String encodedDate = student.encodeBirthDate();
            System.out.println("Mã hóa ngày sinh của sinh viên " + student.getName() + ": " + encodedDate);
        });
    }

    public void checkPrimeBirthYears() {
        studentList.forEach(student -> {
            boolean isPrime = student.isPrimeYear();
            System.out.println("Sinh viên " + student.getName() + " có năm sinh là số nguyên tố: " + isPrime);
        });
    }
    	public boolean isPrime(int number) {
    	    if (number <= 1) {
    	        return false;
    	    }
    	    if (number <= 3) {
    	        return true;
    	    }
    	    if (number % 2 == 0 || number % 3 == 0) {
    	        return false;
    	    }

    	        	    boolean[] prime = new boolean[number + 1];
    	    for (int i = 0; i <= number; i++) {
    	        prime[i] = true; }
    	    for (int p = 2; p * p <= number; p++) {
    	        if (prime[p]) {
    	            for (int i = p * p; i <= number; i += p) {
    	                prime[i] = false;
    	            }
    	        }
    	    }
    	    return prime[number];
    	}
    public void writeResultsToXML(String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("results");
            doc.appendChild(rootElement);

            for (Students student : studentList) {
                Element studentElement = doc.createElement("student");

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(student.getName()));
                studentElement.appendChild(nameElement);

                Element birthdayElement = doc.createElement("birthday");
                birthdayElement.appendChild(doc.createTextNode(student.getBirthday()));
                studentElement.appendChild(birthdayElement);

                Element ageElement = doc.createElement("age");
                ageElement.appendChild(doc.createTextNode(Integer.toString(student.calculateAge())));
                studentElement.appendChild(ageElement);

                Element encodedBirthdayElement = doc.createElement("encodedBirthday");
                encodedBirthdayElement.appendChild(doc.createTextNode(student.encodeBirthDate()));
                studentElement.appendChild(encodedBirthdayElement);

                Element isPrimeElement = doc.createElement("isPrimeBirthYear");
                isPrimeElement.appendChild(doc.createTextNode(Boolean.toString(student.isPrimeYear())));
                studentElement.appendChild(isPrimeElement);

                rootElement.appendChild(studentElement);
            }

            FileWriter writer = new FileWriter(fileName);
            javax.xml.transform.TransformerFactory.newInstance().newTransformer().transform(
                    new javax.xml.transform.dom.DOMSource(doc), new javax.xml.transform.stream.StreamResult(writer));
            writer.close();

            System.out.println("Kết quả đã được ghi vào file: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Students> readStudentsFromXML(String fileName) {
        List<Students> studentList = new ArrayList<>();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File " + fileName + " không tồn tại.");
                return studentList;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("student");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();
                    String birthday = element.getElementsByTagName("birthday").item(0).getTextContent();
                    studentList.add(new Students(id, name, address, birthday));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
