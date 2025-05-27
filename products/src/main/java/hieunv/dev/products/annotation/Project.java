package hieunv.dev.products.annotation;

public class Project {

    @DeveloperInfo(name = "hieunv", date = "2025-05-27", description = "Implement business logic...")
    public void implementBusinessLogic() {
        System.out.println( "Implement business logic...");
    }

    @DeveloperInfo(name = "hieunv", date = "2025-05-27")
    public void setupDatabase() {
        System.out.println( "Setup database...");
    }
}
