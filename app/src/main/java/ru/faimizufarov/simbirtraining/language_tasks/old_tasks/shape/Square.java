package ru.faimizufarov.simbirtraining.language_tasks.old_tasks.shape;

public class Square implements Shape {

    int lengthOfSide = 0;
    double perimeter = 0.0;
    double area = 0.0;

    Square(int lengthOfSide) {
        this.lengthOfSide = lengthOfSide;
        perimeter = lengthOfSide * 4.0;
        area = (double) (lengthOfSide * lengthOfSide);
    }
}
