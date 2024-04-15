import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        Info(content,numbers,employees);

        program(employees, minSalary, maxSalary);
    }


    public static void program(ArrayList<Employee> employees, int minSalary, int maxSalary){

            Scanner sc = new Scanner(System.in);
            System.out.println("Please insert a value to calculate the required number of salary brackets");

            int k = sc.nextInt();

            int[] bracket = salaryBracket(k, minSalary, maxSalary);

        boolean repeat = true;
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

                    while (salaryRepeat){
                        System.out.println("Please insert their salary");
                        newSalary = sc.nextInt();
                        boolean salaryHLCheck = employeeSalary(newSalary, minSalary, maxSalary);

                        if (salaryHLCheck){
                            System.out.println("The new salary is either too high or too low. Please try again");
                            salaryRepeat = true;
                        } else {
                            System.out.println("The new salary is added");
                            salaryRepeat = false;
                        }
                    }

                  //  employees.add(new Employee(newID,newSalary));

                    TreeMap<Integer, ArrayList<Employee>> brackets = new TreeMap<>();

                    for (int i = 0; i < k; i++) {

                    }


                } else {
                    System.out.println("The Employee exists. Please try again.");
                    repeat = true;
                }

            } else if (userChoice == 2) {
                break;
            }
        }

    }


    public static int[] salaryBracket(int k, int minSalary, int maxSalary){
        int bracket [] = new int[k];

        int percentageRange = (maxSalary - minSalary) / k;

        for (int i = 0; i < k; i++) {
            bracket[i] = minSalary + percentageRange * (i+1);
        }
        return bracket;
    }

    public static boolean employeeInfo(List<Employee> employees, int newID){

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

    public void display(){
        System.out.println(id+" "+salary);
    }


}



