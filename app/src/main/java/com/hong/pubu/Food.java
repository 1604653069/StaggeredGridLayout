package com.hong.pubu;

public class Food {

    /**
     * id : 0
     * foodname : 干豆角扣肉
     * foodimage : image/0/20201226160896408345345110106890.jpg
     * type : 热菜/家常菜/下饭菜/午餐/晚餐
     * mainmaterial : 五花肉-400克,干豆角-30克
     * secondmaterial : 食用油-200ml,食盐-半勺,葱-适量,料酒-1勺,生抽-1勺,蚝油-1勺,蒜末-适量,姜-适量
     * thirdmaterial : null
     * fourthmaterial : null
     * goodnum : 2
     * collectnum : 0
     */

    private int id;
    private String foodname;
    private String foodimage;
    private String type;
    private String mainmaterial;
    private String secondmaterial;
    private Object thirdmaterial;
    private Object fourthmaterial;
    private int goodnum;
    private int collectnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainmaterial() {
        return mainmaterial;
    }

    public void setMainmaterial(String mainmaterial) {
        this.mainmaterial = mainmaterial;
    }

    public String getSecondmaterial() {
        return secondmaterial;
    }

    public void setSecondmaterial(String secondmaterial) {
        this.secondmaterial = secondmaterial;
    }

    public Object getThirdmaterial() {
        return thirdmaterial;
    }

    public void setThirdmaterial(Object thirdmaterial) {
        this.thirdmaterial = thirdmaterial;
    }

    public Object getFourthmaterial() {
        return fourthmaterial;
    }

    public void setFourthmaterial(Object fourthmaterial) {
        this.fourthmaterial = fourthmaterial;
    }

    public int getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(int goodnum) {
        this.goodnum = goodnum;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }
}
