
pkgs/model/ModelClasses.class:	ModelClasses.java
	javac -d pkgs ModelClasses.java

pkgs/service/ServiceClasses.class:	ServiceClasses.java pkgs/model/ModelClasses.class
	javac -d pkgs -cp pkgs ServiceClasses.java

obj/HotelReservationTester.class:	HotelReservationTester.java pkgs/model/ModelClasses.class pkgs/service/ServiceClasses.class
	javac -d obj -cp pkgs HotelReservationTester.java
