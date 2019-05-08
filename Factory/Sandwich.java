/*
 * Authors: Originally written by Abdullah.
 */

package Factory;


import Simulation.Food;

public class Sandwich extends Food {
    private String type;

    public Sandwich(String type) {
        super(type);
        this.type = type;
    }

    public SandwichTruck makeSandwich(){
        SandwichTruck sandwich = null;
        type = type.toLowerCase();
        String[] buildSandwich = type.split(":");


        if(buildSandwich[0].equals("roll"))
           sandwich = new Roll();

        if(buildSandwich[0].equals("warp"))
            sandwich = new Wrap();





                if (buildSandwich[1].equals("Ham")) {
                    assert sandwich != null;
                    sandwich.ingredient(new Ham());
                }

                else if(buildSandwich[1].equals("Turkey")) {
                    assert sandwich != null;
                    sandwich.ingredient(new Turkey());
                }

           if(buildSandwich[2].equals("cheese"))
               if (sandwich != null) {
                   sandwich.ingredient(new Cheese());
               }

           if(buildSandwich[3].equals("mayo")) {
                    assert sandwich != null;
                    sandwich.ingredient(new Mayo());
                }

        if(buildSandwich[4].equals("mustard")) {
            assert sandwich != null;
            sandwich.ingredient(new Mustard());
        }

        if(buildSandwich[5].equals("lettuce")) {
            assert sandwich != null;
            sandwich.ingredient(new Lettuce());
        }

        if(buildSandwich[6].equals("tomato")) {
            assert sandwich != null;
            sandwich.ingredient(new Tomato());
        }
        assert sandwich != null;
        sandwich.ingredient(new ContainerCost());
       sandwich.ingredient(new Warping());


        return sandwich;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                "type='" + type + '\'' +
                '}';
    }
}
