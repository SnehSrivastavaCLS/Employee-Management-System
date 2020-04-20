import java.io.*;
import java.util.*;

public class EmployeeManagement
{
    static BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String arg[])throws IOException
    {
        Functions f = new Functions();
        System.out.println("\t\tEMPLOYEE MANAGEMENT SYSTEM");
        while(true)
        {
            System.out.println("Enter Your Choice:");
            System.out.println("1.Display Employee Details\n"
                               +"2.Add Employee\n"
                               +"3.Remove Employee\n"
                               +"4.Update Salary\n"
                               +"5.Exit\n");
            char ch = br1.readLine().charAt(0);
            switch(ch)
            {
                case '1' : f.display();
                           break;  
                case '2' : System.out.println("Enter Employee's ID");
                           int id = Integer.parseInt(br1.readLine());
                           System.out.println("Enter Name");
                           String name = br1.readLine();
                           System.out.println("Enter Salary");
                           double salary = Double.parseDouble(br1.readLine());
                           f.addEmp(id, name, salary);
                           break;
                case '3' : System.out.println("Enter ID of the Employee to be removed");
                           int idd = Integer.parseInt(br1.readLine());
                           f.removeEmp(idd);
                           break;
                case '4' : System.out.println("Enter ID of the Employee whose salary is to be Updated");
                           int r = Integer.parseInt(br1.readLine());
                           f.updateSalary(r, br1);
                           break;

                case '5' : return;
                default: System.out.println("Invalid Input");
            }                        
        }                
    }
}

class DataBase
{
    ArrayList<Integer> Empid = new ArrayList<>();
    ArrayList<String> EmpName = new ArrayList<>();
    ArrayList<Double> EmpSalary = new ArrayList<>();

    public DataBase()
    {
        try
        {
            FileReader fr = new FileReader("database.txt");
            BufferedReader br = new BufferedReader(fr);
            String row = null;
            while((row=br.readLine())!=null)
            {
                StringTokenizer st = new StringTokenizer(row);
                Empid.add(Integer.parseInt(st.nextToken()));
                EmpName.add(st.nextToken());
                EmpSalary.add(Double.parseDouble(st.nextToken()));
            }
            br.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }    
    }
}

class Functions extends DataBase
{

    public void display()throws IOException
    {
        System.out.println("EMPLOYEE ID\tEMPLOYEE NAME\tEMPLOYEE SALARY");
        for(int i=0;i<Empid.size();i++)
            System.out.println(Empid.get(i)+"\t\t"+EmpName.get(i)+"\t\t"+EmpSalary.get(i));
    }

    public void addEmp(int id,String name,double sal)throws IOException
    {
        int index = Empid.indexOf(id);
        if(index != -1)
        {
            System.out.println("Two Employees cannot have same ID\nUse a different Employee ID and Try Again");
            return;
        }
        Empid.add(id);
        EmpName.add(name);
        EmpSalary.add(sal);

        updateDataBase(-1);
        System.out.println("Database Updated");
    }

    public void removeEmp(int id)throws IOException
    {
        int index = Empid.indexOf(id);
        if(index ==-1)
        {
            System.out.println("Employee with that ID does not exist\nTry Again");
            return;
        }
        else
        {
            Empid.remove(index);
            EmpName.remove(index);
            EmpSalary.remove(index);

            updateDataBase(id);
            System.out.println("Database Updated");
        }    
    }

    public void updateSalary(int id,BufferedReader br)throws IOException
    {
        int index = Empid.indexOf(id);
        if(index == -1)
        {
            System.out.println("Employee with that ID does not exist\nTry Again");
            return;
        }
        else
        {
            System.out.println("Enter new Salary");
            double sal = Double.parseDouble(br.readLine());
            EmpSalary.set(index,sal);

            updateDataBase(-1);
            System.out.println("Database Updated");
        }    
    }

    private void updateDataBase(int id)throws IOException
    {
        try
        {
            PrintWriter pw = new PrintWriter("database.txt");
            String content = "";
            for(int i=0;i<Empid.size();i++)
            {
                if(Empid.get(i)!=id)
                {
                    if(i==0)
                        content += Empid.get(0) + "\t" + EmpName.get(0) + "\t" + EmpSalary.get(0);
                    else
                        content += "\n" + Empid.get(i) + "\t" + EmpName.get(i) + "\t" + EmpSalary.get(i);    
                }
            }
            pw.println(content);
            pw.close();
        }catch(FileNotFoundException e)
        {
            System.out.println(e);
        }
    }
}


