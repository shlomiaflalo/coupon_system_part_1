package beans;

 public enum Category {

    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4),
    KARAOKE(5),
    MARATHON(6),
    BOOK_OF_THE_YEAR(7),
    TECHNOLOGY(8),
    HEALTH(9),
    FASHION(10),
    TRAVEL(11),
    ENTERTAINMENT(12),
    SPORTS(13),
    EDUCATION(14),
    BEAUTY(15),
    HOME_IMPROVEMENT(16),
    FINANCE(17),
    AUTOMOTIVE(18),
    PETS(19),
    HOBBIES(20);

    private final int categoryId;

    Category(int categoryId){
        this.categoryId=categoryId;
    }

    public int getCategoryId() {
       return categoryId;
    }

    public static Category getRandomCategory(){
       return values()[(int)(Math.random()*Category.values().length)];
    }

    @Override
    public String toString() {
       return "Category: " + Category.super.name()+
               " ,Category id=" + categoryId;
    }
}