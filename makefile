all:	models services resources ui hotel

models: ModelClasses.java
	javac -d pkgs ModelClasses.java

services:	ModelClasses.java ServiceClasses.java
	javac -d pkgs -cp pkgs ServiceClasses.java

resources:	ModelClasses.java ServiceClasses.java ResourceClasses.java
	javac -d pkgs -cp pkgs ResourceClasses.java

ui:	ModelClasses.java ServiceClasses.java ResourceClasses.java UserInterfaceClasses.java
	javac -d pkgs -cp pkgs UserInterfaceClasses.java

hotel:	ModelClasses.java ServiceClasses.java ResourceClasses.java UserInterfaceClasses.java
	javac -d obj -cp pkgs HotelApplication.java
