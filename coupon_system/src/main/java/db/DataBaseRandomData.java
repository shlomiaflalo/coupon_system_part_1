package db;

import java.util.List;

/**
 * The DataBaseRandomData class provides methods to generate lists of random data useful for populating databases.
 * This includes company emails, passwords, personal emails, first names, last names, coupon titles, and coupon descriptions.
 */
public class DataBaseRandomData {

    public static List<String> companyEmailsForDB(){
        return List.of(
                "info@techsolutions.com",
                "support@innovativeapps.net",
                "contact@nextgenmedia.org",
                "sales@globalservices.co",
                "hello@creativedesigns.io",
                "admin@futuristicenterprises.com",
                "careers@brightfutureinc.org",
                "info@securedata.com",
                "service@eco-friendlyproducts.com",
                "feedback@smartbusinessgroup.net",
                "hello@trendyclothing.co",
                "info@luxurylifestyle.com",
                "support@healthcareinnovations.org",
                "sales@greenenergycorp.com",
                "contact@streamlinedsolutions.io",
                "inquiries@traveladventures.net",
                "info@edtechrevolution.org",
                "hello@foodtechstartups.com",
                "admin@digitalmarketingsolutions.co",
                "support@fintechventures.org"
        );
    }


    public static List<String> passwords(){
        return List.of(
                "P@ssw0rd123!",
                "S3cur3P@ss!",
                "W3lcome2024$",
                "T3st!ng#2023",
                "Ch@ngeMeNow1",
                "B3stP@ssw0rd!",
                "Qw3rty!2023",
                "S@fe&Sound99",
                "D0nTGu3ss!@",
                "P@ssword!2024",
                "1SecureP@ss!",
                "Strong&Stable2",
                "P@ssw0rdsRUs#",
                "Tru5tN0b0dy!",
                "1M@keItS@fe!",
                "C0mp@nyN3twork$",
                "P@ssw0rd#1A",
                "C0d3Secure#2023",
                "D0nT#Sh@re!23",
                "SecreT#P@ss!4"
        );
    }

    public static List<String>personalEmailsForDB(){
        return List.of(
                "john.doe@example.com",
                "jane.smith@example.com",
                "alex.brown@example.com",
                "mary.jones@example.com",
                "mike.wilson@example.com",
                "lisa.white@example.com",
                "david.lee@example.com",
                "susan.davis@example.com",
                "peter.thompson@example.com",
                "anna.jackson@example.com",
                "charles.harris@example.com",
                "emma.martin@example.com",
                "olivia.baker@example.com",
                "liam.carter@example.com",
                "noah.johnson@example.com",
                "ava.rodriguez@example.com",
                "isabella.miller@example.com",
                "ethan.lee@example.com",
                "mason.wilson@example.com",
                "sophia.martinez@example.com"
        );
    }

    public static List<String>firstNames(){
        return List.of(
                "John",
                "Jane",
                "Alex",
                "Mary",
                "Michael",
                "Lisa",
                "David",
                "Susan",
                "Peter",
                "Anna",
                "Charles",
                "Emma",
                "Olivia",
                "Liam",
                "Noah",
                "Ava",
                "Isabella",
                "Ethan",
                "Mason",
                "Sophia"
        );
    }

    public static List<String>lastNames(){
        return List.of(
                "Smith",
                "Johnson",
                "Williams",
                "Brown",
                "Jones",
                "Garcia",
                "Miller",
                "Davis",
                "Rodriguez",
                "Martinez",
                "Hernandez",
                "Lopez",
                "Gonzalez",
                "Wilson",
                "Anderson",
                "Thomas",
                "Taylor",
                "Moore",
                "Jackson",
                "Martin"
        );
    }


    public static List<String>titlesForCoupons(){
        return List.of(
                "Spring Sale: 20% Off",
                "Buy One Get One Free",
                "Free Shipping on Orders Over $50",
                "End of Season Clearance",
                "Exclusive 15% Off for Members",
                "Holiday Special: Save $10",
                "Flash Sale: 24 Hours Only",
                "Free Gift with Purchase",
                "Student Discount: 10% Off",
                "Refer a Friend and Get $5 Off",
                "Limited Time Offer: 30% Off",
                "Buy Two, Get One 50% Off",
                "Weekend Sale: Extra 25% Off",
                "First Order Discount: 20% Off",
                "Summer Blowout: Up to 50% Off",
                "Early Bird Discount: 15% Off",
                "Mystery Coupon: Unwrap Your Savings",
                "Loyalty Reward: 10% Off Next Purchase",
                "App Exclusive: Extra 10% Off",
                "Cyber Monday Deals: Huge Savings"
        );
    }

    public static List<String> descriptionForCoupons(){
        return List.of(
                "Enjoy a 20% discount on your next purchase during our Spring Sale!",
                "Buy one item and get another one free of charge!",
                "Get free shipping on all orders over $50 for a limited time.",
                "Take advantage of our End of Season Clearance and save big!",
                "Exclusive offer: Members receive an additional 15% off their purchase.",
                "Celebrate the holidays with a special discount of $10 off your order.",
                "Don’t miss our Flash Sale—24 hours only for incredible savings!",
                "Receive a free gift with every purchase made this weekend.",
                "Students save 10% on all items with valid student ID.",
                "Refer a friend and both of you will receive $5 off your next purchase!",
                "Limited Time Offer: Enjoy a generous 30% discount on select items.",
                "Buy two items and get the third at 50% off your total!",
                "This weekend only, take an extra 25% off your entire purchase.",
                "First-time customers can use this coupon for a 20% discount!",
                "Join our Summer Blowout Sale with discounts up to 50% off!",
                "Early birds get 15% off when you shop before 10 AM!",
                "Unwrap savings with our Mystery Coupon—surprise discounts await!",
                "Loyal customers receive a 10% discount on their next order as a thank you.",
                "Exclusive to app users: Get an extra 10% off your next purchase.",
                "Shop on Cyber Monday for unbeatable deals and huge savings!"
        );

    }

}
