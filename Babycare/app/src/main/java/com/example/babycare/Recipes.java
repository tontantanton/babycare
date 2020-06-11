package com.example.babycare;

//
//public class Recipes {
//    private String title;
//    private String url;
//    private String image_url;
//    private String id;
//
//    public Recipes() {
//    }
//
//    public Recipes(String title, String url, String image_url, String id){
//        super();
//        this.title = title;
//        this.url = url;
//        this.image_url = image_url;
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        title = title;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        url = url;
//    }
//
//    public String getImage_url() {
//        return image_url;
//    }
//
//    public void setImage(String image_url) {
//        image_url = image_url;
//    }
//
//    public String getID() {
//        return id;
//    }
//
//    public void setID(String id) {
//        id = id;
//    }
//}


//public class Recipes {
//
//
//    private String name;
//    private String calories;
//    private String id;
//    private String protein;
//
//    public Recipes() {
//    }
//
//    public Recipes(String name, String calories, String id, String protein) {
//        super();
//        this.name = name;
//        this.calories = calories;
//        this.id = id;
//        this.protein = protein;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCalories() {
//        return calories;
//    }
//
//    public void setCalories(String calories) {
//        this.calories = calories;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//    public String getProtein() {
//        return protein;
//    }
//
//    public void setProtein(String protein) {
//        this.protein = protein;
//    }
//
//}

public class Recipes {



    private String calories;
    private String id;
    private String name;
    private String protein;
    private String vitamina;
    private String vitaminac;
    private String vitaminb6;
    private String vitaminb12;
    private String calcium;
    private String iodine;
    private String iron;



        public Recipes() {
    }

    public Recipes(String calories, String id, String name, String protein,String vitamina,String vitaminac,
    String vitaminb6, String vitaminb12 ,String calcium, String iodine, String iron) {
        super();

        this.calories = calories;
        this.id = id;
        this.name = name;
        this.protein = protein;
        this.vitamina = vitamina;
        this.vitaminac = vitaminac;
        this.vitaminb6 = vitaminb6;
        this.vitaminb12 = vitaminb12;
        this.calcium = calcium;
        this.iodine = iodine;
        this.iron = iron;


    }


        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProtein() {
            return protein;
        }

        public void setProtein(String protein) {
            this.protein = protein;
        }

        public String getVitamina() {
            return vitamina;
        }

        public void setVitamina(String vitamina) {
            this.vitamina = vitamina;
        }

        public String getVitaminac() {
            return vitaminac;
        }

        public void setVitaminac(String vitaminac) {
            this.vitaminac = vitaminac;
        }

        public String getVitaminb6() {
            return vitaminb6;
        }

        public void setVitaminb6(String vitaminb6) {
            this.vitaminb6 = vitaminb6;
        }

        public String getVitaminb12() {
            return vitaminb12;
        }

        public void setVitaminb12(String vitaminb12) {
            this.vitaminb12 = vitaminb12;
        }

        public String getCalcium() {
            return calcium;
        }

        public void setCalcium(String calcium) {
            this.calcium = calcium;
        }

        public String getIodine() {
            return iodine;
        }

        public void setIodine(String iodine) {
            this.iodine = iodine;
        }

        public String getIron() {
            return iron;
        }

        public void setIron(String iron) {
            this.iron = iron;
        }






}
