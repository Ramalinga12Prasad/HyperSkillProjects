package machine;

import java.util.Scanner;
public class CoffeeMachine {
 public enum MachineState {
        BUY("buy"), FILL("fill"), TAKE("take"), REMAINING("remaining"), EXIT("exit");
        String state ;
        MachineState(String state) {
            this.state = state;
        }
        public String getState(){
            return this.state;
        }
        public static MachineState findByState(String state) {
            for(MachineState currState : MachineState.values()) {
                if(currState.getState().equals(state)) {
                    return currState;
                }
            }
            return null;
        }
    }

    public enum CoffeeTypes {
        ESPRESSO("1"), LATTE("2"), CAPPUCCINO("3"), BACK("back");
        String id;
        CoffeeTypes(String id) {
            this.id = id;
        }
        public String getId() {
            return this.id;
        }
        public static CoffeeTypes findById(String id) {
            for (CoffeeTypes coffeeType : CoffeeTypes.values()) {
                if (coffeeType.getId().equals(id)) {
                    return coffeeType;
                }
            }
            return null;
        }
    }
    private int machineWater ;
    private int machineMilk;
    private int coffeeBeans;
    private int disposableCups;
    private int money;
    private boolean isON;
   
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
       CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
       boolean isMachineRunning = true;
      
       while(coffeeMachine.isON()) {
           System.out.println("Write action (buy, fill, take, remaining, exit): ");
           String action = in.next();
           MachineState state = MachineState.findByState(action);
        switch (state) {
            case BUY :
            coffeeMachine.buy(in);
            break;
            case FILL :
            coffeeMachine.fill(in);
            break;
            case TAKE :
            coffeeMachine.take();
            break;
            case REMAINING :
            System.out.println(coffeeMachine);
            break;
            case EXIT :
            coffeeMachine.off();
         
        }
       }
      
     

        
    }
    public void buy(Scanner in){
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:  ");
        String choice = in.next();
        CoffeeTypes coffeeTypes = CoffeeTypes.findById(choice);
        System.out.println();
        switch (coffeeTypes) {

    // 1 : For the espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
    // 2 : For the latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
    // 3 : And for the cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee. It costs $6.

            case ESPRESSO: {
                if(isDispensible(250, 0, 16, 1)){
                    this.setMachineWater(this.getMachineWater() - 250);
                    this.setCoffeeBeans(this.getCoffeeBeans() - 16);
                    this.decrementDisposableCups();
                    this.setMoney(this.getMoney() + 4);
                    System.out.println("I have enough resources, making you a coffee\n");
                } 
                break;
            }
            case LATTE : {
                if(isDispensible(350, 75, 20, 1)) {
                    this.setMachineWater(this.getMachineWater() - 350);
                    this.setMachineMilk(this.getMachineMilk() - 75);
                    this.setCoffeeBeans(this.getCoffeeBeans() - 20);
                    this.decrementDisposableCups();
                    this.setMoney(this.getMoney() + 7);
                    System.out.println("I have enough resources, making you a coffee\n");
                } 
                break;
            
            }
            case CAPPUCCINO : {
                if(isDispensible(200, 100, 12, 1)) {
                    this.setMachineWater(this.getMachineWater() - 200);
                    this.setMachineMilk(this.getMachineMilk() - 100);
                    this.setCoffeeBeans(this.getCoffeeBeans() - 12);
                    this.decrementDisposableCups();
                    this.setMoney(this.getMoney() + 6);
                    System.out.println("I have enough resources, making you a coffee\n");
                } 
                break;
            }
            case BACK : 
            // System.out.println();
            return ;
        }
    }
    public void fill(Scanner in) {
        System.out.println("\nWrite how many ml of water do you want to add: ");
        int machineWater = in.nextInt();
        this.setMachineWater(this.getMachineWater() + machineWater);
        System.out.println("Write how many ml of milk do you want to add: ");
        int machineMilk = in.nextInt();
        this.setMachineMilk(this.getMachineMilk() + machineMilk);
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        int coffeeBeans = in.nextInt();
        this.setCoffeeBeans(this.getCoffeeBeans() + coffeeBeans);
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        int noOfCups = in.nextInt();
        System.out.println();
        this.setDisposableCups(this.getDisposableCups() + noOfCups);
    }
    public void take() {
        System.out.println("I gave you $" + this.getMoney() + "\n");
        this.setMoney(0);
    }

    public boolean isDispensible(int water, int milk, int beans, int cups){
        boolean enoughWater = this.getMachineWater() >= water;
        boolean enoughMilk = this.getMachineMilk() >= milk;
        boolean enoughBeans = this.getCoffeeBeans() >= beans;
        boolean enoughCup = this.getDisposableCups() >= cups;
        boolean isDispensible = enoughWater && enoughMilk && enoughBeans && enoughCup;
        if(isDispensible){
            return true;
        }
        System.out.print("Sorry, not enough ");
        if(!enoughWater) {
            System.out.println("water!");
        }
        if(!enoughMilk) {
            System.out.println("milk!");
        }
        if(!enoughBeans) {
            System.out.println("coffee beans!");
        }
        if(!enoughCup) {
            System.out.println("disposable cups!");
        }
        System.out.println();


        return false;
    }

    public int getMachineWater() {
        return machineWater;
    }

    public void setMachineWater(int machineWater) {
        this.machineWater = machineWater;
    }

    public int getMachineMilk() {
        return machineMilk;
    }

    public void setMachineMilk(int machineMilk) {
        this.machineMilk = machineMilk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getDisposableCups() {
        return disposableCups;
    }

    public void setDisposableCups(int disposableCups) {
        this.disposableCups = disposableCups;
    }

    public void decrementDisposableCups() {
        this.disposableCups--;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void off(){
        this.isON = false;
    }
    public boolean isON() {
        return this.isON;
    }

    @Override
    public String toString() {
        return "\nThe coffee machine has:\n" + machineWater + " of water\n" + machineMilk + " of milk\n"
                + coffeeBeans + " of coffee beans\n" + disposableCups + " of disposable cups\n" + money + " of money\n";
    }

    public CoffeeMachine(int machineWater, int machineMilk, int coffeeBeans, int disposableCups, int money) {
        this.machineWater = machineWater;
        this.machineMilk = machineMilk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.money = money;
        this.isON = true;
    }
}