package Model.Usuario;

import Model.UserType;
import Model.Rotina.Rotina;

public class Aluno extends Usuario {
    private int age;
    private float weight;
    private float height;
    private Rotina rotina; 

    public Aluno(String name, int age, float weight, float height){
        super();
        setTipoUsuario(UserType.ALUNO);
        setName(name);
        setAge(age);
        setWeight(weight);
        setHeight(height);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rotina getRotina() {
        return rotina;
    }
}
