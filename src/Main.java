import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();
        FileReader fr = new FileReader("Employees.txt");
        BufferedReader br = new BufferedReader(fr);

        int minSalary = 0;
        int maxSalary = 0;
        int numbers = 0;

        String row;
        while ((row = br.readLine()) != null){
            sb.append(row).append("\n");
            numbers++;
        }
        br.close();

        numbers = numbers - 2;

        String[] content = sb.toString().split("\\r?\\n");

        minSalary = Integer.parseInt(content[0]);
        maxSalary = Integer.parseInt(content[1]);


        ArrayList <Employee> employees = new ArrayList<>(numbers);

        Info(content,numbers, employees);

        program(employees, minSalary, maxSalary, numbers);

    }


 public static void program(ArrayList <Employee> employees, int minSalary, int maxSalary, int numbers){

            Scanner sc = new Scanner(System.in);
            System.out.println("Please insert a value to calculate the required number of salary brackets");

            int k = sc.nextInt();


                TreeMap<Integer, ArrayList<Employee>> brackets = new TreeMap<>();

                for (int i = 0; i < k; i++) {
                    brackets.put(i, new ArrayList<>());
                }

                int rangeBracket = range(maxSalary, minSalary, k);
                int rangeSalary = 0;

                boolean repeat = true;
                boolean update = true;
                while (repeat) {

                    System.out.println("Would you like to add a new employee?");
                    System.out.println("Press 1 to add");
                    System.out.println("Press 2 to not add");

                    int userChoice = sc.nextInt();

                    if (userChoice == 1) {

                        System.out.println("Please insert a new ID");

                        int newID = sc.nextInt();
                        int newSalary = 0;

                        boolean sameID = employeeInfo(employees, newID);

                        if (!sameID) {
                            System.out.println("The new employee will be added to the list");

                            boolean salaryRepeat = true;

                            while (salaryRepeat) {
                                System.out.println("Please insert their salary");
                                newSalary = sc.nextInt();
                                boolean salaryHLCheck = employeeSalary(newSalary, minSalary, maxSalary);

                                if (salaryHLCheck) {
                                    System.out.println("The new salary is either too high or too low. Please try again");
                                    salaryRepeat = true;
                                    update = false;
                                } else {
                                    System.out.println("The new salary is added");
                                    salaryRepeat = false;
                                    update = true;
                                }
                            }

                        } else {
                            System.out.println("The Employee exists. Please try again.");
                            repeat = true;
                        }

                        employees.add(new Employee(newID, newSalary));
                    } else if (userChoice == 2) {
                        break;
                    }
                }

     for (Employee e : employees) {
         rangeSalary = salaryBracket(rangeBracket, minSalary, e.getSalary());

         if (brackets.containsKey(rangeSalary)) {
             brackets.get(rangeSalary).add(e);
         } else {
             ArrayList<Employee> newEmployee = new ArrayList<>();
             newEmployee.add(e);
             brackets.put(rangeSalary, newEmployee);
         }
     }

     System.out.println("******************************");
     System.out.println("Employee List");
     for (Map.Entry<Integer, ArrayList<Employee>> entry : brackets.entrySet()) {
         int startBracket = minSalary + (entry.getKey() * rangeBracket);
         int endBracket = Math.min(startBracket + rangeBracket, maxSalary);

         if (entry.getKey() >= 0 && entry.getKey() != k) {
             System.out.println("----------------------------------------------------------------------------------------------------------------");
             System.out.println("Bracket #" + (entry.getKey() + 1) + " Starting Point:" + startBracket + "$" + " Ending Point:" + endBracket + "$");
             for (Employee e : entry.getValue()) {
                 System.out.println("Employee ID: " + e.getId() + ", Salary: " + e.getSalary());
             }
         }
     }

    }

    public static int range(int maxSalary, int minSalary,  int k){
       int range = (maxSalary - minSalary) / k;
        return range;
    }

    public static int salaryBracket(int rangeBracket, int minSalary, int salary){
        int range = (salary - minSalary) / rangeBracket;
        return range;
    }

    public static boolean employeeInfo(ArrayList <Employee> employees, int newID){


        for (Employee e : employees){

            if (e.getId() == newID){
                return true;
            }
        }
        return false;
    }

    public static boolean employeeSalary( int newSalary, int minSalary, int maxSalary){

            if (newSalary < minSalary || newSalary > maxSalary){
                return true;
            }
      return false;
    }

    public static void Info(String[] content, int numbers, ArrayList<Employee> employees){
        int [] ID = new int[numbers];
        int [] salary = new int[numbers];

        for (int i = 2 ; i < content.length; i++) {
            String[] category = content[i].split("\\s+");
            ID[i - 2] = Integer.parseInt(category[0]);
            salary[i - 2] = Integer.parseInt(category[1]);
        }

        for (int i = 0; i < numbers; i++) {
            int sal = salary[i];
            int id = ID[i];
            employees.add(new Employee(id, sal));
        }
    }

}


class Employee {
    private int id;
    private int salary;
    public Employee (int id, int salary){
        this.id = id;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

}



