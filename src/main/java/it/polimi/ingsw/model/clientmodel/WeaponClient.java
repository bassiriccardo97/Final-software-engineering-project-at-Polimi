package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class WeaponClient implements Serializable {

    private String name;
    private char[] cost;
    private char[] costOpt;
    private char[] costAlt;
    private boolean loaded;

    private String baseDescription;
    private String opt1Description;
    private String opt2Description;
    private String altDescription;

    private boolean baseEffect;
    private boolean opt1Effect;
    private boolean opt2Effect;
    private boolean altEffect;

    //GETTER

    public String getName() {
        return name;
    }

    public char[] getCost() {
        return cost;
    }

    public char[] getCostOpt() {
        return costOpt;
    }

    public char[] getCostAlt() {
        return costAlt;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getBaseDescription() {
        return baseDescription;
    }

    public String getOpt1Description() {
        return opt1Description;
    }

    public String getOpt2Description() {
        return opt2Description;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public boolean isBaseEffect() {
        return baseEffect;
    }

    public boolean isOpt1Effect() {
        return opt1Effect;
    }

    public boolean isOpt2Effect() {
        return opt2Effect;
    }

    public boolean isAltEffect() {
        return altEffect;
    }


    //SETTER

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(char[] cost) {
        this.cost = cost;
    }

    public void setCostOpt(char[] costOpt) {
        this.costOpt = costOpt;
    }

    public void setCostAlt(char[] costAlt) {
        this.costAlt = costAlt;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void setBaseDescription(String baseDescription) {
        this.baseDescription = baseDescription;
    }

    public void setOpt1Description(String opt1Description) {
        this.opt1Description = opt1Description;
    }

    public void setOpt2Description(String opt2Description) {
        this.opt2Description = opt2Description;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public void setBaseEffect(boolean baseEffect) {
        this.baseEffect = baseEffect;
    }

    public void setOpt1Effect(boolean opt1Effect) {
        this.opt1Effect = opt1Effect;
    }

    public void setOpt2Effect(boolean opt2Effect) {
        this.opt2Effect = opt2Effect;
    }

    public void setAltEffect(boolean altEffect) {
        this.altEffect = altEffect;
    }
}
