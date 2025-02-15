Welcome to coupon system project.

Once the database had been initialized and filled with
random data for testing you can see, that on every running of the
program you get different result. Made it in a way that
you get x number of companies or customers for each running (playing the program) with
some data for demonstrating the system and the tests.

You should change the db address on the connection pool class to yours
by go to this address:
URL: mysql://localhost:3306/coupon_system?
createDatabaseIfNotExist=true;
or just add coupon_system schema on your sql database.

Full details to connect to project database:
URL: mysql://localhost:3306/coupon_system?
createDatabaseIfNotExist=true";
USER: root;
PASSWORD: shlomi123;

Exceptions: printing out custom exceptions with red
color as bad message using the ConsoleColorsUtil class for colorful printing.
Good messages will be print
out as green color (not connected to exceptions) using the ConsoleColorsUtil class for colorful printing.


